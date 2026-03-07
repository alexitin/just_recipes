package com.alexit.justrecipes.ui.inputingrediets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.byValue
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.delete
import androidx.compose.foundation.text.input.insert
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.foundation.text.input.then
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.alexit.justrecipes.data.model.IngredientModel

@Composable
fun InputtedIngredientsList(
    inputtedIngredients: List<IngredientModel>,
    onDeleteClick: (IngredientModel) -> Unit,
    onWeightClick: (Int, Float) -> Unit,
    iconDeleteIngredient: Int,
    descriptionIconDeleteIngredient: Int,
    colorIconDeleteIngredient: Color,
    colorInputtedIngredientsField: Color,
    colorInputtedIngredientText: Color,
    textStyleInputtedIngredient: TextStyle,
    iconScale: Int,
    descriptionIconScale: Int,
    colorBackgroundWeightIngredient: Color,
    colorWeightIngredient: Color,
    textStyleWeightIngredient: TextStyle,
    width: Dp,
    bottomMenuHeight: Dp,
    widthInputtedIngredientField: Dp,
    widthInputtedIngredientText: Dp,
    widthInputtedIngredientWeight: Dp,
    heightInputtedIngredientWeight:Dp,
    contentPadding: Dp,
    sizeIcon: Dp,
    sizeIconScale: Dp,
    radiusShape: Dp,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    LazyColumn(
        modifier = Modifier
            .consumeWindowInsets(paddingValues = PaddingValues(bottomMenuHeight))
            .imePadding()
    ) {
        items(items = inputtedIngredients, key = { inputtedIngredients.indexOf(it) }) { ingredient ->
            val state = rememberTextFieldState()
            var isFocusedWeight by remember { mutableStateOf(false) }
            Row(
                modifier = Modifier
                    .width(width)
                    .padding(top = contentPadding),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    modifier = Modifier
                        .size(sizeIcon)
                        .clickable(
                            enabled = true,
                            onClick = {
                                onDeleteClick(ingredient)
                                state.clearText()
                            }
                        ),
                    imageVector = ImageVector.vectorResource(iconDeleteIngredient),
                    contentDescription = stringResource(descriptionIconDeleteIngredient),
                    colorFilter = ColorFilter.tint(colorIconDeleteIngredient)
                )
                BasicTextField(
                    state = state,
                    modifier = Modifier
                        .onFocusChanged { isFocusedWeight = it.isFocused }
                        .width(widthInputtedIngredientField)
                        .wrapContentHeight()
                        .background(
                            color = colorInputtedIngredientsField,
                            shape = RoundedCornerShape(radiusShape)
                        )
                        .padding(contentPadding),
                    enabled = true,
                    inputTransformation = InputTransformation.maxLength(5).then {
                        if (asCharSequence().contains("\\D".toRegex())) revertAllChanges()
                        if (asCharSequence().matches("0+".toRegex())) revertAllChanges()
                    },
                    textStyle = textStyleWeightIngredient.copy(
                        textAlign = TextAlign.End,
                        color = colorWeightIngredient
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    onKeyboardAction = KeyboardActionHandler {
                        onWeightClick(ingredient.id, state.text.toString().toFloat())
                        keyboardController?.hide()
                    },
                    cursorBrush = Brush.verticalGradient(
                        0.00f to Color.Transparent,
                        0.20f to Color.Transparent,
                        0.20f to colorWeightIngredient,
                        0.80f to colorWeightIngredient,
                        0.80f to Color.Transparent,
                        1.00f to Color.Transparent
                    ),
                    decorator = { innerTextField ->
                        Row(
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            BasicText(
                                modifier = Modifier
                                    .width(widthInputtedIngredientText),
                                style = textStyleInputtedIngredient,
                                color = { colorInputtedIngredientText },
                                text = ingredient.name
                            )
                            if (isFocusedWeight || state.text.isNotEmpty()){
                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = if (isFocusedWeight) colorBackgroundWeightIngredient else colorInputtedIngredientsField,
                                            shape = RoundedCornerShape(size = radiusShape / 2)
                                        )
                                        .size(
                                            width = widthInputtedIngredientWeight,
                                            height = heightInputtedIngredientWeight
                                        )
                                        .padding(end = contentPadding),
                                    contentAlignment = Alignment.CenterEnd,

                                ) {
                                    innerTextField()
                                }
                                BasicText(
                                    style = textStyleWeightIngredient,
                                    color = { colorWeightIngredient },
                                    text = ingredient.unit
                                )
                            } else if(ingredient.weight == null) {
                                Box(
                                    modifier = Modifier
                                        .size(
                                            width = widthInputtedIngredientWeight,
                                            height = heightInputtedIngredientWeight
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    innerTextField()
                                }
                                Image(
                                    modifier = Modifier
                                        .size(sizeIconScale),
                                    imageVector = ImageVector.vectorResource(iconScale),
                                    contentDescription = stringResource(descriptionIconScale),
                                    colorFilter = ColorFilter.tint(colorWeightIngredient)
                                )
                            }
                            else if (ingredient.weight != null) {
                                //val weight = ingredient.weight!!.toInt()
                                BasicText(
                                    style = textStyleWeightIngredient,
                                    color = { colorWeightIngredient },
                                    text = "${ingredient.weight!!.toInt()} ${ingredient.unit}"
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}