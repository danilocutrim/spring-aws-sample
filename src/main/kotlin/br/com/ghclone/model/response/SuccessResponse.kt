package br.com.ghclone.model.response

data class SuccessResponse<T>(val payload: T) : ApiResponse()