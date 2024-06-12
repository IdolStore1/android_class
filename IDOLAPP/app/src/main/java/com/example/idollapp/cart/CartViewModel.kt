package com.example.idollapp.cart

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.idollapp.database.AppDatabase
import com.example.idollapp.goods.CartItem
import com.example.idollapp.ui.base.BaseViewModel
import com.example.idollapp.ui.base.CommonViewModelFactory
import com.example.idollapp.ui.base.LoadingData
import com.example.idollapp.ui.base.emitSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun createShoppingCartViewModel(): ShoppingCartViewModel {
    val context = LocalContext.current.applicationContext
    return viewModel(factory = CommonViewModelFactory {
        ShoppingCartViewModel(
            CartRepositoryLocal(
                AppDatabase.getInstance(context).shoppingCartDao()
            )
        )
    })
}

class ShoppingCartViewModel(private val repository: ICartRepository) : BaseViewModel() {

    private val _cartItemsFlow =
        MutableStateFlow<LoadingData<List<CartItem>>>(LoadingData.Loading())

    init {
        notifyCartItemsChanged()
    }

    private fun notifyCartItemsChanged() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchCartItems().collect {
                _cartItemsFlow.emitSuccess(it)
            }
        }
    }

    fun addToCart(goods: CartItem) {
        viewModelScope.launch {
            repository.addToCart(goods)
            notifyCartItemsChanged()
        }
    }

    fun removeFromCart(goods: CartItem) {
        viewModelScope.launch(Dispatchers.IO){
            repository.removeFromCart(goods)
            notifyCartItemsChanged()
        }
    }

    fun removeGoodsFromCart(cart: CartItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeGoodsFromCart(cart)
            notifyCartItemsChanged()
        }
    }

    fun getCartItems(): StateFlow<LoadingData<List<CartItem>>> {
        return _cartItemsFlow
    }

    fun getTotalQuantity(): Flow<Int> {
        return getCartItems().map {
            when (it) {
                is LoadingData.Error -> 0
                is LoadingData.Loading -> 0
                is LoadingData.Success -> it.getValue().sumOf { it.quantity }
            }
        }
    }

    fun getTotalPrice(): Flow<Double> {
        return getCartItems().map {
            when (it) {
                is LoadingData.Error -> 0.0
                is LoadingData.Loading -> 0.0
                is LoadingData.Success -> it.getValue()
                    .sumOf { it.item.price.toDouble() * it.quantity }
            }
        }
    }

}