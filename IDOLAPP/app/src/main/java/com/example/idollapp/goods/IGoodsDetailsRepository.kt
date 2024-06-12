package com.example.idollapp.goods

import kotlinx.coroutines.flow.Flow

interface IGoodsDetailsRepository {

    fun getGoodsDetails(goodsId: String): Flow<GoodsDetails?>

    fun getGoodsDetailsSku(goodsId: String): Flow<List<GoodsDetailsSku>>

    fun getGoodsFavorite(goodsId: String): Flow<Boolean>

    fun setGoodsFavorite(goodsId: String, goods: GoodsDetails?): Flow<Boolean>

    fun addToCart(goodsId: String, goodsDetails: GoodsDetails,selectedSku: GoodsDetailsSku): Flow<Boolean>

}