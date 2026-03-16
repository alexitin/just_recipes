package com.alexit.justrecipes.ui.inputingrediets

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexit.justrecipes.data.model.IngredientModel
import com.alexit.justrecipes.data.repository.IngredientRepository
import com.alexit.justrecipes.utility.GSuffArray
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InputIngredientsViewModel @Inject constructor(
    private val ingredientRepository: IngredientRepository,
): ViewModel() {

    private val _uiState = MutableStateFlow(InputIngredientsUiState())
    val uiState: StateFlow<InputIngredientsUiState> = _uiState.asStateFlow()

    private lateinit var _inputtedIngredients: SnapshotStateList<IngredientModel>
    val inputtedIngredients: List<IngredientModel> get() = _inputtedIngredients
    var ingredients = mutableListOf<IngredientModel>()
    private lateinit var gsaIngredients: GSuffArray

    init {
        viewModelScope.launch {
            loadIngredients()
            _inputtedIngredients = loadInputtedIngredients()
            updateGSA()
        }
    }
    lateinit var deletingIngredient: IngredientModel
    lateinit var addingIngredient: IngredientModel

    val inputTextStateIngredient = TextFieldState()
    var focusInputIngredientState = mutableStateOf(false)
    var selectedIndexCategory = mutableIntStateOf(-1)

    fun updateSuggestionsIngredient(colorMatched: Color) {
        val query = inputTextStateIngredient.text.toString()
        val suggestions = highlight(
            color = colorMatched,
            query = query,
            items = ingredients.map { it.name },
            matches = findMatchingIndex(query, gsaIngredients)
        )
        _uiState.update { currentState ->
            currentState.copy(suggestionsIngredient = suggestions)
        }
    }

    fun selectSuggestionIngredient(suggestion: String) {
        inputTextStateIngredient.setTextAndPlaceCursorAtEnd(suggestion)
    }

    fun addInputtedIngredient(ingredientName: String) {
        if (ingredients.any { it.name == ingredientName } && !inputtedIngredients.any { it.name == ingredientName }) {
            addingIngredient = ingredients.find { it.name == ingredientName } !!
            inputTextStateIngredient.clearText()
            ingredientRepository.addInputtedIngredient(addingIngredient)
            _inputtedIngredients.add(0, addingIngredient)
        }
        else if (inputtedIngredients.any { it.name == ingredientName } ) {
            addingIngredient = inputtedIngredients.find { it.name == ingredientName } !!
            inputTextStateIngredient.clearText()
            _uiState.update { currentState ->
                currentState.copy(isIngredientInputted = true)
            }
        }
        else if (ingredients.any { it.name != ingredientName }) {
            addingIngredient = IngredientModel(
                id = ingredients.size + 1,
                name = ingredientName,
                weight = null,
                category = ""
            )
            _uiState.update { currentState ->
                currentState.copy(isIngredientNew = true)
            }
        }
    }

    fun updateIsIngredientInputted() {
        _uiState.update { currentState ->
            currentState.copy(isIngredientInputted = false)
        }
    }

    fun addNewIngredient(category: String) {
        addingIngredient = IngredientModel(
            id = addingIngredient.id,
            name = addingIngredient.name,
            weight = addingIngredient.weight,
            category = category
        )
        ingredientRepository.addIngredient(addingIngredient)
        ingredients.add(addingIngredient)
        updateGSA()
        ingredientRepository.addInputtedIngredient(addingIngredient)
        _inputtedIngredients.add(0, addingIngredient)
        selectedIndexCategory.intValue = -1
        updateIsIngredientNew()
    }

    fun updateIsIngredientNew() {
        inputTextStateIngredient.clearText()
        _uiState.update { currentState ->
            currentState.copy(isIngredientNew = false)
        }
    }

    fun deleteInputtedIngredient() {
        ingredientRepository.deleteInputtedIngredient(deletingIngredient)
        _inputtedIngredients.remove(deletingIngredient)
        _uiState.update { currentState ->
            currentState.copy(isDeleteIngredient = false)
        }
    }

    fun updateIsDeleteIngredient(ingredient: IngredientModel) {
        deletingIngredient = ingredient
        _uiState.update { currentState ->
            currentState.copy(isDeleteIngredient = true)
        }
    }

    fun dismissDeleteIngredient() {
        _uiState.update { currentState ->
            currentState.copy(isDeleteIngredient = false)
        }
    }

    fun updateWeightIngredient(id: Int, weight: Float) {
        inputtedIngredients.find { it.id == id }?.weight = weight
    }

    private fun updateGSA() {
            gsaIngredients = GSuffArray(*ingredients.map { it.name }.toTypedArray())

    }

    private fun loadIngredients () {
        ingredients += ingredientRepository.getIngredients()
    }

    private fun loadInputtedIngredients(): SnapshotStateList<IngredientModel> {
        return ingredientRepository.getInputtedIngredients().toMutableStateList()
    }

    private fun findMatchingIndex(query: String, gsa: GSuffArray): Map<Int, List<Int>> {
        if (query.isEmpty()) return emptyMap()

        return gsa.index(query)
            .groupBy { (_, itemIndex) -> itemIndex } // group by index to yield a map of index to a list of index and character positions
            .mapValues { (_, list) -> list.map { (start, _) -> start } } // generate new map of index(key) to list of character positions (Values)
    }

    private fun highlight(
        color: Color,
        query: String,
        items: List<String>,
        matches: Map<Int, List<Int>>
    ): List<AnnotatedString> {
        if (query.isEmpty()) return items.map { AnnotatedString(it) }

        return matches
            .map { entry ->     // build list of annotated string from the index and character positions
                buildAnnotatedString {
                    append(items[entry.key])
                    entry.value.forEach {
                        addStyle(
                            style = SpanStyle(background = color),
                            start = it,
                            end = it + query.length
                        )
                    }
                }
            }
    }
}