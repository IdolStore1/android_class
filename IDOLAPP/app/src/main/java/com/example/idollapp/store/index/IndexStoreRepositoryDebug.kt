package com.example.idollapp.store.index

import androidx.paging.PagingData
import com.example.idollapp.goods.Goods
import com.example.idollapp.goods.GoodsCategory
import com.example.idollapp.test.generateGoods
import com.example.idollapp.test.testCategorys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class IndexStoreRepositoryDebug : IIndexStoreRepository {

    override fun fetchCategories(): Flow<List<GoodsCategory>> {
        return flow {
            testCategorys.map {
                GoodsCategory(
                    id = it,
                    name = it
                )
            }.let {
                emit(it)
            }
        }
    }

    override fun fetchGoodsList(categoryId: String): Flow<PagingData<Goods>> {
        return flow {
            emit(PagingData.from(generateGoods()))
        }
    }

}