package br.com.ghclone.builder

import br.com.ghclone.schema.PermissionKeySchema
import br.com.ghclone.schema.PermissionSchema
import br.com.ghclone.schema.UserSchema
import br.com.ghclone.enums.PkPrefix

data class UserSchemaBuilder(var userName: String = "", var email: String? = null, var login: String? = null) {
    fun build(): PermissionSchema {
        val userSk = PkPrefix.USER.pk(userName)
        val key = PermissionKeySchema(userSk, userSk)
        return PermissionSchema(key, userInfo = UserSchema(userName, email, login))
    }
}

inline fun userSchema(block: UserSchemaBuilder.() -> Unit): PermissionSchema =
    UserSchemaBuilder().apply(block).build()