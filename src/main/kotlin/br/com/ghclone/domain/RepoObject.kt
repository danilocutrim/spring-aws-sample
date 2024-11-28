package br.com.ghclone.domain

import br.com.ghclone.enums.ContentType
import br.com.ghclone.model.ProjectName
import java.nio.file.Path

data class KeyRepo(val repoName: String, val path: Path,val contentType: ContentType):KeyData(    partitionKey = KeyDat(repoName, ContentType.REPO),
    separator = "#",
    KeyDat(repoName, type = contentType))

sealed class RepoKeys(val contentType: ContentType){
    abstract val projectName: ProjectName
    abstract val path:Path
    data class FileKey(override val projectName: ProjectName, override val path: Path):RepoKeys(ContentType.FILE)
    data class RepoKey(override val projectName: ProjectName, override val path: Path):RepoKeys(contentType = ContentType.REPO)
    fun key() = KeyRepo(projectName.name, path, contentType)
}

val ab = RepoKeys.RepoKey(ProjectName("name"), Path.of("path"))
sealed interface RepoItemKey{
    fun key():KeyData
}



sealed class FileObject {
    abstract val name: String
    abstract val path: Path
    abstract val createdAt: String

    data class File(
        override val name: String,
        override val path: Path,
        override val createdAt: String,
        val content: String,
        val encode: String
    ) : FileObject()

    data class Directory(
        override val name: String,
        override val path: Path,
        override val createdAt: String,
        val files: List<FileObject>
    ) : FileObject()

}

fun main() {
    val file = FileObject.File(
        name = "file",
        path = Path.of("path"),
        createdAt = "2021-09-09",
        content = "content",
        encode = "utf-8"
    )
    val a = RepoKeys.FileKey(ProjectName("name"), Path.of("path"))

    val directory = FileObject.Directory(
        name = "directory",
        path = Path.of("path"),
        createdAt = "2021-09-09",
        files = listOf(file)
    )

    println(directory)
    println(a.key().sk)
}