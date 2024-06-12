package com.example.idollapp.order

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.idollapp.database.AppDatabase
import com.example.idollapp.ui.base.BaseViewModel
import com.example.idollapp.ui.base.CommonViewModelFactory
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

@Composable
fun createOrderListViewModel(): OrderListViewModel {
    val context = LocalContext.current.applicationContext
    return viewModel(factory = CommonViewModelFactory {
        OrderListViewModel(OrderSourceLocal(AppDatabase.getInstance(context).orderDao()))
    })
}

class OrderListViewModel(private val repository: IOrderSource) : BaseViewModel() {

    fun getOrderList(status: IOrderStatus): Flow<PagingData<OrderListItem>> {
        return repository.getOrderList(status).cachedIn(viewModelScope)
    }

    fun updateReceived(item: OrderListItem): Flow<Boolean> {
        Timber.d("updateReceived : $item  ")
        return repository.updateStatus(item.id, IOrderStatus.WaitComment)
    }

}