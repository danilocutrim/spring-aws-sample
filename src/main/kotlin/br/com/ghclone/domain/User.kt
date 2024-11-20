package br.com.ghclone.domain

import br.com.ghclone.schema.PermissionSchema


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


