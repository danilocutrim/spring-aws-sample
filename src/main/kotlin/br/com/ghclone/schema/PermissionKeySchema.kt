package br.com.ghclone.schema

import br.com.ghclone.enums.PkPrefix
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey

@DynamoDbBean
data class UserSchema(
    var userName: String? = null,
    var email: String? = null,
    var login: String? = null
):Schema

@DynamoDbBean
data class PermissionKeySchema(
    @get:DynamoDbPartitionKey
    var pk: String? = null,
    @get:DynamoDbSortKey
    var sk: String? = null,
):Schema {
    companion object{
        fun userKey(userName: String): PermissionKeySchema {
            val key = PkPrefix.USER.pk(userName)
            return PermissionKeySchema(pk = key, sk = key)
        }

        fun userPermissionKey(userName: String, repoName: String): PermissionKeySchema {
            val userPk = PkPrefix.ROLE.pk(userName)
            val repoSk = PkPrefix.REPO.pk(repoName)
            return PermissionKeySchema(pk = userPk, sk = repoSk)
        }

        fun projectPermissionKey(userName: String, repoName: String): PermissionKeySchema {
            val repoPk = PkPrefix.ROLE.pk(repoName)
            val userSk = PkPrefix.USER.pk(userName)
            return PermissionKeySchema(pk = repoPk, sk = userSk)
        }
    }
}

@DynamoDbBean
data class RepoPermissionSchema(
    var repoName: String? = null,
    var admin: String? = null,
    var user: String? = null,
    var type: String? = ""
):Schema




