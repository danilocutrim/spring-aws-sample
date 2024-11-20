package br.com.ghclone.model.request

import br.com.ghclone.enums.ContentType
import br.com.ghclone.schema.RepoEntity

data class ContentRequest(
    val repo: String,
    val contentPath: String,
    val encoding: String? = null,
    val content: String?=null,
    val fileName: String?=null,
    val description: String? = null
) {
    fun toRepo(): RepoEntity {

        return RepoEntity(
            repositoryName = repo,
            description = description ?: "",
            content = contentPath,
            contentType = contentType(),
            fileName = fileName ?: ""
        )
    }
    private fun contentType():ContentType{
        if(fileName.isNullOrEmpty()){
            return ContentType.REPO
        }
        return ContentType.FILE
    }
//    fun toStorageObject(): StorageObject {
//        return storageObject {
//            projectName = project
//            apiUri = uri
//            fileName = this@ContentRequest.fileName
//            content = this@ContentRequest.content
//            encodingType = encoding
//        }
//    }
}
