package br.com.ghclone.service

import br.com.ghclone.model.Repo
import br.com.ghclone.model.entity.RepoEntity
import br.com.ghclone.repository.RepoRepository
import org.springframework.stereotype.Service


@Service
class RepositoryService(private val dynamoRepo:RepoRepository) {

    suspend fun createRepository(repository: Repo) {
        dynamoRepo.save(RepoEntity(repositoryName = repository.name, description = repository.description))

    }

    suspend fun getRepository(document: String, id: String): RepoEntity {
        val repoEntity = dynamoRepo.get(document, id)
        return repoEntity
    }
}