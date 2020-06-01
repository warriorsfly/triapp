package com.warriorsfly.core.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInterceptor(private val token:String=""): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()

        if ((original.url().encodedPath()
                .contains("/api/Identity/Jwtoken") && original.method() == "post")
        ) {
            return chain.proceed(original)
        }

        val url = original.url()
        val requestBuilder = original.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}