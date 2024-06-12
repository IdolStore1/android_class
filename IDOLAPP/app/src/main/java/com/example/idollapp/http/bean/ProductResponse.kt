package com.example.idollapp.http.bean


data class SelectSkuSaleAttrResponse(
    val SpuAttrValueVo: List<SpuAttrValueVo>,
)

data class SpuAttrValueVo(
    val attrId: Int,
    val attrName: String,
    val temAttrValue: String,
    val attrValue: List<String>
)

data class SpuInfoResponse(
    val spuInfo: SpuInfo
)

data class SpuInfo(
    val id: Int,
    val spuName: String,
    val spuDescription: String,
    val images: List<Image>,
    val idol_id: Int,
    val count: Int,
    val defaultPrice: Double
)

data class Image(
    val imgName: String,
    val imgUrl: String,
    val defaultImg: Int
)

data class SkuInfoResponse(
    val code: Int,
    val SkuInfo: SkuInfo
)

data class SkuInfo(
    val id: Int,
    val imageUrl: String,
    val hasStock: Boolean,
    val stock: Int,
    val price: Int
)

data class AttrItem(
    val attrId: Int,
    val attrName: String,
    val attrValue: String
)

data class ProductByIdolResponse(
    val code: Int,
    val products: List<Product>
)

data class Product(
    val id: Int,
    val defaultPrice: Float,
    val count: Int,
    val spuName: String,
    val defaultImgUrl: String
)

data class IdolListResponse(
    val idolList: List<Idol>
)

data class Idol(
    val idolId: Int,
    val idolName: String,
    val images: String
)

data class IdolPhotosResponse(
    val photoList: List<String>
)

data class ProductResponse(
    val products: List<Product>
)

