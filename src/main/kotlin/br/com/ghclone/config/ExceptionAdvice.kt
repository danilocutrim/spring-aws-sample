package br.com.ghclone.config

import br.com.ghclone.exception.InvalidFieldsException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@RestControllerAdvice
class ExceptionAdvice : ResponseEntityExceptionHandler() {


    override fun handleWebExchangeBindException(
        ex: WebExchangeBindException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        exchange: ServerWebExchange
    ): Mono<ResponseEntity<Any>> {
        val errorBody = InvalidFieldsException.Builder()
        ex.bindingResult.fieldErrors.forEach {
            errorBody.addError(it.field, it.defaultMessage ?: "")
        }
        val ourError = errorBody.build()
        return createResponseEntity(ourError, ex.headers, ex.statusCode, exchange)
    }

}

