package com.example.idollapp.store.index

import androidx.paging.PagingData
import com.example.idollapp.goods.Goods
import com.example.idollapp.goods.GoodsCategory
import kotlinx.coroutines.flow.Flow

interface IIndexStoreRepository {

    fun fetchCategories(): Flow<List<GoodsCategory>>

    fun fetchGoodsList(categoryId: String): Flow<PagingData<Goods>>

}