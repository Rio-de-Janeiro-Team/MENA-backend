package net.thechance.chat.exception

import chat.dto.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(exception: Exception): ResponseEntity<BaseResponse<Any>> {
        val response = BaseResponse<Any>(
            status = 500,
            success = false,
            message = "An unexpected error occurred: ${exception.message}"
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response)
    }
}
