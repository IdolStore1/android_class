package com.example.idollapp.goods

import com.example.idollapp.test.generateGoodsDetails
import com.example.idollapp.test.generateGoodsDetailsSku
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GoodsDetailsRepositoryLocalDebug(
    private val repository: IGoodsDetailsRepository
) : IGoodsDetailsRepository by repository {

    override fun getGoodsDetails(goodsId: String): Flow<GoodsDetails?> {
        return flow {
            emit(generateGoodsDetails())
        }
    }

    override fun getGoodsDetailsSku(goodsId: String): Flow<List<GoodsDetailsSku>> {
        return flow {
            emit(generateGoodsDetailsSku())
        }
    }
}