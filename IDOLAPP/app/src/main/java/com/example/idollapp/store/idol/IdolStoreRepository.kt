package com.example.idollapp.store.idol

import androidx.paging.PagingData
import com.example.idollapp.goods.Goods
import com.example.idollapp.store.model.BannerEntity
import com.example.idollapp.store.model.IdolEntity
import kotlinx.coroutines.flow.Flow

interface IdolStoreRepository {

    fun fetchIdols(): Flow<PagingData<IdolEntity>>

    fun fetchBanners(id: String): Flow<List<BannerEntity>>

    fun fetchGoodsList(idolId: String): Flow<PagingData<Goods>>

}