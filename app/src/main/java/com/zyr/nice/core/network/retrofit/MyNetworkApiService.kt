package com.zyr.nice.core.network.retrofit

import com.zyr.nice.core.model.Song
import com.zyr.nice.core.model.response.PageDTO
import com.zyr.nice.core.model.response.ResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * 网络请求接口
 */
interface MyNetworkApiService {
    @GET("song/page")
    suspend fun songs(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): ResponseDTO<PageDTO<List<Song>>>

    @GET("song/{id}")
    suspend fun songDetail(@Path("id") id: Long): ResponseDTO<Song>

}