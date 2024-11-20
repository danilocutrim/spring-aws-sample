package br.com.ghclone.entity.dynamodb

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean

@DynamoDbBean
data class ContentSchema(
    var content: String? = null,
    var contentType: String? = null,
    var name: String? = null,
    var path:String? = null,
    var createdAt: String? = null
)