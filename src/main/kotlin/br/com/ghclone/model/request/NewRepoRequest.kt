package br.com.ghclone.model.request

import br.com.ghclone.model.entity.RepoEntity
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.validation.constraints.NotBlank

@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)
data class NewRepoRequest(
//    @get:Size(min = 1, max = 2)
    val name: String,
    @get:NotBlank
    val description: String? = null
) {
    fun toRepo(): RepoEntity {
        return RepoEntity(
            repositoryName = name,
            description = description?:""
        )
    }
}