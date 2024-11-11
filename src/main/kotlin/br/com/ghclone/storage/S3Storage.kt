package br.com.ghclone.storage

import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.PutObjectRequest
import aws.smithy.kotlin.runtime.content.ByteStream
import java.nio.ByteBuffer


abstract class S3Storage(private val bucketName: String, private val s3Client: S3Client) {

    suspend fun save(key: String, content: ByteBuffer) {
        s3Client
    }


    fun createPutObject(rootDir: String,path:String, fileName:String, content: ByteStream) {
        PutObjectRequest {
            bucket = bucketName
            key = fileName
            body = content

        }
    }


}