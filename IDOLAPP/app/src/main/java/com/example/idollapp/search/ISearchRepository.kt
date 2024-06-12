package com.example.idollapp.search

import androidx.paging.PagingData
import com.example.idollapp.goods.Goods
import kotlinx.coroutines.flow.Flow

interface ISearchRepository {

    fun search(key: String ): Flow<PagingData<SearchItem>>

}