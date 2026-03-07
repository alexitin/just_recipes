package com.alexit.justrecipes.ui.inputingrediets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.alexit.justrecipes.R
import com.alexit.justrecipes.data.model.IngredientModel
import com.alexit.justrecipes.ui.components.CustomDialog
import com.alexit.justrecipes.ui.components.CustomPopup
import com.alexit.justrecipes.ui.components.CustomSearchBar
import com.alexit.justrecipes.ui.theme.JustRecipesTheme

@Composable
fun InputIngredientsScreen(
   inputIngredientsViewModel: InputIngredientsViewModel = hiltViewModel()
) {
    val inputIngredientsUiState by inputIngredientsViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        ) {
        InputIngredientsTitlePanel(
            height = JustRecipesTheme.dimensions.heightTitlePanel,
            background = JustRecipesTheme.colors.background1,
            color = JustRecipesTheme.colors.text1,
            padding = JustRecipesTheme.dimensions.paddingTextTitlePanel,
            text = stringResource(R.string.title_input_ingredients),
            style = JustRecipesTheme.typography.titlePanel
        )
        Column(
            modifier = Modifier
                .padding(vertical = JustRecipesTheme.dimensions.paddingFieldInput)
                .background(JustRecipesTheme.colors.background0)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            inputIngredientsViewModel.updateSuggestionsIngredient(JustRecipesTheme.colors.background3)
            CustomSearchBar(
                focusInputState = inputIngredientsViewModel.focusInputIngredientState,
                state = inputIngredientsViewModel.inputTextStateIngredient,
                suggestions = inputIngredientsUiState.suggestionsIngredient,
                onSuggestionClick = { suggestion: String -> inputIngredientsViewModel.selectSuggestionIngredient(suggestion) },
                onDoneClick = { ingredientName: String -> inputIngredientsViewModel.addInputtedIngredient(ingredientName) },
                height = JustRecipesTheme.dimensions.heightFieldInput,
                width = JustRecipesTheme.dimensions.widthInputtedIngredient,
                textStyle = JustRecipesTheme.typography.inputSearchField,
                placeholder = stringResource(R.string.placeholder_input_ingredients),
                focusedField = JustRecipesTheme.colors.background2,
                focusedBorderField = JustRecipesTheme.colors.border2,
                focusedTextColor = JustRecipesTheme.colors.text2,
                unfocusedField = JustRecipesTheme.colors.background3,
                unfocusedBorderField = JustRecipesTheme.colors.border3,
                unfocusedTextColor = JustRecipesTheme.colors.text3,
                contentPadding = JustRecipesTheme.dimensions.contentPaddingField,
                radiusShape = JustRecipesTheme.dimensions.radiusCornerField,
                borderThickness = JustRecipesTheme.dimensions.borderThickness,
                sizeIcon = JustRecipesTheme.dimensions.sizeIcon,
                bottomMenuHeight = JustRecipesTheme.dimensions.heightBottomMenu,
                colorIconSearch = JustRecipesTheme.colors.iconSearchIngredient,
            )
            if (inputIngredientsViewModel.inputTextStateIngredient.text.isEmpty()) {
                InputtedIngredientsList(
                    inputtedIngredients = inputIngredientsViewModel.inputtedIngredients,
                    onDeleteClick = { ingredient: IngredientModel -> inputIngredientsViewModel.updateIsDeleteIngredient(ingredient) },
                    onWeightClick = { id: Int, weight: Float -> inputIngredientsViewModel.updateWeightIngredient(id, weight) },
                    iconDeleteIngredient = R.drawable.round_do_not_disturb_on_24,
                    descriptionIconDeleteIngredient = R.string.delete_inputted_ingredient,
                    colorIconDeleteIngredient = JustRecipesTheme.colors.iconDeleteIngredient,
                    colorInputtedIngredientsField = JustRecipesTheme.colors.background4,
                    colorInputtedIngredientText = JustRecipesTheme.colors.text4,
                    textStyleInputtedIngredient = JustRecipesTheme.typography.titlePanel,
                    iconScale = R.drawable.outline_scale_24,
                    descriptionIconScale = R.string.scale,
                    colorBackgroundWeightIngredient = JustRecipesTheme.colors.background5,
                    colorWeightIngredient = JustRecipesTheme.colors.text5,
                    textStyleWeightIngredient = JustRecipesTheme.typography.weightInputtedIngredient,
                    contentPadding = JustRecipesTheme.dimensions.contentPaddingField,
                    width = JustRecipesTheme.dimensions.widthInputtedIngredient,
                    bottomMenuHeight = JustRecipesTheme.dimensions.heightBottomMenu,
                    widthInputtedIngredientField = JustRecipesTheme.dimensions.widthInputtedIngredientField,
                    widthInputtedIngredientText = JustRecipesTheme.dimensions.widthInputtedIngredientText,
                    widthInputtedIngredientWeight = JustRecipesTheme.dimensions.widthInputtedIngredientWeight,
                    heightInputtedIngredientWeight = JustRecipesTheme.dimensions.heightInputtedIngredientWeight,
                    sizeIcon = JustRecipesTheme.dimensions.sizeIcon,
                    sizeIconScale = JustRecipesTheme.dimensions.sizeIconScale,
                    radiusShape = JustRecipesTheme.dimensions.radiusCornerField
                )
            }
            if (inputIngredientsUiState.isDeleteIngredient) {
                CustomDialog(
                    onDismissRequest = { inputIngredientsViewModel.dismissDeleteIngredient() },
                    onConfirmation = { inputIngredientsViewModel.deleteInputtedIngredient() },
                    heightDialog = JustRecipesTheme.dimensions.heightDialog,
                    widthDialog = JustRecipesTheme.dimensions.widthDialog,
                    colorBackground = JustRecipesTheme.colors.background6,
                    colorBorder = JustRecipesTheme.colors.border6,
                    colorText = JustRecipesTheme.colors.text6,
                    radiusShape = JustRecipesTheme.dimensions.radiusCornerField,
                    borderThickness = JustRecipesTheme.dimensions.borderThickness,
                    textDialog = stringResource(R.string.delete_ingredient),
                    item = inputIngredientsViewModel.deletingIngredient.name,
                    textStyle = JustRecipesTheme.typography.titlePanel,
                    textConfirmation = stringResource(R.string.confirmation),
                    textDismiss = stringResource(R.string.dismiss)
                )
            }
            if (inputIngredientsUiState.isIngredientInputted) {
                CustomPopup(
                    onDismissRequest = {inputIngredientsViewModel.updateIsIngredientInputted()},
                    contentPadding = JustRecipesTheme.dimensions.paddingFieldInput,
                    widthPopup = JustRecipesTheme.dimensions.widthPopup,
                    colorBackground = JustRecipesTheme.colors.background6,
                    colorBorder = JustRecipesTheme.colors.border6,
                    colorText = JustRecipesTheme.colors.text6,
                    radiusShape = JustRecipesTheme.dimensions.radiusCornerField,
                    borderThickness = JustRecipesTheme.dimensions.borderThickness,
                    textStyle = JustRecipesTheme.typography.titlePanel,
                    textPopupPre = stringResource(R.string.ingredient),
                    textPopupAft = stringResource(R.string.already_exist),
                    item = inputIngredientsViewModel.addingIngredient.name
                )
            }
            if (inputIngredientsUiState.isIngredientNew) {
                MakeNewIngredient(
                    onDismissRequest = { inputIngredientsViewModel.updateIsIngredientNew() },
                    onConfirmation = { category: String -> inputIngredientsViewModel.addNewIngredient(category) },
                    selectedIndex = inputIngredientsViewModel.selectedIndexCategory,
                    heightDialog = JustRecipesTheme.dimensions.heightNewIngredientDialog,
                    widthDialog = JustRecipesTheme.dimensions.widthNewIngredientDialog,
                    colorBackground = JustRecipesTheme.colors.background6,
                    colorStroke = JustRecipesTheme.colors.border6,
                    colorText = JustRecipesTheme.colors.text6,
                    radiusShape = JustRecipesTheme.dimensions.radiusCornerField,
                    borderThickness = JustRecipesTheme.dimensions.borderThickness,
                    textDialogPre = stringResource(R.string.add_unknown_ingredient),
                    item = inputIngredientsViewModel.inputTextStateIngredient.text.toString(),
                    textDialogAft = stringResource(R.string.select_category_ingredient),
                    textStyle = JustRecipesTheme.typography.titlePanel,
                    textConfirmation = stringResource(R.string.confirmation),
                    textDismiss = stringResource(R.string.dismiss),
                    contentPadding = JustRecipesTheme.dimensions.contentPaddingField,
                    colorBackgroundCategory = JustRecipesTheme.colors.background2,
                    colorBorderCategory = JustRecipesTheme.colors.border2,
                    colorTextCategory = JustRecipesTheme.colors.text2,
                    listCategory = inputIngredientsViewModel.ingredients.map { it.category }.distinct().sorted(),
                    colorBackgroundCategorySelected = JustRecipesTheme.colors.background3
                )
            }
        }
    }
}
