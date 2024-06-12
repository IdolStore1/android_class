package com.example.idollapp.goods

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.idollapp.cart.CartRepositoryLocal
import com.example.idollapp.database.AppDatabase
import com.example.idollapp.http.repository.ProductRepository
import com.example.idollapp.ui.base.BaseViewModel
import com.example.idollapp.ui.base.CommonViewModelFactory
import com.example.idollapp.utils.BuildParams
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun createGoodsViewModel(): GoodsViewModel {
    val context = LocalContext.current.applicationContext
    val goodsDetailsRepositoryLocal = GoodsDetailsRepositoryLocal(
        ProductRepository(),
        AppDatabase.getInstance(context).goodsFavoriteDao(),
        CartRepositoryLocal(AppDatabase.getInstance(context).shoppingCartDao())
    )
    return viewModel(factory = CommonViewModelFactory {
        when (BuildParams.getBuildType()) {
            com.example.idollapp.utils.BuildType.Dev -> {
                GoodsViewModel(
                    GoodsDetailsRepositoryLocalDebug(goodsDetailsRepositoryLocal)
                )
            }

            com.example.idollapp.utils.BuildType.Test -> {
                GoodsViewModel(goodsDetailsRepositoryLocal)
            }
        }
    })
}

class GoodsViewModel(
    private val repository: IGoodsDetailsRepository
) : BaseViewModel() {

    private val goodsId = MutableStateFlow("")
    private val _goods = MutableStateFlow<GoodsDetails?>(null)
    val goods: StateFlow<GoodsDetails?> = _goods
    private val _goodsSku = MutableStateFlow<List<GoodsDetailsSku>>(emptyList())
    val goodsSku: StateFlow<List<GoodsDetailsSku>> = _goodsSku

    private val _goodsFavorite = MutableStateFlow<Boolean>(false)
    val goodsFavorite: StateFlow<Boolean> = _goodsFavorite


    private suspend fun checkFavorite() {
        val goodsIdValue = goodsId.value
        repository
            .getGoodsFavorite(goodsIdValue)
            .collect {
                _goodsFavorite.emit(it)
            }
    }

    private suspend fun fetchGoodsDetailsSku() {
        val goodsIdValue = goodsId.value
        repository.getGoodsDetailsSku(goodsIdValue)
            .collect {
                if (it.isNotEmpty()) {
                    _goodsSku.emit(it)
                }
            }
    }

    private suspend fun fetchGoodsDetails() {
        repository
            .getGoodsDetails(goodsId.value)
            .collect {
                if (it != null) {
                    _goods.emit(it)
                }
            }
    }

    fun setGoodsId(id: String) {
        viewModelScope.launch {
            goodsId.emit(id)
        }
        viewModelScope.launch {
            fetchGoodsDetails()
        }
        viewModelScope.launch {
            fetchGoodsDetailsSku()
        }
        viewModelScope.launch {
            checkFavorite()
        }
    }

    fun onClickFavorite() {
        viewModelScope.launch {
            repository
                .setGoodsFavorite(goodsId.value, goods.value)
                .collect {
                    checkFavorite()
                }
        }
    }

    fun addToCartClick(selectedSku: GoodsDetailsSku): Flow<Boolean> {
        return repository.addToCart(goodsId.value, goods.value!!,selectedSku)
    }

}