package com.alexit.justrecipes.data.model

data class IngredientModel(
    val id: Int,
    val name: String,
    var weight: Float?,
    val category: String
)
