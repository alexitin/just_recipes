package com.alexit.justrecipes.ui.components

import androidx.compose.ui.text.AnnotatedString
import com.alexit.justrecipes.utility.GSuffArray
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SuggestionsState(scope: CoroutineScope, val items: List<String>) {
//class SuggestionsState(val items: List<String>) {
    private lateinit var gsa: GSuffArray
    val suggestions: MutableStateFlow<List<AnnotatedString>> = MutableStateFlow(emptyList())

    init {
        scope.launch {
            withContext(Dispatchers.Default) {
                gsa = GSuffArray(*items.toTypedArray())
            }
        }
    }

    fun findMatchingIndex(query: String): Map<Int, List<Int>> {
        if (query.isEmpty()) return emptyMap()

        return gsa.index(query)
            .groupBy { (_, itemIndex) -> itemIndex } // group by index to yield a map of index to a list of index and character positions
            .mapValues { (_, list) -> list.map { (start, _) -> start } } // generate new map of index(key) to list of character positions (Values)
    }
}