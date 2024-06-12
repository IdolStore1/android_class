package com.example.idollapp.store.index

import androidx.paging.PagingData
import com.example.idollapp.goods.Goods
import com.example.idollapp.goods.GoodsCategory
import com.example.idollapp.http.repository.ProductRepository
import com.example.idollapp.test.generateCategories
import com.example.idollapp.ui.base.paging.GenericPagingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class IndexStoreRepositoryRemote(private val apiService: ProductRepository) :
    IIndexStoreRepository {
    override fun fetchCategories(): Flow<List<GoodsCategory>> {
        return flow {
            emit(generateCategories())
        }
    }

    override fun fetchGoodsList(categoryId: String): Flow<PagingData<Goods>> {
        return GenericPagingRepository { page, size ->
            val orNull = apiService.getProductList(page, size).getOrThrow()
            if (orNull.isSuccess) {
                orNull.data.products.map { product ->
                    Goods(
                        product.id.toString(),
                        product.spuName,
                        product.defaultPrice,
                        product.count,
                        product.defaultImgUrl,
                    )
                }
            } else {
                emptyList()
            }
        }.getPagingData()
    }


}