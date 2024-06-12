package com.example.idollapp.goods

import com.example.idollapp.database.ShoppingCart
import com.example.idollapp.database.UserAddressEntity
import com.example.idollapp.http.bean.SpuInfo


fun UserAddressEntity.toUI(): UserAddress {
    return UserAddress(id, name, phone, address)
}

fun SpuInfo.toGoodsDetails(): GoodsDetails {
    return GoodsDetails(
        id = id.toString(),
        name = spuName,
        price = defaultPrice.toFloat(),
        sold = count,
        pic = images.map {
            GoodsDetailsImage(it.imgName, it.imgUrl, it.defaultImg)
        },
        desc = spuDescription
    )
}

fun SpuInfo.toCartItem(): CartItem {
    return CartItem(
        id = id,
        item = Goods(
            id = id.toString(),
            name = spuName,
            price = defaultPrice.toFloat(),
            sold = count,
            pic = images.first().imgUrl
        ),
        quantity = 1,
        skuName = ""
    )
}

fun ShoppingCart.toCartItem(): CartItem {
    return CartItem(
        id = id,
        item = Goods(
            id = productId,
            name = productName,
            price = productPrice,
            sold = 0,
            pic = productImage
        ),
        quantity = productQuantity,
        skuName = ""
    )
}

fun CartItem.toDb(userId: String): ShoppingCart {
    return ShoppingCart(
        id = id,
        userId = userId,
        productId = item.id,
        productName = item.name,
        productPrice = item.price,
        productImage = item.pic,
        productQuantity = quantity,
//        productSkuName = skuName,
    )
}

data class GoodsCategory(val id: String, val name: String)


data class Goods(
    val id: String,
    val name: String,
    val price: Float,
    val sold: Int,
    val pic: String,
)


data class GoodsDetailsImage(
    val imgName: String,
    val imgUrl: String,
    val defaultImg: Int
)


data class GoodsDetails(
    val id: String,
    val name: String,
    val desc: String,
    val price: Float,
    val sold: Int,
    val pic: List<GoodsDetailsImage>,
)

fun GoodsDetails.toGoods(): Goods {
    return Goods(id, name, price, sold, pic.first().imgUrl)
}


data class GoodsDetailsSku(
    val id: String,
    val name: String,
    val attrValue: String,
    val attrValues: List<String>,
)


data class CartItem(val id: Int, val item: Goods, val quantity: Int, val skuName: String)

data class UserAddress(val id: Int, val name: String, val phone: String, val address: String)

data class GoodsComment(val id: String, val user: String, val content: String, val time: String)

data class GoodsModel(val id: String, val name: String, val price: Float, val pic: String)