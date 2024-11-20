package br.com.ghclone.repository


import br.com.ghclone.constants.PERMISSION_TABLE_NAME
import br.com.ghclone.constants.TABLE_NAME
import br.com.ghclone.entity.dynamodb.PermissionSchema
import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.*
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest

@Repository
class PermissionRepository(
    private val dynamoDb: DynamoDbEnhancedAsyncClient
) : AbstractRepository<PermissionSchema>(dynamoDb) {

    override fun createTable(): DynamoDbAsyncTable<PermissionSchema> {
        return dynamoDb.table(PERMISSION_TABLE_NAME, TableSchema.fromBean(PermissionSchema::class.java))
    }

    override fun createGetRequest(entity: PermissionSchema): GetItemEnhancedRequest {
        return GetItemEnhancedRequest.builder()
            .key(
                Key.builder()
                    .partitionValue(entity.key?.pk)
                    .sortValue(entity.key?.sk)
                    .build()
            )
            .build()
    }

    override fun createSaveRequest(entity: PermissionSchema): PutItemEnhancedRequest<PermissionSchema> {
        val conditionExpression = Expression.builder()
            .expression("attribute_not_exists(pk) AND attribute_not_exists(sk)")
            .build()

        return PutItemEnhancedRequest.builder(PermissionSchema::class.java)
            .item(entity)
            .conditionExpression(conditionExpression)
            .build()
    }


//    suspend fun get2(document: String, id: String?): List<RepoContent> {
//        val key = if (id != null) {
//            Key.builder().partitionValue(document).sortValue(id).build()
//        } else {
//            Key.builder().partitionValue(document).build()
//        }
//        return table.query(QueryConditional.keyEqualTo(key)).items().awaitFirst()
//    }

//    override suspend fun update(entity: RepoContent) {
//        val updateItemEnhancedRequest = UpdateItemEnhancedRequest
//            .builder(RepoContent::class.java).item(entity).build()
//        table.updateItem(updateItemEnhancedRequest).await()
//    }
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