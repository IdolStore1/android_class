package com.example.idollapp.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.idollapp.ui.view.rememberBooleanState
import com.example.idollapp.ui.view.rememberStringState
import timber.log.Timber

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchBarLayout(key: String = "", onSearch: (key: String) -> Unit) {
    val search = rememberStringState(key)
    val active = rememberBooleanState()
    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        query = search.get(),
        onQueryChange = {
            Timber.d(" search bar on query change : $it")
            search.update(it)
        },
        onSearch = {
            Timber.d(" search bar on search : $it")
            search.update(it)
            onSearch(it)
            active.beFalse()
        },
        active = active.get(),
        onActiveChange = {
            active.update(it)
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "")
        },
        trailingIcon = {
            if (search.get().isNotEmpty()) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = "",
                    modifier = Modifier.clickable {
                        search.update("")
                        active.beFalse()
                    })
            }
        },
        placeholder = { Text(text = "Search") },
        colors = SearchBarDefaults.colors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
        shape = RoundedCornerShape(50)
    ) {
        // search layout
    }
}
