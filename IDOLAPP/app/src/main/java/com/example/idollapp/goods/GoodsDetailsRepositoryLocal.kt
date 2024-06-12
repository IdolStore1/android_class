package com.example.idollapp.goods

import com.example.idollapp.cart.ICartRepository
import com.example.idollapp.database.GoodsFavorite
import com.example.idollapp.database.GoodsFavoriteDao
import com.example.idollapp.http.repository.ProductRepository
import com.example.idollapp.user.usermanager.AppUserManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class GoodsDetailsRepositoryLocal(
    private val repository: ProductRepository,
    private val favoriteDao: GoodsFavoriteDao,
    private val cartRepository: ICartRepository
) : IGoodsDetailsRepository {

    private val userId = AppUserManager.instance().getUserInfo()?.id ?: ""

    override fun getGoodsDetails(goodsId: String): Flow<GoodsDetails?> {
        return flow {
            repository.getSpuInfo(goodsId, userId)
                .onSuccess {
                    if (it.isSuccess) {
                        val data = it.data.spuInfo
                        emit(data.toGoodsDetails())
                    } else {
                        Timber.e("error msg : ${it.message}")
                    }
                }.onFailure {
                    it.printStackTrace()
                }
        }
    }

    override fun getGoodsDetailsSku(goodsId: String): Flow<List<GoodsDetailsSku>> {
        return flow {
            repository.selectSkuSaleAttr(goodsId).onSuccess {
                if (it.isSuccess) {
                    it.data.SpuAttrValueVo.map {
                        GoodsDetailsSku(
                            id = it.attrId.toString(),
                            name = it.attrName,
                            attrValue = it.temAttrValue,
                            attrValues = it.attrValue
                        )
                    }.apply {
                        emit(this)
                    }
                } else {
                    Timber.e("error msg : ${it.message}")
                }
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    override fun getGoodsFavorite(goodsId: String): Flow<Boolean> {
        return flow {
            favoriteDao.findByGoodsIdUserId(goodsId, userId)?.let {
                emit(true)
            } ?: let {
                emit(false)
            }
        }
    }

    override fun setGoodsFavorite(
        goodsId: String,
        goods: GoodsDetails?
    ): Flow<Boolean> {
        return flow {
            favoriteDao.findByGoodsIdUserId(goodsId, userId)?.let {
                favoriteDao.delete(it)
            } ?: let {
                favoriteDao.insert(
                    GoodsFavorite(
                        0,
                        goodsId = goodsId,
                        userId = userId,
                        createTime = System.currentTimeMillis(),
                        title = goods?.name ?: "",
                        imageUrl = goods?.pic?.firstOrNull()?.imgUrl ?: "",
                        price = goods?.price ?: 0f
                    )
                )
            }
            emit(true)
        }
    }

    override fun addToCart(
        goodsId: String,
        goodsDetails: GoodsDetails,
        selectedSku: GoodsDetailsSku
    ): Flow<Boolean> {
        return flow {
            cartRepository.addToCart(goodsDetails.toGoods(), selectedSku)
            emit(true)
        }
    }
}