package com.alexit.justrecipes.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredient_own")
data class IngredientOwn(
    @PrimaryKey(autoGenerate = false)
    val ingredientId: Int,
    val name: String,
    val category: String
)
