package br.com.ghclone.builder

import br.com.ghclone.schema.PermissionKeySchema
import br.com.ghclone.schema.PermissionSchema
import br.com.ghclone.schema.RepoPermissionSchema
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


