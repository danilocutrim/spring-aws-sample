package br.com.ghclone.builder

import br.com.ghclone.schema.RepoSchema
import br.com.ghclone.schema.RepoInfoSchema
import br.com.ghclone.schema.RepoKeySchema
import br.com.ghclone.enums.PkPrefix
import br.com.ghclone.util.dateNow

data class RepoInfoEntityBuilder(
    var repoName: String = "",
    var repoDescription: String = "",
) {
    fun build(): RepoSchema {
        require(repoName.isNotBlank()) { "Repository name cannot be blank" }
        val key = PkPrefix.REPO.pk(repoName).let { RepoKeySchema(it, it) }
        val repoInfoSchema = RepoInfoSchema(
            repositoryName = repoName,
            description = repoDescription,
            repoCreatedAt = dateNow()
        )
        return RepoSchema(key, repoInfo = repoInfoSchema)
    }
}

inline fun repoInfoEntity(block: RepoInfoEntityBuilder.() -> Unit): RepoSchema = RepoInfoEntityBuilder().apply(block).build()