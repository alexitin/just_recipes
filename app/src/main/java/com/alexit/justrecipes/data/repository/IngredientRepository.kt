package com.alexit.justrecipes.data.repository

import com.alexit.justrecipes.data.model.IngredientModel
import com.alexit.justrecipes.data.sources.IngredientsSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface IngredientRepository {

    fun getIngredients(): Flow<List<IngredientModel>>
    fun addIngredient(ingredient: IngredientModel)
    fun getInputtedIngredients(): Flow<List<IngredientModel>>
    fun addInputtedIngredient(ingredient: IngredientModel)
    fun deleteInputtedIngredient(ingredient: IngredientModel)
    fun updateWeightIngredient(id: Int, weight: Int?)
}

class IngredientRepositoryImpl(
    private val ingredientsSource: IngredientsSource
) : IngredientRepository {

    override fun getIngredients(): Flow<List<IngredientModel>> = flow {
        emit(ingredientsSource.listIngredients)
    }

    override fun addIngredient(ingredient: IngredientModel) {
        ingredientsSource.listIngredients.add(ingredient)
    }

    override fun getInputtedIngredients(): Flow<List<IngredientModel>> = flow {
        emit(ingredientsSource.listInputtedIngredients)
    }

    override fun addInputtedIngredient(ingredient: IngredientModel) {
        ingredientsSource.listInputtedIngredients.add(0,ingredient)
    }

    override fun deleteInputtedIngredient(ingredient: IngredientModel) {
        ingredientsSource.listInputtedIngredients.remove(ingredient)
    }

    override fun updateWeightIngredient(id: Int, weight: Int?) {
        val updatedIngredient = ingredientsSource.listInputtedIngredients.find { it.id == id }?.copy(weight = weight)
        val index = ingredientsSource.listInputtedIngredients.indexOfFirst { it.id == id }
        ingredientsSource.listInputtedIngredients[index] = updatedIngredient !!
    }
}