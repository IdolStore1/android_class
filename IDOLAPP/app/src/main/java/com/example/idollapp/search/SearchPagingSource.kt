package com.example.idollapp.search

import androidx.paging.PagingData
import com.example.idollapp.http.repository.ProductRepository
import com.example.idollapp.test.generateSearchItems
import com.example.idollapp.ui.base.paging.GenericPagingRepository
import com.example.idollapp.utils.toFormatString
import kotlinx.coroutines.flow.Flow
import java.util.Locale

class SearchPagingRepositoryDebug : ISearchRepository {
    override fun search(key: String): Flow<PagingData<SearchItem>> {
        return GenericPagingRepository { page, size ->
            generateSearchItems()
        }.getPagingData()
    }
}

class SearchPagingRepositoryRemote(private val repository: ProductRepository) : ISearchRepository {
    override fun search(key: String): Flow<PagingData<SearchItem>> {
        return GenericPagingRepository { page, size ->
            val orThrow = repository.search(key, 1, "").getOrThrow()
            if (orThrow.isSuccess) {
                orThrow.data.products.map { product ->
                    SearchItem(
                        id = product.id.toString(),
                        name = product.spuName,
                        price = product.defaultPrice.toFormatString(),
                        image = product.defaultImgUrl,
                    )
                }
            } else {
                emptyList()
            }
        }.getPagingData()
    }
}
