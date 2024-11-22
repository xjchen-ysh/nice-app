package com.zyr.nice.core.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseDTO<T>(val data: T? = null, val success: Boolean, val message: String)