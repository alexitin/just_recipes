package com.alexit.justrecipes.ui

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alexit.justrecipes.ui.inputingrediets.InputIngredientsScreen
import com.alexit.justrecipes.ui.inputingrediets.InputIngredientsViewModel
import com.alexit.justrecipes.ui.ownrecipes.OwnRecipesScreen
import com.alexit.justrecipes.ui.requestai.RequestAiScreen
import com.alexit.justrecipes.ui.searchrecipe.SearchRecipeScreen

@Composable
fun RecipesNavHost(
    navController: NavHostController,
    //modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = InputIngredients.route,
        //modifier = modifier
    ) {
        composable(route = InputIngredients.route) {
            InputIngredientsScreen()
        }
        composable(route = SearchRecipe.route) {
            SearchRecipeScreen()
        }
        composable(route = RequestAi.route) {
            RequestAiScreen()
        }
        composable(OwnRecipes.route) {
            OwnRecipesScreen()
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) {
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}