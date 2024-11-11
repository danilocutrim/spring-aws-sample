package br.com.ghclone.storage

import java.nio.file.Path

interface FileStorage {

    suspend fun <T> save(path: Path, data: T): Boolean

    suspend fun <T> read(path: Path): T

    suspend fun delete(path: Path): Boolean

    suspend fun exists(path: Path): Boolean

    suspend fun list(path: Path): List<Path>

    suspend fun createDirectory(path: Path): Boolean

    suspend fun deleteDirectory(path: Path): Boolean

    suspend fun existsDirectory(path: Path): Boolean

    suspend fun listDirectory(path: Path): List<Path>

    suspend fun move(source: Path, target: Path): Boolean

    suspend fun copy(source: Path, target: Path): Boolean

    suspend fun rename(source: Path, target: Path): Boolean

    suspend fun size(path: Path): Long

    suspend fun lastModified(path: Path): Long

    suspend fun isDirectory(path: Path): Boolean

    suspend fun isFile(path: Path): Boolean

    suspend fun isHidden(path: Path): Boolean

    suspend fun updateLastModified(path: Path, time: Long): Boolean

    suspend fun updateFile(path: Path, data: ByteArray): Boolean
}