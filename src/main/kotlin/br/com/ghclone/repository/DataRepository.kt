package br.com.ghclone.repository

interface DataRepository<T> {
    suspend fun save(entity: T): T
    suspend fun get(document: String, id: String): T
    suspend fun update(entity: T)
}