package br.com.ghclone.domain

import br.com.ghclone.entity.builder.repoPermission
import br.com.ghclone.entity.builder.userPermission
import br.com.ghclone.entity.builder.userSchema
import br.com.ghclone.entity.dynamodb.PermissionSchema
import com.fasterxml.jackson.annotation.JsonProperty


data class User(val name: String, val email: String? = null, var login: String? = null) {
    companion object {
        fun fromEntity(entity: PermissionSchema): User {
            return User(
                requireNotNull(entity.userInfo?.userName) { "User name cannot be blank" },
                requireNotNull(entity.userInfo?.email) { "User email cannot be blank" },
                requireNotNull(entity.userInfo?.login) { "User login cannot be blank" }
            )
        }
    }
}

data class Permission(val name: String, val isAdmin: Boolean)

data class Contributor(
    val user: User,
    @JsonProperty("isAdmin")
    val userIsAdmin: Boolean
) {
    fun toUserSchema(): PermissionSchema {
        return userSchema {
            userName = user.name
            login = user.login
            email = user.email
        }
    }

    fun toUserPermissionSchema(repo: String): PermissionSchema {
        return userPermission {
            userName = user.name
            isAdmin = userIsAdmin
            repoName = repo
        }

    }

    fun toRepoPermissionSchema(repo: String): PermissionSchema {
        return repoPermission {
            userName = user.name
            isAdmin = userIsAdmin
            repoName = repo
        }

    }

    companion object {
        fun fromEntity(entity: PermissionSchema): Contributor {
            val userName = requireNotNull(entity.userInfo?.userName) { "User name cannot be blank" }
            val isAdmin = requireNotNull(entity.permission?.admin) { "Permission cannot be blank" }.toBoolean()
            return Contributor(User(userName), isAdmin)
        }
    }
}
