package br.com.ghclone.exception

data class BadRequestException(override val message: String):RuntimeException()