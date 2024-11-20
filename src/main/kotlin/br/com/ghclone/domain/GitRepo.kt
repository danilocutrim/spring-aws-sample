package br.com.ghclone.domain

import br.com.ghclone.entity.builder.repoInfoEntity
import br.com.ghclone.entity.dynamodb.RepoSchema

sealed class RepoAttribute {

    data class Content(
        val name: String,
        val path: String,
        val type: String,
        val content: String,
        val createdAt: String
    ) : RepoAttribute()


}

data class Info(val name: String, val description: String, val type: String, val createdAt: String) : RepoAttribute() {
    fun toEntity() = repoInfoEntity {
        repoName = name
        repoDescription = description
    }
    companion object {
        fun fromEntity(entity: RepoSchema): Info {
            val name = requireNotNull(entity.repoInfo?.repositoryName) { "Repository name cannot be blank" }
            val description = requireNotNull(entity.repoInfo?.description) { "Repository description cannot be blank" }
            val createdAt =
                requireNotNull(entity.repoInfo?.repoCreatedAt) { "Repository creation date cannot be blank" }
            return Info(name, description, "repo", createdAt)
        }
    }
}
