package com.alexit.justrecipes.ui.components

import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexit.justrecipes.R
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

@Composable
fun CustomSearchBar (
    state: TextFieldState,
    ingredientsName: PersistentList<String>,
    onSuggestionClick: (String) -> Unit,
    onDoneClick: (String) -> Unit,
    height: Dp,
    width: Dp,
    textStyle: TextStyle,
    placeholder: String,
    focusedField: Color,
    focusedBorderField: Color,
    focusedTextColor: Color,
    unfocusedField: Color,
    unfocusedBorderField: Color,
    unfocusedTextColor: Color,
    borderThickness: Dp,
    contentPadding: Dp,
    radiusShape:Dp,
    sizeIcon:Dp,
    bottomMenuHeight: Dp,
    colorIconSearch: Color,
) {
    var isFocusedIngredient by remember { mutableStateOf(false) }
    val colorField: Color
    val colorBorderField: Color
    if (state.text.isNotEmpty() || isFocusedIngredient) {
        colorField = focusedField
        colorBorderField = focusedBorderField
    } else {
        colorField = unfocusedField
        colorBorderField = unfocusedBorderField
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val suggestionsState = rememberSuggestionsState(items = ingredientsName)

    BasicTextField(
        state = state,
        modifier = Modifier
            .onFocusChanged { isFocusedIngredient = it.isFocused }
            .height(height)
            .width(width),
        enabled = true,
        inputTransformation = InputTransformation {
            if ( asCharSequence().isNotEmpty() && !asCharSequence().first().isLetter()) revertAllChanges()
        },
        textStyle = textStyle.copy(color = focusedTextColor),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        onKeyboardAction = KeyboardActionHandler {
            if (state.text.isNotEmpty()) {
                onDoneClick(state.text.toString().trim())
                keyboardController?.hide()
            }
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
                if (state.text.isNotEmpty() || isFocusedIngredient) {
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
                    Box(
                        modifier = Modifier.padding(start = contentPadding)
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
        val suggestions = suggestionsState.suggestions.collectAsStateWithLifecycle().value
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
                .padding(start = contentPadding, end = contentPadding)
                .animateContentSize(),
            verticalArrangement = Arrangement.Center
        ) {
            items(items = suggestions) { suggestion ->
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

    LaunchedEffect(state.text) {
        delay(300)
        withContext(Dispatchers.IO) {
            suggestionsState.suggestions.update {
                highlight(
                    color = unfocusedField,
                    query = state.text.toString().trim(),
                    items = ingredientsName,
                    matches = suggestionsState.findMatchingIndex(state.text.toString().trim())
                )
            }
        }
    }
}

@Composable
private fun rememberSuggestionsState(items: PersistentList<String>): SuggestionsState {
    val scope = rememberCoroutineScope()
    return remember { SuggestionsState(scope, items) }
}

private fun highlight(
    color: Color,
    query: String,
    items: List<String>,
    matches: Map<Int, List<Int>>
): List<AnnotatedString> {
    if (query.isEmpty()) return listOf()

    return matches
        .map { entry ->     // build list of annotated string from the index and character positions
            buildAnnotatedString {
                append(items[entry.key])
                entry.value.forEach {
                    addStyle(
                        style = SpanStyle(background = color),
                        start = it,
                        end = it + query.length
                    )
                }
            }
        }
}
