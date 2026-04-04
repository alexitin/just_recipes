package com.alexit.justrecipes.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe")
data class RecipeDb(
    @PrimaryKey(autoGenerate = false)
    val recipeId: Int,
    val name: String,
    val portion: Int,
    val image: String,
    val details: String,
    val detailImg: String,
    val duration: Int
)
