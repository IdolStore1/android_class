package com.example.idollapp.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.idollapp.http.repository.ProductRepository
import com.example.idollapp.ui.base.BaseViewModel
import com.example.idollapp.ui.base.CommonViewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

@Composable
fun createSearchViewModel(): SearchViewModel {
    val context = LocalContext.current.applicationContext
    return viewModel(factory = CommonViewModelFactory {
        SearchViewModel(SearchPagingRepositoryRemote(ProductRepository()))
    })
}

class SearchViewModel(private val repository: ISearchRepository) : BaseViewModel() {
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    val searchResults: StateFlow<PagingData<SearchItem>> = _query
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query -> repository.search(query).cachedIn(viewModelScope) }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    fun setQuery(newQuery: String) {
        _query.value = newQuery
    }

}
