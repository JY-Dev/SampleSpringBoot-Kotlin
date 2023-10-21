package com.example.sample.web.exception

import com.example.sample.domain.exception.BusinessException
import com.example.sample.domain.exception.NotFoundException
import com.example.sample.web.dto.error.ErrorResponse
import org.apache.commons.logging.LogFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.MethodNotAllowedException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler
    fun notFoundException(exception: NotFoundException): ResponseEntity<ErrorResponse> =
        ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse(exception.message ?: NotFoundException.DEFAULT))

    @ExceptionHandler
    fun methodNotAllowedException(exception: MethodNotAllowedException): ResponseEntity<ErrorResponse> =
        ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(ErrorResponse(exception.message))

    @ExceptionHandler
    fun businessException(exception: BusinessException): ResponseEntity<ErrorResponse> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(exception.message ?: "잘못된 요청입니다."))

    @ExceptionHandler
    fun messageNotReadableException(exception: HttpMessageNotReadableException) =
        ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse("보낸 Request의 Json이 올바르지 않습니다."))

    @ExceptionHandler
    fun serverError(exception: Exception, model: Model): ResponseEntity<ErrorResponse> {
        val log = LogFactory.getLog(this::class.java)
        log.error(exception)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse(exception.message ?: "서버 오류 입니다."))
    }

    @ExceptionHandler
    fun requestParamException(exception: RequestParamException) =
        ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(exception.message ?: "RequestParam이 올바르지 않습니다."))

}