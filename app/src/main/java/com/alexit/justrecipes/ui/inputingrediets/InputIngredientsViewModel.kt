package com.alexit.justrecipes.ui.inputingrediets

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
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
    val inputTextStateIngredient = TextFieldState()
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
    var selectedIndexCategory = mutableIntStateOf(-1)

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

    fun updateWeightIngredient(id: Int, weight: Int) {
        ingredientRepository.updateWeightIngredient(id, weight)
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
}