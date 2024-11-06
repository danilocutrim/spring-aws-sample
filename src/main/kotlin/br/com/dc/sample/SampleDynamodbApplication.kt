package br.com.dc.sample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SampleDynamodbApplication

fun main(args: Array<String>) {
	runApplication<SampleDynamodbApplication>(*args)
}
