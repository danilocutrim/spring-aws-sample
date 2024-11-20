package br.com.ghclone.service

import br.com.ghclone.domain.Info
import br.com.ghclone.entity.builder.repoInfoEntity
import br.com.ghclone.entity.dynamodb.RepoSchema
import br.com.ghclone.enums.PkPrefix
import br.com.ghclone.model.request.NewRepoRequest
import br.com.ghclone.repository.RepoRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import software.amazon.awssdk.enhanced.dynamodb.model.Page


@Service
class RepoService(private val dynamoRepo: RepoRepository, val userService: UserService) {

    suspend fun createRepository(repo: NewRepoRequest): Info {
        val entity = repo.info.toEntity()
        dynamoRepo.save(entity)
        userService.addPermission(entity.repoInfo?.repositoryName!!, repo.contributors())

        return Info.fromEntity(entity)
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

    fun findRepos(name: String): Flow<Page<RepoSchema>> {
        return dynamoRepo.findAllByPk(PkPrefix.REPO.pk(name))
    }

//    suspend fun getRepository(document: String, id: String?): RepoContent {
//        val repoEntity = dynamoRepo.get(document, id)
//        return repoEntity
//    }
}