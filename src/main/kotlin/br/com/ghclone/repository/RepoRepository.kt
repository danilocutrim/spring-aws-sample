package br.com.ghclone.repository


import br.com.ghclone.constants.TABLE_NAME
import br.com.ghclone.constants.USER_NOT_FOUND
import br.com.ghclone.exception.NotFoundException
import br.com.ghclone.model.entity.RepoEntity
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.future.await
import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.*
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional
import software.amazon.awssdk.enhanced.dynamodb.model.UpdateItemEnhancedRequest

@Repository
class RepoRepository(private val dynamoDb: DynamoDbEnhancedAsyncClient) : DataRepository<RepoEntity> {

    val logger = KotlinLogging.logger { }
    val table: DynamoDbAsyncTable<RepoEntity> = dynamoDb.table(TABLE_NAME, TableSchema.fromBean(RepoEntity::class.java))

    override suspend fun save(entity: RepoEntity): RepoEntity {
        val conditionExpression = Expression.builder()
            .expression("attribute_not_exists(repositoryName) AND attribute_not_exists(contentSk)")
            .build()

        val putItemRequest = PutItemEnhancedRequest.builder(RepoEntity::class.java)
            .item(entity)
            .conditionExpression(conditionExpression)
            .build()

        table.putItem(putItemRequest).await()
        return entity
    }

    suspend fun projectExists(hk:String, sk:String): Boolean {
        return table.getItem(Key.builder().partitionValue(hk).sortValue(sk).build()).await() !=null
    }

    override suspend fun get(document: String, id: String?): RepoEntity {
        val key = if (id != null) {
            Key.builder().partitionValue(document).sortValue(id).build()
        } else {
            Key.builder().partitionValue(document).build()
        }
        return table.getItem(key).await()
            ?: throw NotFoundException(
                USER_NOT_FOUND
            )
    }

//    suspend fun get2(document: String, id: String?): List<RepoEntity> {
//        val key = if (id != null) {
//            Key.builder().partitionValue(document).sortValue(id).build()
//        } else {
//            Key.builder().partitionValue(document).build()
//        }
//        return table.query(QueryConditional.keyEqualTo(key)).items().awaitFirst()
//    }

    override suspend fun update(entity: RepoEntity) {
        val updateItemEnhancedRequest = UpdateItemEnhancedRequest
            .builder(RepoEntity::class.java).item(entity).build()
        table.updateItem(updateItemEnhancedRequest).await()
    }
//
//    override suspend fun update(entity: User) {
//
//        logger.debug {
//            "update: update user," +
//                    " document: ${entity.document}" +
//                    " id: ${entity.userId}"
//        }
//        val updateItemSpec = UpdateItemSpec().withPrimaryKey(
//            "document",
//            entity.document,
//            "userId",
//            entity.userId
//        )
//            .withUpdateExpression(
//                " set birthDate = :bd," +
//                        " country = :ct," +
//                        " lastName = :ln," +
//                        " userName = :na," +
//                        " nickName = :nn"
//            )
//            .withValueMap(
//                ValueMap()
//                    .withString(":bd", entity.birthDate)
//                    .withString(":nn", entity.nickName)
//                    .withString(":ct", entity.country)
//                    .withString(":ln", entity.lastName)
//                    .withString(":na", entity.name)
//            )
//            .withReturnValues(ReturnValue.UPDATED_NEW)
//        dynamoDb.getTable(TABLE_NAME).updateItem(updateItemSpec)
//
//        logger.debug {
//            "update: updated user Success," +
//                    " document: ${entity.document}" +
//                    " id: ${entity.userId}"
//        }
//    }
//
//    fun listAll(document: String): User? {
//        return mapper.load(User::class.java, document)
//    }
}