package br.com.ghclone.service

import br.com.ghclone.domain.RepoInfo
import br.com.ghclone.builder.repoInfoEntity
import br.com.ghclone.schema.RepoSchema
import br.com.ghclone.enums.PkPrefix
import br.com.ghclone.model.request.NewRepoRequest
import br.com.ghclone.repository.RepoRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import software.amazon.awssdk.enhanced.dynamodb.model.Page


@Service
class RepoService(private val dynamoRepo: RepoRepository, val userService: UserService) {

    suspend fun createRepository(repo: NewRepoRequest): RepoInfo {
        val entity = repo.repoInfo.toEntity()
        dynamoRepo.save(entity)
        userService.addPermission(entity.repoInfo?.repositoryName!!, repo.contributors())

        return RepoInfo.fromEntity(entity)
    }

//    suspend fun addOrUpdateContent(contentRequest: ContentRequest): RepoContent {
//        val repoEntity = contentRequest.toRepo()
//        val repoExists = dynamoRepo.projectExists(repoEntity.repositoryName, repoEntity.projectSk())
//        if (repoExists) {
////            return dynamoRepo.save(repoEntity)
//        } else {
//            throw Exception("Repository not found")
//        }
//    }

    suspend fun findRepo(name: String): RepoSchema? {
        return dynamoRepo.findOne(repoInfoEntity { repoName = name })
    }

    fun findRepos(name: String): Flow<MutableList<RepoSchema>> {
        return dynamoRepo.findAllByPk(PkPrefix.REPO.pk(name))
    }

//    suspend fun getRepository(document: String, id: String?): RepoContent {
//        val repoEntity = dynamoRepo.get(document, id)
//        return repoEntity
//    }
}