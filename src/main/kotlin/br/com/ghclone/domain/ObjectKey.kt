package br.com.ghclone.domain

import br.com.ghclone.constants.PATH_PATTERN
import br.com.ghclone.enums.ContentType
import br.com.ghclone.model.ProjectName
import br.com.ghclone.schema.RepoKeySchema
import br.com.ghclone.util.toPath

data class KeyDat(val key: String, val type: ContentType)

@JvmInline
value class ObjectPath(val str: String) {
    init {
        require(str.isNotBlank()) { "Object path cannot be blank" }
        require(str.contains(Regex(PATH_PATTERN))) { "Project name can only contain letters, numbers, hyphens, and underscores" }
    }
}

@JvmInline
value class ObjectName(val name: String) {
    init {
        require(name.isNotBlank()) { "Object name cannot be blank" }
        require(name.length <= 1024) { "Object name cannot be longer than 1024 characters" }
    }
}

abstract class KeyData(open val partitionKey: KeyDat, val separator: String, vararg sK: KeyDat) {

    val keyStr = { i: KeyDat, _: String -> "${i.type}${separator}${i.key}" }
    val pathStr = sK.joinToString(separator = "/") {
        it.key
    }.toPath()
    val sk by lazy {
        sK.joinToString { keyStr(it, separator) }
    }
    val pk by lazy { keyStr(partitionKey, separator) }


}

//data class DynamoKey(override val partitionKey: String, override val pkType: ContentType) :
//    KeyData(partitionKey, pkType, "#") {
//    fun sk(type: ContentType, value: String): String {
//        return sKs.joinToString()
//    }
//}

data class ProjecKey(
    val repoName: ProjectName
) : KeyData(
    partitionKey = KeyDat(repoName.name, ContentType.REPO),
    separator = "#",
    KeyDat(repoName.name, ContentType.REPO)
) {
    fun toKeySchema(): RepoKeySchema {
        return RepoKeySchema(pk, sk)
    }

//    fun objectKey(): String {
//        val completePath = repoName.name.toPath(path.str)
//        require(completePath.name == name) { "Object name must match the name in the path" }
//        require(completePath.startsWith(repoName.name)) { "Object path must start with the project name" }
//        return completePath.toString()
//    }
//
//    fun dataKey(): String {
//        return type.toKey(objectKey())
//    }
//
//    fun storageKey(): String {
//        return objectKey().toPath().normalize().toString()
//    }
}

//
//data class DataKey(
//    override val repoName: ProjectName,
//    override val path: ObjectPath,
//    override val name: String,
//    override val type: ContentType
//) : ObjectKey(repoName = repoName, path = path, name = name, type = type, maxKeySize = 1024) {
//    val objectPath = repoName.name.toPath(path.str)
//
//    init {
//        require(name.isNotBlank()) { "Object name cannot be blank" }
//        require(objectPath.name == name) { "Object name must match the name in the path" }
//        require(objectPath.name.length <= maxKeySize) { "Object name cannot be longer than $maxKeySize characters" }
//    }
//}
//
//data class StorageKey(
//    override val repoName: ProjectName,
//    override val path: ObjectPath,
//    override val name: String,
//    override val type: ContentType
//) : ObjectKey(repoName = repoName, path = path, name = name, type = type, maxKeySize = 1024) {
//    val objectPath = repoName.name.toPath(path.str)
//
//    init {
//        require(name.isNotBlank()) { "Object name cannot be blank" }
//        require(objectPath.name == name) { "Object name must match the name in the path" }
//        require(objectPath.name.length <= maxKeySize) { "Object name cannot be longer than $maxKeySize characters" }
//    }
//}


fun main() {
    val keyD =
        ProjecKey(ProjectName("project"))


    println(keyD.pk)
    println(keyD.sk)
    println(keyD.pathStr)


}