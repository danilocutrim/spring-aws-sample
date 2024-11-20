package br.com.ghclone.domain

import br.com.ghclone.builder.repoPermission
import br.com.ghclone.builder.userPermission
import br.com.ghclone.builder.userSchema
import br.com.ghclone.schema.PermissionSchema
import com.fasterxml.jackson.annotation.JsonProperty

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