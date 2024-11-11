package br.com.ghclone.service

import br.com.ghclone.model.entity.User
import br.com.ghclone.model.request.UserSaveRequest
import br.com.spring.dynamodb.model.request.UserUpdateRequest
import br.com.ghclone.model.response.UserSaveResponse
import br.com.ghclone.repository.UserRepository
import io.github.oshai.kotlinlogging.KotlinLogging

import org.springframework.stereotype.Service

@Service
class UserService(
    val repository: UserRepository
) {
    val logger = KotlinLogging.logger {}

    suspend fun saveUser(
        userSaveRequest: UserSaveRequest,
    ): UserSaveResponse {
        logger.info { "saveUser: saving user, document: ${userSaveRequest.documentNumber}" }
        val response =
            repository.save(
                User(
                    document = userSaveRequest.documentNumber,
                    lastName = userSaveRequest.lastName,
                    country = userSaveRequest.country,
                    nickName = userSaveRequest.nickName,
                    birthDate = userSaveRequest.birthDate,
                    name = userSaveRequest.name
                )
            )
        return response.let { UserSaveResponse(it.userId, it.document) }
            .also {
                logger.info {
                    "saveUser: saved user" +
                            "document: ${it.document}" +
                            "Id: ${it.id}"
                }
            }
    }

    suspend fun getUser(id: String, document: String): User {

        logger.info { "getUser: finding user,document: $document, Id: $id" }
        return repository.get(document = document, id = id)
            .also {
                logger.info {
                    "getUser: find successfully for ," +
                            "document: $document, Id: $id"
                }
            }
    }

    suspend fun updateUser(
        userUpdateRequest: UserUpdateRequest,
        userId: String,
        document: String
    ) {
        logger.info {
            "updateUser: updating to document: $document," +
                    " Id:$userId"
        }

        val entityToUpdate = repository.get(document = document, id = userId)
        val user = User(
            userId = userId,
            document = document,
            nickName = userUpdateRequest.nickName ?: entityToUpdate.nickName,
            country = userUpdateRequest.country ?: entityToUpdate.country,
            lastName = userUpdateRequest.lastName ?: entityToUpdate.lastName,
            name = userUpdateRequest.name ?: entityToUpdate.name,
            birthDate = userUpdateRequest.birthDate ?: entityToUpdate.birthDate
        )
        repository.update(user)
            .also {
                logger.info {
                    "updateUser: update user with success, id: ${user.userId}"
                }
            }
    }
}