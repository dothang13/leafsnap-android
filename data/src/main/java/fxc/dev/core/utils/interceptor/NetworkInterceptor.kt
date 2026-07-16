package fxc.dev.core.utils.interceptor

import fxc.dev.core.NetworkConstant
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

open class NetworkInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("debug", "${NetworkConstant.IS_DEBUG}")
            .build()

        val requestBuilder = original.newBuilder()
            .url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}