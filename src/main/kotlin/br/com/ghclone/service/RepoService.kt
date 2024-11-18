package br.com.ghclone.service

import br.com.ghclone.model.entity.RepoEntity
import br.com.ghclone.model.request.ContentRequest
import br.com.ghclone.repository.RepoRepository
import org.springframework.stereotype.Service


@Service
class RepoService(private val dynamoRepo: RepoRepository) {

    suspend fun createRepository(repo: RepoEntity) {
        dynamoRepo.save(repo)

    }

    suspend fun addOrUpdateContent(contentRequest: ContentRequest): RepoEntity {
        val repoEntity = contentRequest.toRepo()
        val repoExists = dynamoRepo.projectExists(repoEntity.repositoryName, repoEntity.projectSk())
        if (repoExists) {
            return dynamoRepo.save(repoEntity)
        } else {
            throw Exception("Repository not found")
        }
    }

    suspend fun getRepository(document: String, id: String?): RepoEntity {
        val repoEntity = dynamoRepo.get(document, id)
        return repoEntity
    }
}