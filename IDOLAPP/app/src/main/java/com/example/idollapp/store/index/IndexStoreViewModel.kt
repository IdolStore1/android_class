package com.example.idollapp.store.index

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.idollapp.goods.Goods
import com.example.idollapp.goods.GoodsCategory
import com.example.idollapp.http.repository.ProductRepository
import com.example.idollapp.ui.base.BaseViewModel
import com.example.idollapp.ui.base.CommonViewModelFactory
import com.example.idollapp.utils.BuildParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@Composable
fun createIndexStoreViewModel(): IndexStoreViewModel {
    val context = LocalContext.current.applicationContext
    return viewModel(factory = CommonViewModelFactory {
        when (BuildParams.getBuildType()) {
            com.example.idollapp.utils.BuildType.Dev -> {
                IndexStoreViewModel(IndexStoreRepositoryDebug())
            }

            com.example.idollapp.utils.BuildType.Test -> {
                IndexStoreViewModel(IndexStoreRepositoryRemote(ProductRepository()))
            }
        }
    })
}

class IndexStoreViewModel(private val repository: IIndexStoreRepository) : BaseViewModel() {

    private val _selectedCategory = MutableStateFlow<GoodsCategory?>(null)
    val selectedCategory: StateFlow<GoodsCategory?> = _selectedCategory

    private val _categories = MutableStateFlow(listOf<GoodsCategory>())
    val categories = _categories

    val goodsList: StateFlow<PagingData<Goods>> = _selectedCategory
        .debounce(300)
        .distinctUntilChanged()
        .filterNotNull()
        .flatMapLatest { query -> repository.fetchGoodsList(query.id).cachedIn(viewModelScope) }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchCategories()
                .collect {
                    _categories.emit(it)
                    _selectedCategory.emit(it.first())
                }
        }
    }

    fun setCategory(newCategory: GoodsCategory) {
        _selectedCategory.value = newCategory
    }

}