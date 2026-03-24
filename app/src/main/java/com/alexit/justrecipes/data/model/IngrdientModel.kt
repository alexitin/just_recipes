package com.alexit.justrecipes.data.model


data class IngredientModel(
    val id: Int,
    val name: String,
    val category: String
) {
    var weight: Int? = null
}
