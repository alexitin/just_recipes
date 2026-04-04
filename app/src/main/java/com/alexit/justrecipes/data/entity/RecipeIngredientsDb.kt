package com.alexit.justrecipes.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_ingredients",
    primaryKeys = ["recipeId", "ingredientId"]
)
data class RecipeIngredientsDb(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val recipeId: Int,
    val ingredientId: Int,
    val quantity: Float,
    val unit: String,
    val density: Float
)