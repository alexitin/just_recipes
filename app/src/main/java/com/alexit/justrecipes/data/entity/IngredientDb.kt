package com.alexit.justrecipes.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredient")
data class IngredientDb(
    @PrimaryKey(autoGenerate = false)
    val ingredientId: Int,
    val name: String,
    val energy: Float,
    val protein: Float,
    val fat: Float,
    val carbohydrate: Float,
    val synonym: String,
    val category: String
)
