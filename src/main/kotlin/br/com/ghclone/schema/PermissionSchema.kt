package br.com.ghclone.schema

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbFlatten
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbIgnoreNulls
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPreserveEmptyObject

@DynamoDbBean
data class PermissionSchema(
    @get:DynamoDbFlatten
    var key: PermissionKeySchema? = null,
    @get:DynamoDbFlatten
    @get:DynamoDbIgnoreNulls
    @get:DynamoDbPreserveEmptyObject
    var permission: RepoPermissionSchema? = null,
    @get:DynamoDbFlatten
    @get:DynamoDbIgnoreNulls
    @get:DynamoDbPreserveEmptyObject
    var userInfo: UserSchema? = null
)