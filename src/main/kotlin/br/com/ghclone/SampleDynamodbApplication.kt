package br.com.ghclone

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@EnableWebFlux
@SpringBootApplication
class SampleDynamodbApplication

fun main(args: Array<String>) {
    runApplication<SampleDynamodbApplication>(*args)
}
