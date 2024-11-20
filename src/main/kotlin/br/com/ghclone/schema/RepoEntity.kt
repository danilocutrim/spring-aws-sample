package br.com.ghclone.schema

import br.com.ghclone.enums.ContentType
import com.fasterxml.jackson.annotation.JsonInclude
import software.amazon.awssdk.enhanced.dynamodb.mapper.UpdateBehavior
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@DynamoDbBean
data class RepoEntity(
    @get:DynamoDbPartitionKey
    var repositoryName: String = "",
    @get:DynamoDbUpdateBehavior(UpdateBehavior.WRITE_IF_NOT_EXISTS)
    var description: String = "",
    @get:DynamoDbIgnore
    var content: String = repositoryName,
    @get:DynamoDbIgnore
    var contentType: ContentType = ContentType.EMPTY,
    @get:DynamoDbIgnore
    var fileName: String = "",
) {
    @get:DynamoDbSortKey
    @get:DynamoDbAttribute(value = "contentSk")
    var contentSk: String = contentSk()

    private fun contentSk(): String {
        return sKBuilder(content, fileName)
    }

    fun projectSk(): String {
        check(repositoryName.isNotBlank()) { "Repository name is required" }
        return ContentType.EMPTY.key.plus(repositoryName)
    }
}


fun sKBuilder(vararg values: String): String {
    return values.joinToString(separator = "/")
}