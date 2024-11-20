package br.com.ghclone.model.request

import br.com.ghclone.domain.Contributor
import br.com.ghclone.domain.Info
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)
data class NewRepoRequest(
    val info: Info,
    val creator: Contributor,
    val additionalUsers: List<Contributor> = emptyList()
) {
    fun entity() = info
    fun contributors() = additionalUsers.plus(creator)
}