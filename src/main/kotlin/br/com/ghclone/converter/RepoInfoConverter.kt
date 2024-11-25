package br.com.ghclone.converter

import br.com.ghclone.domain.RepoInfo
import br.com.ghclone.domain.User
import br.com.ghclone.schema.RepoInfoSchema
import br.com.ghclone.schema.UserSchema

class RepoInfoConverter : ItemConverter<RepoInfo, RepoInfoSchema> {
    override fun toSchema(item: RepoInfo): RepoInfoSchema {
        return RepoInfoSchema(
            repositoryName = item.name,
            description = item.description,
            repoCreatedAt = item.createdAt
        )
    }

    override fun fromSchema(item: RepoInfoSchema): RepoInfo {
        val repoName = requireNotNull(item.repositoryName) { "Repository name cannot be blank" }
        val description = requireNotNull(item.description) { "Repository description cannot be blank" }
        val createdAt = requireNotNull(item.repoCreatedAt) { "Repository creation date cannot be blank" }
        return RepoInfo(name = repoName, description = description, createdAt = createdAt, type = "repo")
    }

}


