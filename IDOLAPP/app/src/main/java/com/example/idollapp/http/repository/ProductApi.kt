package com.example.idollapp.http.repository

import com.example.idollapp.http.ApiResponse
import com.example.idollapp.http.HttpConstants
import com.example.idollapp.http.bean.IdolListResponse
import com.example.idollapp.http.bean.IdolPhotosResponse
import com.example.idollapp.http.bean.Product
import com.example.idollapp.http.bean.ProductByIdolResponse
import com.example.idollapp.http.bean.ProductResponse
import com.example.idollapp.http.bean.SelectSkuSaleAttrResponse
import com.example.idollapp.http.bean.SkuInfoResponse
import com.example.idollapp.http.bean.SpuInfoResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ProductApi {

    @GET(HttpConstants.PRODUCT_BASE_URL + "product/skusaleattrvalue/selectSkuSaleAttr/{spuId}")
    suspend fun selectSkuSaleAttr(@Path("spuId") spuId: String): ApiResponse<SelectSkuSaleAttrResponse>

    @GET(HttpConstants.PRODUCT_BASE_URL + "product/spuinfo/getSpuInfo")
    suspend fun getSpuInfo(@QueryMap params: HashMap<String, Any>): ApiResponse<SpuInfoResponse>

    @POST(HttpConstants.PRODUCT_BASE_URL + "product/skuinfo/getSkuInfo")
    suspend fun getSkuInfo(@Body params: HashMap<String, @JvmSuppressWildcards Any>): ApiResponse<SkuInfoResponse>

    @GET(HttpConstants.PRODUCT_BASE_URL + "product/spuinfo/getProductByIdolId")
    suspend fun getProductByIdolId(@QueryMap params: HashMap<String, Any>): ApiResponse<ProductByIdolResponse>

    @GET(HttpConstants.PRODUCT_BASE_URL + "product/idolinfo/getIdolList")
    suspend fun getIdolList(@QueryMap params: HashMap<String, Any>): ApiResponse<IdolListResponse>

    @GET(HttpConstants.PRODUCT_BASE_URL + "product/spuinfo/getProductList")
    suspend fun getProductList(@QueryMap params: HashMap<String, Any>): ApiResponse<ProductResponse>

    @POST(HttpConstants.PRODUCT_BASE_URL + "product/spuinfo/search")
    suspend fun search(@Body params: HashMap<String, @JvmSuppressWildcards Any>): ApiResponse<ProductResponse>

    @GET(HttpConstants.PRODUCT_BASE_URL + "product/idolinfo/getIdolPhotos/{idolId}")
    suspend fun getIdolPhotos(@Path("idolId") idolId: String): ApiResponse<IdolPhotosResponse>
}
