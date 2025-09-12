package net.thechance.chat.config

import net.thechance.chat.dto.BaseResponse
import net.thechance.chat.dto.baseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ChatExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun onGlobalException(e:Exception): ResponseEntity<BaseResponse<Any>>{
        return baseResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR,
            message = "error in the server: ${e.message}",
            success = false
        )
    }
}