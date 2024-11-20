package br.com.ghclone.service

import br.com.ghclone.domain.Contributor
import br.com.ghclone.schema.PermissionSchema
import br.com.ghclone.repository.PermissionRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val repository: PermissionRepository) {

    suspend fun addPermission(repo: String, contributor: List<Contributor>): List<PermissionSchema> {
        contributor.forEach {
            applyUserPermission(repo, it)}
        return contributor.map { it.toUserSchema() }
    }

    suspend fun createUserIfNotExists(contributor: Contributor): PermissionSchema {
        val entity = contributor.toUserSchema()
        if (repository.findOne(entity) == null) {
            repository.save(entity)
        }
        return entity
    }

    suspend fun applyUserPermission(repo: String, contributor: Contributor): PermissionSchema {
        val entity = createUserIfNotExists(contributor)
        repository.save(contributor.toUserPermissionSchema(repo))
        repository.save(contributor.toRepoPermissionSchema(repo))
        return entity
    }
}