package com.alexit.justrecipes.data.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf


data class IngredientModel(
    val id: Int,
    val name: String,
    val category: String,
    val weight: Int? = null
)
