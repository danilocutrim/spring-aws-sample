package br.com.ghclone.model.request

import br.com.ghclone.domain.Contributor
import br.com.ghclone.domain.RepoInfo
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)
data class NewRepoRequest(
    val repoInfo: RepoInfo,
    val creator: Contributor,
    val additionalUsers: List<Contributor> = emptyList()
) {
    fun entity() = repoInfo
    fun contributors() = additionalUsers.plus(creator)
}