package br.com.ghclone.repository

import br.com.ghclone.exception.BadRequestException
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.future.await
import kotlinx.coroutines.reactive.asFlow
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.model.*
import java.util.function.Consumer

abstract class AbstractRepository<T>(private val dynamoDb: DynamoDbEnhancedAsyncClient) {
    private val table: DynamoDbAsyncTable<T> by lazy { createTable() }
    private val logger = KotlinLogging.logger {}

    suspend fun save(entity: T): T {
        val exists = table.getItem(entity).await() != null
        if (exists) {
            logger.warn { "save: entity already exists $entity" }
            throw BadRequestException("Entity already exists")
        }
        val request = createSaveRequest(entity)
        logger.debug { "save: saving entity ${request.item()}" }
        table.putItem(request).await()
        return entity
    }

    suspend fun findOne(entity: T): T? {
        logger.debug { "findOne: finding entity $entity" }
        val request = createGetRequest(entity)
        return table.getItem(request).await()
    }


    suspend fun findOne(pk: String, sk: String): T? {
        return table.getItem(Key.builder().partitionValue(pk).sortValue(sk).build()).await()
    }

    fun findAllByPk(pk:String): Flow<MutableList<T>> {
        return table.query(QueryConditional.keyEqualTo(Key.builder().partitionValue(pk).build())).asFlow().map { it.items() }
    }

    abstract fun createSaveRequest(entity: T): PutItemEnhancedRequest<T>

    abstract fun createGetRequest(entity: T): GetItemEnhancedRequest

    abstract fun createTable(): DynamoDbAsyncTable<T>

}