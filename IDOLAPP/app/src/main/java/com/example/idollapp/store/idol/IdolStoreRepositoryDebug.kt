package com.example.idollapp.store.idol

import androidx.paging.PagingData
import com.example.idollapp.goods.Goods
import com.example.idollapp.store.model.BannerEntity
import com.example.idollapp.store.model.IdolEntity
import com.example.idollapp.test.generateBanners
import com.example.idollapp.test.generateGoods
import com.example.idollapp.test.generateIdols
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class IdolStoreRepositoryDebug : IdolStoreRepository {
    override fun fetchIdols(): Flow<PagingData<IdolEntity>> {
        return flow {
            emit(PagingData.from(generateIdols()))
        }
    }

    override fun fetchBanners(id: String): Flow<List<BannerEntity>> {
        return flow {
            emit(generateBanners())
        }
    }


    override fun fetchGoodsList(idolId: String): Flow<PagingData<Goods>> {
        return flow {
            emit(PagingData.from(generateGoods()))
        }
    }

}