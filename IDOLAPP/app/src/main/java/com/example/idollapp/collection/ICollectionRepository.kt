package com.example.idollapp.collection

import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import kotlinx.coroutines.flow.Flow

interface ICollectionRepository {

    fun getCollectionList(): Flow<PagingData<CollectionGoodsListItem>>

    suspend  fun  removeFromCollection(goods: CollectionGoodsListItem)

    suspend fun addToShoppingCart(goods: CollectionGoodsListItem)

}