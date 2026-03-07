package com.alexit.justrecipes.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.alexit.justrecipes.ui.bottommenu.BottomMenu
import com.alexit.justrecipes.ui.theme.JustRecipesTheme

@Composable
fun RecipesScreen(
) {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentScreen = recipesBottomMenuScreens.find {
        it.route == currentDestination?.route } ?: InputIngredients

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Column (
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ) {
            RecipesNavHost(navController = navController)
        }
        Row(
            modifier = Modifier
                .height(JustRecipesTheme.dimensions.heightBottomMenu)
                .background(JustRecipesTheme.colors.bottomMenu)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomMenu(
                recipesBottomMenuScreens = recipesBottomMenuScreens,
                buttonSelected = { newScreen ->
                    navController.navigateSingleTopTo(newScreen.route)
                                 },
                currentScreen = currentScreen
            )
        }
    }
}
