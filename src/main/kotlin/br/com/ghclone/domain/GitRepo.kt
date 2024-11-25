package br.com.ghclone.domain

import br.com.ghclone.builder.repoInfoEntity
import br.com.ghclone.model.ProjectName
import br.com.ghclone.schema.RepoSchema

data class RepoInfo(val name: String, val description: String, val type: String, val createdAt: String) {
    val key = ProjecKey(repoName = ProjectName(name))
    fun toEntity() = repoInfoEntity {
        repoName = name
        repoDescription = description
    }

    fun toSchema():RepoSchema{
        return repoInfoEntity {
            repoName = name
            repoDescription = description
        }
    }

    companion object {
        fun fromEntity(entity: RepoSchema): RepoInfo {
            val name = requireNotNull(entity.repoInfo?.repositoryName) { "Repository name cannot be blank" }
            val description = requireNotNull(entity.repoInfo?.description) { "Repository description cannot be blank" }
            val createdAt =
                requireNotNull(entity.repoInfo?.repoCreatedAt) { "Repository creation date cannot be blank" }
            return RepoInfo(name, description, "repo", createdAt)
        }
    }
}
