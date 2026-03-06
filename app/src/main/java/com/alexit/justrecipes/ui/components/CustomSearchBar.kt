package com.alexit.justrecipes.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import com.alexit.justrecipes.R

@Composable
fun CustomSearchBar (
    focusInputState: MutableState<Boolean>,
    state: TextFieldState,
    suggestions: List<AnnotatedString>,
    onSuggestionClick: (String) -> Unit,
    onDoneClick: (String) -> Unit,
    height: Dp,
    width: Dp,
    textStyle: TextStyle,
    placeholder: String,
    unfocusedTextColor: Color,
    focusedTextColor: Color,
    unfocusedField: Color,
    focusedField: Color,
    unfocusedBorderField: Color,
    focusedBorderField: Color,
    borderThickness: Dp,
    contentPadding: Dp,
    radiusShape:Dp,
    sizeIcon:Dp,
    bottomMenuHeight: Dp,
    colorIconSearch: Color,
) {
    val colorField: Color
    val colorBorderField: Color
    //val isFocusedIngredient = focusInputState.value
    if (state.text.isNotEmpty() || focusInputState.value) {
        colorField = focusedField
        colorBorderField = focusedBorderField
    } else {
        colorField = unfocusedField
        colorBorderField = unfocusedBorderField
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    BasicTextField(
        state = state,
        modifier = Modifier
            .onFocusChanged { focusInputState.value = it.isFocused}
            .height(height)
            .width(width),
        enabled = true,
        textStyle = textStyle.copy(color = focusedTextColor),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        onKeyboardAction = KeyboardActionHandler {
            onDoneClick(state.text.toString().trim())
            keyboardController?.hide()
        },
        lineLimits = TextFieldLineLimits.SingleLine,
        cursorBrush = Brush.verticalGradient(
            0.00f to Color.Transparent,
            0.25f to Color.Transparent,
            0.25f to focusedTextColor,
            0.80f to focusedTextColor,
            0.80f to Color.Transparent,
            1.00f to Color.Transparent
        ),
        decorator = { innerTextField ->
            Row(
                modifier = Modifier
                    .background(
                        color = colorField,
                        shape = RoundedCornerShape(size = radiusShape)
                    )
                    .border(
                        border = BorderStroke(
                            width = borderThickness,
                            color = colorBorderField
                        ),
                        shape = RoundedCornerShape(size = radiusShape)
                    ),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (state.text.isNotEmpty() || focusInputState.value) {
                    Image(
                        modifier = Modifier
                            .size(sizeIcon)
                            .padding(start = contentPadding),
                        imageVector = ImageVector.vectorResource(id = R.drawable.search_24px),
                        contentDescription = stringResource(id = R.string.icon_search),
                        colorFilter = ColorFilter.tint(colorIconSearch)
                    )
                    Box(
                        modifier = Modifier
                            .size(
                                width = width - (sizeIcon * 2) - contentPadding,
                                height = height
                            )
                            .padding(start = contentPadding),
                        contentAlignment = Alignment.CenterStart
                        )
                    {
                        innerTextField()
                    }
                    Image(
                        modifier = Modifier
                            .clickable(
                                enabled = true,
                                onClick = { state.clearText() }
                            )
                            .size(sizeIcon)
                            .padding(start = contentPadding),
                        imageVector = ImageVector.vectorResource(id = R.drawable.close_24px),
                        contentDescription = stringResource(id = R.string.clear_text),
                        colorFilter = ColorFilter.tint(colorIconSearch)
                    )
                } else {
                    Image(
                        modifier = Modifier
                            .size(sizeIcon)
                            .padding(start = contentPadding),
                        imageVector = ImageVector.vectorResource(id = R.drawable.add_24px),
                        contentDescription = stringResource(id = R.string.icon_add),
                        colorFilter = ColorFilter.tint(unfocusedTextColor)
                    )
                    Box(modifier = Modifier.padding(start = contentPadding)
                    ) {
                        BasicText(
                            style = textStyle,
                            text = placeholder,
                            color = { unfocusedTextColor }
                        )
                        innerTextField()
                    }
                }
            }
        },
    )
    if (state.text.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .consumeWindowInsets(paddingValues = PaddingValues(bottomMenuHeight))
                .imePadding()
                .width(width)
                .background(
                    color = focusedField,
                    shape = RoundedCornerShape(size = radiusShape)
                )
                .border(
                    border = BorderStroke(
                        width = borderThickness,
                        color = focusedBorderField
                    ),
                    shape = RoundedCornerShape(size = radiusShape)
                )
                .padding(start = contentPadding, end = contentPadding),
            verticalArrangement = Arrangement.Center
        ) {
            items(items = suggestions, key = { suggestions.indexOf(it) }) { suggestion ->
                BasicText(
                    modifier = Modifier
                        .padding(top = contentPadding, bottom = contentPadding)
                        .clickable(
                            enabled = true,
                            onClick = { onSuggestionClick(suggestion.text) }
                        ),
                    text = suggestion,
                    style = textStyle,
                    color = { focusedTextColor }
                )
                CustomDivider(
                    color = focusedTextColor,
                    thickness = borderThickness.dpToPx(),
                    startX = 0f,
                    endX = (width - contentPadding * 2).dpToPx(),
                    startY = 0f,
                    endY = 0f
                )
            }
        }
    }
}
