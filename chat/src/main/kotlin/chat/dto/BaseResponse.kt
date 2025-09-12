package chat.dto

data class BaseResponse<T>(
    val body: T? = null,
    val status: Int,
    val success: Boolean,
    val message: String? = null
)

fun <T> baseResponse(
    data: T? = null,
    success: Boolean,
    status: HttpStatus = HttpStatus.OK,
    message: String? = null
): ResponseEntity<BaseResponse<T>> {
    return ResponseEntity.status(status).body(
        BaseResponse(
            success = success,
            data = data,
            message = message
        )
    )
}