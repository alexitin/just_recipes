package com.alexit.justrecipes.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_ingredients_own",
    primaryKeys = ["recipeId", "ingredientId"]
)
data class RecipeIngredientsOwn(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val recipeId: Int,
    val ingredientId: Int,
    val quantity: Float,
    val unit: String
)
