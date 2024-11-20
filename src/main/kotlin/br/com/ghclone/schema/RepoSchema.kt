package br.com.ghclone.schema

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbFlatten
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbIgnoreNulls
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPreserveEmptyObject

@DynamoDbBean
data class RepoSchema(
    @get:DynamoDbFlatten
    var key: RepoKeySchema? = null,
    @get:DynamoDbFlatten
    @get:DynamoDbIgnoreNulls
    @get:DynamoDbPreserveEmptyObject
    var content: ContentSchema? = null,
    @get:DynamoDbFlatten
    @get:DynamoDbIgnoreNulls
    @get:DynamoDbPreserveEmptyObject
    var repoInfo: RepoInfoSchema? = null
)

