package net.thechance.chat.dto

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class BaseResponse<T>(
    val data: T? = null,
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