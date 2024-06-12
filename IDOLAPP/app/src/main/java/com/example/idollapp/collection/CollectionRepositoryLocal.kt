package com.example.idollapp.collection

import androidx.paging.PagingData
import com.example.idollapp.database.GoodsFavoriteDao
import com.example.idollapp.database.ShoppingCart
import com.example.idollapp.database.ShoppingCartDao
import com.example.idollapp.ui.base.paging.GenericPagingRepository
import com.example.idollapp.user.usermanager.AppUserManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CollectionRepositoryLocal(
    private val goodsFavoriteDao: GoodsFavoriteDao,
    private val shoppingCartDao: ShoppingCartDao
) : ICollectionRepository {
    override fun getCollectionList(): Flow<PagingData<CollectionGoodsListItem>> {
        val userInfo = AppUserManager.instance().getUserInfo()
        val userId = userInfo?.id ?: ""
        return GenericPagingRepository { page, size ->
            withContext(Dispatchers.IO) {
                goodsFavoriteDao.getAllGoodsByUserId(userId).map { goodsFavorite ->
                    CollectionGoodsListItem(
                        id = goodsFavorite.id,
                        goodsId = goodsFavorite.goodsId,
                        title = goodsFavorite.title,
                        imageUrl = goodsFavorite.imageUrl,
                        price = goodsFavorite.price
                    )
                }
            }
        }.getPagingData()
    }

    override suspend fun removeFromCollection(goods: CollectionGoodsListItem) {
        goodsFavoriteDao.removeFromCollection(goods.toFavorite())
    }

    override suspend fun addToShoppingCart(goods: CollectionGoodsListItem) {
        val userInfo = AppUserManager.instance().getUserInfo()
        val userId = userInfo?.id ?: ""
        shoppingCartDao.findByGoodsIdUserId(goods.goodsId, userId)?.let {
            shoppingCartDao.updateCartItems(listOf(it.copy(productQuantity = it.productQuantity + 1)))
        } ?: let {
            shoppingCartDao.insertCartItems(
                listOf(
                    ShoppingCart(
                        0, userId, goods.goodsId, goods.title, goods.price, goods.imageUrl, 1,
                    )
                )
            )
        }
    }
}