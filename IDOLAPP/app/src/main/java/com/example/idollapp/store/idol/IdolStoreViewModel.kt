package com.example.idollapp.store.idol

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.idollapp.http.repository.ProductRepository
import com.example.idollapp.store.model.IdolEntity
import com.example.idollapp.ui.base.BaseViewModel
import com.example.idollapp.ui.base.CommonViewModelFactory
import com.example.idollapp.utils.BuildParams
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

@Composable
fun createIdolStoreViewModel(): IdolStoreViewModel {
    val context = LocalContext.current.applicationContext
    return viewModel(factory = CommonViewModelFactory {
        when (BuildParams.getBuildType()) {
            com.example.idollapp.utils.BuildType.Dev -> {
                IdolStoreViewModel(IdolStoreRepositoryDebug())
            }

            com.example.idollapp.utils.BuildType.Test -> {
                IdolStoreViewModel(IdolStoreRepositoryRemote(ProductRepository()))
            }
        }

    })
}

// 仿照 IndexStoreViewModel ，再根据 IdolStoreRepository 实现功能
class IdolStoreViewModel(private val repository: IdolStoreRepository) : BaseViewModel() {

    private val _selectedIdol = MutableStateFlow<IdolEntity?>(null)
    val selectedIdol: StateFlow<IdolEntity?> = _selectedIdol

    val idols = repository
        .fetchIdols()
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    val goodsList = _selectedIdol
        .debounce(100)
        .distinctUntilChanged()
        .filterNotNull()
        .flatMapLatest { idol -> repository.fetchGoodsList(idol.id).cachedIn(viewModelScope) }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    val banners = _selectedIdol
        .debounce(100)
        .distinctUntilChanged()
        .filterNotNull()
        .flatMapLatest { idol -> repository.fetchBanners(idol.id) }

    fun setIdol(newIdol: IdolEntity) {
        _selectedIdol.value = newIdol
    }

}
