package br.com.ghclone.controller

import br.com.ghclone.model.entity.RepoEntity
import br.com.ghclone.model.request.ContentRequest
import br.com.ghclone.model.request.NewRepoRequest
import br.com.ghclone.service.RepoService
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/repo")
class RepoController(
    private val repoService: RepoService
) {
    private val logger = KotlinLogging.logger {}

    @PostMapping
    suspend fun createRepo(
        @Valid @RequestBody projectRequest: NewRepoRequest,
    ) {
        logger.info { "saveUser: saving User " }
        repoService.createRepository(projectRequest.toRepo())
    }

    @PostMapping("/update")
    suspend fun createOrUpdateContent(
        @Valid @RequestBody contentRequest: ContentRequest,
    ) {
        logger.info { "saveUser: saving User " }
        repoService.addOrUpdateContent(contentRequest)
    }

    @GetMapping
    suspend fun getUser(
        @RequestParam("repoName") @NotBlank repoName: String,
        @RequestParam("path", required = false) path: String? = null
    ): RepoEntity {
        logger.info { "getUser: finding user by document: $path Id: $repoName" }
        return repoService.getRepository(
            document = repoName,
            id = path,
        ).also {
            logger.info {
                "getUser: find successfully to " +
                        "document: $path Id: $repoName"
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
