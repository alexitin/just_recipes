package com.alexit.justrecipes.di

import com.alexit.justrecipes.data.repository.IngredientRepository
import com.alexit.justrecipes.data.repository.IngredientRepositoryImpl
import com.alexit.justrecipes.data.sources.IngredientsSource
import com.alexit.justrecipes.data.sources.IngredientsSourceDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object justRecipesAppModule {

    @Provides
    @Singleton
    fun provideIngredientRepository(ingredientsSource: IngredientsSource) : IngredientRepository {
        return IngredientRepositoryImpl(ingredientsSource)
    }

    @Provides
    @Singleton
    fun provideIngredientSource() : IngredientsSource {
        return IngredientsSourceDB()
    }
}