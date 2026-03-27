package com.alexit.justrecipes.ui.inputingrediets

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexit.justrecipes.ui.components.CustomDivider
import com.alexit.justrecipes.ui.components.SuggestionsState
import com.alexit.justrecipes.ui.components.dpToPx
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

@Composable
fun SuggestionsIngredientsShow(
    state: TextFieldState,
    suggestionsState: SuggestionsState,
    onSuggestionClick: (String) -> Unit,
    width: Dp,
    textStyle: TextStyle,
    colorField: Color,
    colorBorderField: Color,
    colorText: Color,
    colorSuggestion: Color,
    borderThickness: Dp,
    contentPadding: Dp,
    radiusShape:Dp,
    bottomMenuHeight: Dp,
){
    val suggestions = suggestionsState.suggestions.collectAsStateWithLifecycle().value
    LazyColumn(
        modifier = Modifier
            .consumeWindowInsets(paddingValues = PaddingValues(bottomMenuHeight))
            .imePadding()
            .width(width)
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
                color = { colorText }
            )
            CustomDivider(
                color = colorText,
                thickness = borderThickness.dpToPx(),
                startX = 0f,
                endX = (width - contentPadding * 2).dpToPx(),
                startY = 0f,
                endY = 0f
            )
        }
    }
    LaunchedEffect(state.text) {
        delay(300)
        withContext(Dispatchers.IO) {
            suggestionsState.suggestions.update {
                highlight(
                    color = colorSuggestion,
                    query = state.text.toString().trim(),
                    items = suggestionsState.items,
                    matches = suggestionsState.findMatchingIndex(state.text.toString().trim())
                )
            }
        }
    }
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