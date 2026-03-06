package com.alexit.justrecipes.ui.inputingrediets

import androidx.compose.ui.text.AnnotatedString

data class InputIngredientsUiState(
    val suggestionsIngredient: List<AnnotatedString> = listOf(),
    val isDeleteIngredient: Boolean = false,
    val isIngredientInputted: Boolean = false,
    val isIngredientNew: Boolean = false,
)
