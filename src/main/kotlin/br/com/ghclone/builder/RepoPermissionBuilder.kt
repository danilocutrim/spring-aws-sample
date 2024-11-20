package br.com.ghclone.builder

import br.com.ghclone.schema.PermissionKeySchema
import br.com.ghclone.schema.PermissionSchema
import br.com.ghclone.schema.RepoPermissionSchema
import br.com.ghclone.enums.PkPrefix

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