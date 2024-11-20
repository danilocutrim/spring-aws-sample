package br.com.ghclone.entity.dynamodb

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*

@DynamoDbBean
data class UserSchema(
    var userName: String? = null,
    var email: String? = null,
    var login: String? = null
)

@DynamoDbBean
data class PermissionKeySchema(
    @get:DynamoDbPartitionKey
    var pk: String? = null,
    @get:DynamoDbSortKey
    var sk: String? = null,
)

@DynamoDbBean
data class RepoPermissionSchema(
    var repoName: String? = null,
    var admin: String? = null,
    var user: String? = null
)

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




