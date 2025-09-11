package chat.dto

data class BaseResponse<T>(
    val body: T? = null,
    val status: Int? = null,
    val success: Boolean = false,
    val message: String? = null
)