package br.com.ghclone.model.request

import br.com.ghclone.storage.StorageObject
import br.com.ghclone.storage.storageObject

data class ContentRequest(
    val content: String,
    val encoding: String,
    val project: String,
    val uri: String,
    val fileName: String
) {
    fun toStorageObject(): StorageObject {
        return storageObject {
            projectName = project
            apiUri = uri
            fileName = this@ContentRequest.fileName
            content = this@ContentRequest.content
            encodingType = encoding
        }
    }
}
