package net.thechance.chat.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(basePackages = ["net.thechance.chat"])
class GlobalExceptionHandler {
    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(exception: Exception): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An unexpected error occurred: ${exception.message}")
    }
}
