package com.example.idollapp.http.repository

import com.example.idollapp.http.ApiResponse
import com.example.idollapp.http.ApiWrapper
import com.example.idollapp.http.bean.AttrItem
import com.example.idollapp.http.bean.IdolListResponse
import com.example.idollapp.http.bean.IdolPhotosResponse
import com.example.idollapp.http.bean.Product
import com.example.idollapp.http.bean.ProductByIdolResponse
import com.example.idollapp.http.bean.ProductResponse
import com.example.idollapp.http.bean.SelectSkuSaleAttrResponse
import com.example.idollapp.http.bean.SkuInfoResponse
import com.example.idollapp.http.bean.SpuInfoResponse

class ProductRepository {

    private fun createApi(): ProductApi {
        return ApiWrapper.create(ProductApi::class.java)
    }

    suspend fun selectSkuSaleAttr(spuId: String): Result<ApiResponse<SelectSkuSaleAttrResponse>> {
        return kotlin.runCatching { createApi().selectSkuSaleAttr(spuId) }
    }

    suspend fun getSpuInfo(
        spuId: String? = null,
        userId: String? = null
    ): Result<ApiResponse<SpuInfoResponse>> {
        val params = HashMap<String, Any>().apply {
            spuId?.let { put("spuId", it) }
            userId?.let { put("userId", it) }
        }
        return kotlin.runCatching { createApi().getSpuInfo(params) }
    }

    suspend fun getSkuInfo(attrList: List<AttrItem>): Result<ApiResponse<SkuInfoResponse>> {
        val params = HashMap<String, Any>().apply {
            put("attrList", attrList)
        }
        return kotlin.runCatching { createApi().getSkuInfo(params) }
    }

    suspend fun getProductByIdolId(
        idolId: String? = null,
        pageNum: String? = null,
        pageSize: String? = null
    ): Result<ApiResponse<ProductByIdolResponse>> {
        val params = HashMap<String, Any>().apply {
            idolId?.let { put("idolId", it) }
            pageNum?.let { put("pageNum", it) }
            pageSize?.let { put("pageSize", it) }
        }
        return kotlin.runCatching { createApi().getProductByIdolId(params) }
    }

    suspend fun getIdolList(
        pageNum: String? = null,
        pageSize: String? = null
    ): Result<ApiResponse<IdolListResponse>> {
        val params = HashMap<String, Any>().apply {
            pageNum?.let { put("pageNum", it) }
            pageSize?.let { put("pageSize", it) }
        }
        return kotlin.runCatching { createApi().getIdolList(params) }
    }

    suspend fun getProductList(
        pageNum: Int? = null,
        pageSize: Int? = null
    ): Result<ApiResponse<ProductResponse>> {
        val params = HashMap<String, Any>().apply {
            pageNum?.let { put("pageNum", it) }
            pageSize?.let { put("pageSize", it) }
        }
        return kotlin.runCatching { createApi().getProductList(params) }
    }

    suspend fun search(
        searchText: String,
        count: Int,
        priceRange: String
    ): Result<ApiResponse<ProductResponse>> {
        val params = HashMap<String, Any>().apply {
            put("searchText", searchText)
            put("count", count)
            put("priceRange", priceRange)
        }
        return kotlin.runCatching { createApi().search(params) }
    }

    suspend fun getIdolPhotos(idolId: String): Result<ApiResponse<IdolPhotosResponse>> {
        return kotlin.runCatching { createApi().getIdolPhotos(idolId) }
    }

}
