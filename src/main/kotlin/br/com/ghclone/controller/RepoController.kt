package br.com.ghclone.controller

import br.com.ghclone.domain.Info
import br.com.ghclone.entity.dynamodb.RepoSchema
import br.com.ghclone.model.request.NewRepoRequest
import br.com.ghclone.model.response.SuccessResponse
import br.com.ghclone.service.RepoService
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.web.bind.annotation.*
import software.amazon.awssdk.enhanced.dynamodb.model.Page

@RestController
@RequestMapping("/repo")
class RepoController(
    private val repoService: RepoService
) {
    private val logger = KotlinLogging.logger {}

    @PostMapping
    suspend fun createRepo(
        @Valid @RequestBody projectRequest: NewRepoRequest,
    ): SuccessResponse<Info> {
        logger.info { "saveUser: saving User " }
        return SuccessResponse(repoService.createRepository(projectRequest))
    }

//    @PostMapping("/update")
//    suspend fun createOrUpdateContent(
//        @Valid @RequestBody contentRequest: ContentRequest,
//    ) {
//        logger.info { "saveUser: saving User " }
//        repoService.addOrUpdateContent(contentRequest)
//    }

    @GetMapping
    suspend fun findRepo(
        @RequestParam("repoName") @NotBlank repoName: String,
        @RequestParam("path", required = false) path: String? = null
    ): RepoSchema? {
        logger.info { "getUser: finding user by document: $path Id: $repoName" }
        return repoService.findRepo(
            name = repoName
        ).also {
            logger.info {
                "getUser: find successfully to " +
                        "document: $path Id: $repoName"
            }
        }
    }

    @GetMapping("/{repoName}")
    suspend fun findRepo2(
        @PathVariable("repoName") @NotBlank repoName: String,
    ): List<RepoSchema> {
        val list = mutableListOf<RepoSchema>()
        return repoService.findRepos(
            name = repoName
        ).map { it.items() }.toList().flatten().also {
            logger.info {
                "getUser: find successfully to " +
                        "document: $repoName"
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
