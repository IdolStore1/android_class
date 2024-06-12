package com.example.idollapp.test

import com.example.idollapp.collection.CollectionGoodsListItem
import com.example.idollapp.goods.CartItem
import com.example.idollapp.goods.Goods
import com.example.idollapp.goods.GoodsCategory
import com.example.idollapp.goods.GoodsComment
import com.example.idollapp.goods.GoodsDetails
import com.example.idollapp.goods.GoodsDetailsImage
import com.example.idollapp.goods.GoodsDetailsSku
import com.example.idollapp.goods.GoodsModel
import com.example.idollapp.goods.UserAddress
import com.example.idollapp.order.OrderListItem
import com.example.idollapp.order.randomStatus
import com.example.idollapp.search.SearchItem
import com.example.idollapp.store.model.BannerEntity
import com.example.idollapp.store.model.IdolEntity
import java.util.Locale
import kotlin.random.Random


fun generateCategories(size: Int = 20): List<GoodsCategory> {
    var id = 0
    val random = Random(System.currentTimeMillis())
    return List(size) {
        GoodsCategory(
            id = "${id++}",
            testCategorys.random()
        )
    }
}

fun generateGoods(size: Int = 50): List<Goods> {
    var id = 0
    val random = Random(System.currentTimeMillis())
    return List(size) {
        Goods(
            id = "${id++}",
            name = testGoodsNames.random(),
            price = testGoodsPrices.random(),
            sold = random.nextInt(1000),
            pic = randomGoodsPic(),
        )
    }
}

fun generateSearchItems(size: Int = 50): List<SearchItem> {
    var id = 0
    return List(size) {
        SearchItem(
            id = "${id++}",
            name = testGoodsNames.random(),
            price = String.format(Locale.getDefault(), "%.2f", testGoodsPrices.random()),
            image = randomGoodsPic(),
        )
    }
}

fun generateCartItems(size: Int = 50): List<CartItem> {
    var id = 0
    val random = Random(System.currentTimeMillis())
    return List(size) {
        CartItem(
            id = id++,
            quantity = random.nextInt(10),
            item = Goods(
                id = "${id++}",
                name = testGoodsNames.random(),
                price = testGoodsPrices.random(),
                sold = random.nextInt(1000),
                pic = randomGoodsPic(),
            ),
            skuName = ""
        )
    }
}

fun generateAddress(size: Int = 50): List<UserAddress> {
    var id = 0
    return List(size) {
        UserAddress(
            id = id++,
            testUserName.random(),
            testUserPhone.random(),
            testAddress.random(),
        )
    }
}

fun generateCollectionItems(size: Int = 50): List<CollectionGoodsListItem> {
    var id = 0
    return List(size) {
        CollectionGoodsListItem(
            id = id++,
            goodsId = "${id++}",
            title = testGoodsNames.random(),
            price = testGoodsPrices.random(),
            imageUrl = randomGoodsPic(),
        )
    }
}


fun generateOrderItems(size: Int = 50): List<OrderListItem> {
    var id = 0
    return List(size) {
        OrderListItem(
            id = "${id++}",
            sn = "${id++}",
            imageUrl = randomGoodsPic(),
            title = testGoodsNames.random(),
            price = "${testGoodsPrices.random()}",
            freight = "${testGoodsPrices.random()}",
            remark = testRemark.random(),
            status = randomStatus(),
        )
    }
}

fun generateIdols(size: Int = 50): List<IdolEntity> {
    var id = 0
    return List(size) {
        IdolEntity(
            id = "${id++}",
            name = testUserName.random(),
            avatar = randomGoodsPic(),
        )
    }
}


fun generateBanners(size: Int = 50): List<BannerEntity> {
    var id = 0
    return List(size) {
        BannerEntity(
            id = "${id++}",
            pic = randomGoodsPic(),
        )
    }
}


fun generateGoodsDetails(): GoodsDetails {
    return GoodsDetails(
        id = Random.nextInt(100).toString(),
        name = testGoodsNames.random(),
        desc = testGoodsNames.random(),
        price = testGoodsPrices.random(),
        sold = 100,
        pic = listOf(
            GoodsDetailsImage(
                imgName = "1",
                imgUrl = randomGoodsPic(),
                defaultImg = 1,
            )
        ),
    )
}

fun generateGoodsDetailsComment(size: Int = 50): List<GoodsComment> {
    return List(size) {
        GoodsComment(
            id = it.toString(),
            user = testUserName.random(),
            content = testRemark.random(),
            time = testDate.random(),
        )
    }
}

fun generateGoodsDetailsModel(size: Int = 50): List<GoodsModel> {
    return List(size) {
        GoodsModel(
            id = it.toString(),
            name = testGoodsNames.random(),
            price = testGoodsPrices.random(),
            pic = randomGoodsPic(),
        )
    }
}

fun generateGoodsDetailsSku(size: Int = 50): List<GoodsDetailsSku> {
    return List(size) {
        GoodsDetailsSku(
            id = it.toString(),
            name = testGoodsNamesSku.random(),
            attrValue = "",
            attrValues = listOf()
        )
    }
}


fun generateGoodsDetailsImgs(size: Int = 50): List<GoodsDetailsImage> {
    return List(size) {
        GoodsDetailsImage(
            imgName = it.toString(),
            imgUrl = randomGoodsPic(),
            0
        )
    }
}




