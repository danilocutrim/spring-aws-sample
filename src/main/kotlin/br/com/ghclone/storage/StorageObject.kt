package br.com.ghclone.storage

import br.com.ghclone.model.ProjectName

class StorageObject(
    val projectName: ProjectName,
    val apiUri: String,
    val fileName: String,
    val content: String,
    val encodingType: String
) {
    fun s3Key(): String {
        return "$projectName/$fileName"
    }
}


data class StorageObjectBuilder(
    var projectName: String = "",
    var apiUri: String = "",
    var fileName: String = "",
    var content: String = "",
    var encodingType: String = ""
) {
    fun build(): StorageObject {
        return StorageObject(
            projectName = ProjectName(projectName),
            apiUri = apiUri,
            fileName = fileName,
            content = content,
            encodingType = encodingType
        )
    }
}

fun storageObject(block: StorageObjectBuilder.() -> Unit): StorageObject = StorageObjectBuilder().apply(block).build()