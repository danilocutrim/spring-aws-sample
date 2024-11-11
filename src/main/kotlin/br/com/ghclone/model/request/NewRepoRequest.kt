package br.com.ghclone.model.request

import br.com.ghclone.model.Repo

data class NewRepoRequest(
    val name: String,
    val description: String,
    val homepage: String,
    val isPrivate: Boolean,
    val hasIssues: Boolean,
    val hasProjects: Boolean,
    val hasWiki: Boolean
){
    fun toRepo(): Repo {
        return Repo(
            id = "",
            name = name,
            description = description,
            contents = emptyList()
        )
    }
}