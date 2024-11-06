package br.com.dc.sample.config

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.s3.S3Client

@RestController
@RequestMapping("/api")
class Controller(private val s3: S3Client,private val dynamoDbClient: DynamoDbClient) {

    @RequestMapping("/s3")
    fun s3(): String {
        return s3.listBuckets().buckets().toString()
    }
    @RequestMapping("/dynamodb")
    fun dynamoDb(): String {
        return dynamoDbClient.listTables().tableNames().toString()
    }
}