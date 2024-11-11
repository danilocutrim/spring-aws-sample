package br.com.ghclone.controller

import br.com.ghclone.model.entity.RepoEntity
import br.com.ghclone.model.entity.User
import br.com.ghclone.model.request.ContentRequest
import br.com.ghclone.model.request.NewRepoRequest
import br.com.ghclone.service.RepositoryService
import br.com.ghclone.service.UserService
import br.com.spring.dynamodb.model.request.UserUpdateRequest
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/repo")
class RepoController(
    private val repositoryService: RepositoryService
) {
    private val logger = KotlinLogging.logger {}

    @PostMapping
    suspend fun createRepo(
        @RequestBody projectRequest: NewRepoRequest,
    ) {
        logger.info { "saveUser: saving User " }
        repositoryService.createRepository(projectRequest.toRepo())
    }

    @GetMapping
    suspend fun getUser(
        @RequestParam("id") @NotBlank id: String,
        @RequestParam("document") document: String
    ): RepoEntity {
        logger.info { "getUser: finding user by document: $document Id: $id" }
        return repositoryService.getRepository(
            document = document,
            id = id,
        ).also {
            logger.info {
                "getUser: find successfully to " +
                        "document: $document Id: $id"
            }
        }
    }
//
//    @PutMapping("/{userId}")
//    suspend fun updateUser(
//        @Valid @RequestBody userUpdate: UserUpdateRequest,
//        @PathVariable userId: String,
//        @NotBlank @RequestHeader("document") document: String
//    ) {
//        logger.info {
//            "updateUser: updating user to document: $document, Id:" +
//                    userId
//        }
//        userService.updateUser(
//            userUpdateRequest = userUpdate,
//            document = document,
//            userId = userId
//        ).also {
//            logger.info {
//                "updateUser: updated user to document: $document, Id:" +
//                        userId
//            }
//        }
//    }
}
