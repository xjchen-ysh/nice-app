package com.zyr.nice.core.network.di

import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object NetworkModule {

    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    fun okHttpCallFactory(
        okHttpClient: OkHttpClient
    ): Call.Factory = okHttpClient

    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }

}