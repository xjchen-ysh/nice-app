package com.zyr.nice.core.model.response

import kotlinx.serialization.Serializable

@Serializable
data class PageDTO<T>(
    val data: T? = null,
    val total: Long = 0,
    val totalPages: Int = 0,
    val page: Int = 0,
    val pageSize: Int = 3
)