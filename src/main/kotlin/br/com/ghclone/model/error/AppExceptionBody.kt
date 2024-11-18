package br.com.ghclone.model.error

import java.time.LocalDateTime

data class AppExceptionBody<T>(
    val timeStamp: LocalDateTime = LocalDateTime.now(),
    val status: Int,
    val error: T,
    val message: String
)
