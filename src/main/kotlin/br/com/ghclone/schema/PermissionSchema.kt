package br.com.ghclone.schema

import br.com.ghclone.enums.PermissionType
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
):Schema  {
    companion object {
        fun user(userInfo: UserSchema): PermissionSchema {
            val user = requireNotNull(userInfo.userName)
            val userPk = PermissionKeySchema.userKey(user)
            return PermissionSchema(key = userPk, userInfo = userInfo)
        }

        fun userPermission(repoPermissionSchema: RepoPermissionSchema): PermissionSchema {
            require(PermissionType.USER.type.equals(repoPermissionSchema.type, ignoreCase = true))
            val user = requireNotNull(repoPermissionSchema.user)
            val repo = requireNotNull(repoPermissionSchema.repoName)
            val pk = PermissionKeySchema.userPermissionKey(user, repo)
            return PermissionSchema(key = pk, permission = repoPermissionSchema)
        }

        fun repoPermission(repoPermissionSchema: RepoPermissionSchema): PermissionSchema {
            require(PermissionType.REPO.type.equals(repoPermissionSchema.type, ignoreCase = true))
            val user = requireNotNull(repoPermissionSchema.user)
            val repo = requireNotNull(repoPermissionSchema.repoName)
            val pk = PermissionKeySchema.projectPermissionKey(user, repo)
            return PermissionSchema(key = pk, permission = repoPermissionSchema)
        }
    }

}