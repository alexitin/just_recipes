package com.alexit.justrecipes.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_own")
data class RecipeOwn(
    @PrimaryKey(autoGenerate = false)
    val recipeId: Int,
    val name: String,
    val recipe: String?,
    val images: String?
)
