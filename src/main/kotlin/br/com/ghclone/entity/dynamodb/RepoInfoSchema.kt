package br.com.ghclone.entity.dynamodb

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean

@DynamoDbBean
data class RepoInfoSchema(
    var repositoryName: String? = null,
    var description: String? = null,
    var repoCreatedAt: String? = null,
)