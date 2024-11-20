package br.com.ghclone.entity.builder

import br.com.ghclone.entity.dynamodb.PermissionKeySchema
import br.com.ghclone.entity.dynamodb.PermissionSchema
import br.com.ghclone.entity.dynamodb.RepoPermissionSchema
import br.com.ghclone.entity.dynamodb.UserSchema
import br.com.ghclone.enums.PkPrefix

data class UserPermissionBuilder(var repoName: String = "", var isAdmin: Boolean = false, var userName: String = "") {
    fun build(): PermissionSchema {
        val userPk = PkPrefix.ROLE.pk(userName)
        val repoSk = PkPrefix.REPO.pk(repoName)
        val key = PermissionKeySchema(userPk, repoSk)
        return PermissionSchema(key, RepoPermissionSchema(repoName, isAdmin.toString(), userName))
    }
}

inline fun userPermission(block: UserPermissionBuilder.() -> Unit): PermissionSchema =
    UserPermissionBuilder().apply(block).build()

data class RepoPermissionBuilder(var repoName: String = "", var isAdmin: Boolean = false, var userName: String = "") {
    fun build(): PermissionSchema {
        val userSk = PkPrefix.ROLE.pk(userName)
        val repoPk = PkPrefix.REPO.pk(repoName)
        val key = PermissionKeySchema(repoPk, userSk)
        return PermissionSchema(key, RepoPermissionSchema(repoName, isAdmin.toString(), userName))
    }
}

inline fun repoPermission(block: RepoPermissionBuilder.() -> Unit): PermissionSchema =
    RepoPermissionBuilder().apply(block).build()


data class UserSchemaBuilder(var userName: String = "", var email: String? = null, var login: String? = null) {
    fun build(): PermissionSchema {
        val userSk = PkPrefix.USER.pk(userName)
        val key = PermissionKeySchema(userSk, userSk)
        return PermissionSchema(key, userInfo = UserSchema(userName, email, login))
    }
}

inline fun userSchema(block: UserSchemaBuilder.() -> Unit): PermissionSchema =
    UserSchemaBuilder().apply(block).build()