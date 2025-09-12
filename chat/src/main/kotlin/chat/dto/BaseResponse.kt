package net.thechance.chat.dto

data class BaseResponse<T>(
    val body: T? = null,
    val status: Int,
    val success: Boolean,
    val message: String? = null
)