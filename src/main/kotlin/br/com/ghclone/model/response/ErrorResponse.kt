package br.com.ghclone.model.response

data class ErrorResponse<T>(val error: T, val code: Int) : ApiResponse()