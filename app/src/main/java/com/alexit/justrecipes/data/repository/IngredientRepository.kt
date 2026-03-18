package com.alexit.justrecipes.data.repository

import com.alexit.justrecipes.data.model.IngredientModel
import com.alexit.justrecipes.data.sources.IngredientsSource

interface IngredientRepository {

    fun getIngredients(): List<IngredientModel>
    fun addIngredient(ingredient: IngredientModel)
    fun getInputtedIngredients(): MutableList<IngredientModel>
    fun addInputtedIngredient(ingredient: IngredientModel)
    fun deleteInputtedIngredient(ingredient: IngredientModel)
    fun updateWeightIngredient(id: Int, weight: Int)
}

class IngredientRepositoryImpl(
    private val ingredientsSource: IngredientsSource
) : IngredientRepository {

    override fun getIngredients(): List<IngredientModel> {
        return ingredientsSource.listIngredients
    }

    override fun addIngredient(ingredient: IngredientModel) {
        ingredientsSource.listIngredients.add(ingredient)
    }

    override fun getInputtedIngredients(): MutableList<IngredientModel> {
        return ingredientsSource.listInputtedIngredients
    }

    override fun addInputtedIngredient(ingredient: IngredientModel) {
        ingredientsSource.listInputtedIngredients.add(0,ingredient)
    }

    override fun deleteInputtedIngredient(ingredient: IngredientModel) {
        ingredientsSource.listInputtedIngredients.remove(ingredient)
    }

    override fun updateWeightIngredient(id: Int, weight: Int) {
        ingredientsSource.listInputtedIngredients.find { it.id == id }?.weight = weight
    }
}