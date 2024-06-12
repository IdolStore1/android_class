package com.example.idollapp.settlement

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.idollapp.database.AppDatabase
import com.example.idollapp.goods.CartItem
import com.example.idollapp.goods.UserAddress
import com.example.idollapp.http.repository.ProductRepository
import com.example.idollapp.ui.base.BaseViewModel
import com.example.idollapp.ui.base.CommonViewModelFactory
import com.example.idollapp.ui.base.LoadingData
import com.example.idollapp.utils.BuildParams
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun createSettlementViewModel(): SettlementViewModel {
    val context = LocalContext.current.applicationContext
    return viewModel(factory = CommonViewModelFactory {
        val settlementRepository = SettlementRepositoryLocal(
            AppDatabase.getInstance(context).userAddressDao(),
            AppDatabase.getInstance(context).shoppingCartDao(),
            AppDatabase.getInstance(context).orderDao(),
            ProductRepository()
        )
        when (BuildParams.getBuildType()) {
            com.example.idollapp.utils.BuildType.Dev -> SettlementViewModel(
                SettlementRepositoryLocalDebug(settlementRepository)
            )

            com.example.idollapp.utils.BuildType.Test -> SettlementViewModel(
                settlementRepository
            )
        }

    })
}

class SettlementViewModel(
    private val settlementRepository: ISettlementRepository
) : BaseViewModel() {

    private val _goodsList = MutableStateFlow<LoadingData<List<CartItem>>>(LoadingData.Loading())
    val goodsList: StateFlow<LoadingData<List<CartItem>>> = _goodsList

    private val _addressList = MutableStateFlow<List<UserAddress>>(listOf())
    val addressList: StateFlow<List<UserAddress>> = _addressList

    private val _selectedAddress = MutableStateFlow<UserAddress?>(null)
    val selectedAddress: StateFlow<UserAddress?> = _selectedAddress

    init {
        viewModelScope.launch { loadAddress() }
    }

    fun onSelectedAddress(userAddress: UserAddress) {
        viewModelScope.launch {
            _selectedAddress.emit(userAddress)
        }
    }

    private suspend fun loadAddress() {
        settlementRepository.loadAddress().collect {
            _addressList.emit(it)
            _selectedAddress.emit(it.firstOrNull())
        }
    }

    fun loadCartByGoodsId(goodsId: String) {
        viewModelScope.launch {
            settlementRepository.loadCartByGoodsId(goodsId).collect {
                _goodsList.emit(it)
            }
        }
    }

    fun loadCartFromDB(cartIds: List<String>) {
        viewModelScope.launch {
            settlementRepository.loadCartFromDBGoodsIds(cartIds).collect {
                _goodsList.emit(it)
            }
        }
    }

    fun submitOrder(cartItems: List<CartItem>): Flow<Int> {
        return settlementRepository.submitOrder(cartItems,selectedAddress.value)
    }

    fun clearShoppingCart(ids: List<String>): Flow<Boolean> {
        return settlementRepository.clearShoppingCart(ids)
    }
}
