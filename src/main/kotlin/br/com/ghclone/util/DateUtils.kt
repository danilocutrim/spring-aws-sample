package br.com.ghclone.util

import aws.smithy.kotlin.runtime.time.Instant
import aws.smithy.kotlin.runtime.time.TimestampFormat

fun dateNow() = Instant.now().format(TimestampFormat.ISO_8601)