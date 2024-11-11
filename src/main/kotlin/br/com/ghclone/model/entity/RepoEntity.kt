package br.com.ghclone.model.entity

import com.fasterxml.jackson.annotation.JsonInclude
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey

@JsonInclude(JsonInclude.Include.NON_NULL)
@DynamoDbBean
data class RepoEntity(
    @get:DynamoDbPartitionKey
    var repositoryName: String? = null,
    @get:DynamoDbAttribute(value = "repositoryDescription")
    var description: String? = null,
    @get:DynamoDbSortKey
    @get:DynamoDbAttribute(value = "sK")
    var sK:String? = "REPO#$repositoryName"
)
