package com.example.idollapp.http

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import java.nio.charset.StandardCharsets


class UserTokenInterceptor(private val userTokenStorage: ITokenStorage) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        addAuthToken(request)
        val build = request.build()
        val proceed = chain.proceed(build)

        val loginExpired = proceed.code == 403
        Timber.d(" response status code : ${proceed.code}")

        val originBodyString = proceed.body?.source()?.apply { request(Long.MAX_VALUE) }
            ?.buffer?.clone()?.readString(StandardCharsets.UTF_8)

        // 这里为了不用进行重复解析 json，直接使用字符串来判断了，简单直接
        val isSuccess = originBodyString?.contains("\"code\":1") ?: false

        return when {
            loginExpired -> {
                // 应该打开登录界面的
                proceed
            }
            isSuccess -> { // 这个判断不要做比较好，因为无论接口返回 成功与否，都应该是调用者处理。
                proceed
            }

            else -> {
                Timber.v("other error : ")
                proceed // 返回原始的数据
            }
        }

    }

    private fun addAuthToken(request: Request.Builder) {
        val requestBuild = request.build()
        val isLogin = requestBuild.url.encodedPath.contains("/login/")
        if (!isLogin) {
            userTokenStorage.getToken()?.let {
                request.removeHeader("Authorization")
                request.addHeader("Authorization", "Bearer " + userTokenStorage.getToken())
            }
        }
    }

}