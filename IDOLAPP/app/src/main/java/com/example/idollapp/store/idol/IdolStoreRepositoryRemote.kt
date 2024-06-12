package com.example.idollapp.store.idol

import androidx.paging.PagingData
import com.example.idollapp.goods.Goods
import com.example.idollapp.http.repository.ProductRepository
import com.example.idollapp.store.model.BannerEntity
import com.example.idollapp.store.model.IdolEntity
import com.example.idollapp.ui.base.paging.GenericPagingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class IdolStoreRepositoryRemote(private val apiService: ProductRepository) :
    IdolStoreRepository {
    override fun fetchIdols(): Flow<PagingData<IdolEntity>> {
      return GenericPagingRepository { page, size ->
          val orNull =
              apiService.getIdolList( page.toString(), size.toString()).getOrThrow()
          if (orNull.isSuccess) {
              orNull.data.idolList.map { product ->
                  IdolEntity(
                      id = product.idolId.toString(),
                      name = product.idolName,
                      avatar = product.images
                  )
              }
          } else {
              emptyList()
          }
      }.getPagingData()
    }

    override fun fetchBanners(id: String): Flow<List<BannerEntity>> {
        return flow {
            val orThrow = apiService.getIdolPhotos(id).getOrThrow()
            if (orThrow.isSuccess) {
                orThrow.data.photoList.map {
                    BannerEntity(id = "", it)
                }.apply { emit(this) }
            } else {
                emit(emptyList())
            }
        }
    }


    override fun fetchGoodsList(idolId: String): Flow<PagingData<Goods>> {
        return GenericPagingRepository { page, size ->
            val orNull =
                apiService.getProductByIdolId(idolId, page.toString(), size.toString()).getOrThrow()
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