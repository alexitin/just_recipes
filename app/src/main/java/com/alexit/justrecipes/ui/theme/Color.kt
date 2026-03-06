package com.alexit.justrecipes.ui.theme

import androidx.activity.ComponentDialog
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val green1 = Color(0xFFE8E8D6)
val green2 = Color(0xFF84B444)
val green3 = Color(0xFF83A656)
val green4 = Color(0x99DCEDD3)
val green5 = Color(0xFFDCEDD3)
val green6 = Color(0x33102107)
val green7 = Color(0xFF9ADB46)
val green8 = Color(0xFF93A654)
val green9 = Color(0xFFCBD8A9)
val green10 = Color(0xFFB2C485)
val grey1 = Color(0x1A3E5134)
val black1 = Color(0xFF323230)
val black2 = Color(0xCCFFFFFF)
val brown1 = Color(0xFFC8BC9A)
val brown2 = Color(0x99AA9669)
val brown3 = Color(0xFFE5E2CE)
val brown4 = Color(0xFFAA9669)
val brown5 = Color(0x88AA9669)
val brown6 = Color(0xFFFFF7E2)
val red1 = Color(0xFFEE5C4C)

@Immutable
data class CustomColors(
    val background: Color,
    val titlePanel: Color,
    val onTitlePanel: Color,
    val dividerBottomMenu: Color,
    val bottomMenu: Color,
    val onBottomMenu: Color,
    val bottomMenuSelected: Color,
    val onBottomMenuSelected: Color,
    val topBorderButtonSelected: Color,
    val unfocusedTextIngredient: Color,
    val focusedTextIngredient: Color,
    val unfocusedFieldIngredient: Color,
    val focusedFieldIngredient: Color,
    val unfocusedBorderFieldIngredient: Color,
    val focusedBorderFieldIngredient: Color,
    val inputtedIngredientsField: Color,
    val iconSearchIngredient: Color,
    val inputtedIngredientsText: Color,
    val iconDeleteIngredient: Color,
    val weightIngredient: Color,
    val backgroundWeightIngredient: Color,
    val backgroundDialog: Color,
    val strokeDialog: Color,
    val textDialog: Color
)

val themeColors = CustomColors(
    background = green1,
    titlePanel = green2,
    onTitlePanel = green5,
    dividerBottomMenu = grey1,
    bottomMenu = green3,
    onBottomMenu = green4,
    bottomMenuSelected = green6,
    onBottomMenuSelected = green5,
    topBorderButtonSelected = green7,
    unfocusedTextIngredient = green8,
    focusedTextIngredient = black1,
    unfocusedFieldIngredient = green9,
    focusedFieldIngredient = black2,
    unfocusedBorderFieldIngredient = green10,
    focusedBorderFieldIngredient = brown1,
    inputtedIngredientsField = brown3,
    iconSearchIngredient = brown2,
    inputtedIngredientsText = brown4,
    iconDeleteIngredient = red1,
    weightIngredient = brown5,
    backgroundWeightIngredient = black2,
    backgroundDialog = brown6,
    strokeDialog = brown1,
    textDialog = brown2
)

val LocalCustomColors = staticCompositionLocalOf {
    themeColors
}