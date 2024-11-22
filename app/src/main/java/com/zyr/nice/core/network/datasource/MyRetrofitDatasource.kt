package com.zyr.nice.core.network.datasource

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.zyr.nice.core.config.Config
import com.zyr.nice.core.model.Song
import com.zyr.nice.core.model.response.PageDTO
import com.zyr.nice.core.model.response.ResponseDTO
import com.zyr.nice.core.network.di.NetworkModule
import com.zyr.nice.core.network.retrofit.MyNetworkApiService
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

object MyRetrofitDatasource {

    private val service = Retrofit.Builder()
        .baseUrl(Config.ENDPOINT)
        .callFactory(NetworkModule.providesOkHttpClient())
        .addConverterFactory(
            NetworkModule.providesNetworkJson().asConverterFactory("application/json".toMediaType())
        )
        .build()
        .create(MyNetworkApiService::class.java)

    @GET("song/page")
    suspend fun songs(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): ResponseDTO<PageDTO<List<Song>>> {
        return service.songs(page, pageSize)
    }

    @GET("song/{id}")
    suspend fun songDetail(@Path("id") id: Long): ResponseDTO<Song> {
        return service.songDetail(id)
    }

}