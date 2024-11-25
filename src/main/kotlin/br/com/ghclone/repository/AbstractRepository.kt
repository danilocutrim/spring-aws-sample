package br.com.ghclone.repository

import br.com.ghclone.exception.BadRequestException
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.future.await
import kotlinx.coroutines.reactive.asFlow
import software.amazon.awssdk.annotations.NotThreadSafe
import software.amazon.awssdk.core.SdkBytes
import software.amazon.awssdk.enhanced.dynamodb.*
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.internal.AttributeValues
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.utils.Validate
import java.util.*

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


//class Key private constructor(builder: Key.Builder) {
//    private val partitionValue: AttributeValue?
//    private val sortValue: AttributeValue?
//
//    init {
//        Validate.isTrue(
//            builder.partitionValue != null && builder.partitionValue != AttributeValues.nullAttributeValue(),
//            "partitionValue should not be null",
//            *arrayOfNulls(0)
//        )
//        this.partitionValue = builder.partitionValue
//        this.sortValue = builder.sortValue
//    }
//
//    fun keyMap(tableSchema: TableSchema<*>, index: String): Map<String?, AttributeValue?> {
//        val keyMap: MutableMap<String?, AttributeValue?> = HashMap<Any?, Any?>()
//        keyMap[tableSchema.tableMetadata().indexPartitionKey(index)] = partitionValue
//        if (this.sortValue != null) {
//            keyMap[tableSchema.tableMetadata().indexSortKey(index).orElseThrow {
//                IllegalArgumentException(
//                    "A sort key value was supplied for an index that does not support one. Index: $index"
//                )
//            } as String] =
//                sortValue
//        }
//
//        return Collections.unmodifiableMap(keyMap)
//    }
//
//    fun partitionKeyValue(): AttributeValue? {
//        return this.partitionValue
//    }
//
//    fun sortKeyValue(): Optional<AttributeValue> {
//        return Optional.ofNullable(this.sortValue)
//    }
//
//    fun primaryKeyMap(tableSchema: TableSchema<*>): Map<String?, AttributeValue?> {
//        return this.keyMap(tableSchema, TableMetadata.primaryIndexName())
//    }
//
//    fun toBuilder(): Key.Builder {
//        return (Key.Builder()).partitionValue(this.partitionValue).sortValue(this.sortValue)
//    }
//
//    override fun equals(o: Any?): Boolean {
//        if (this === o) {
//            return true
//        } else if (o != null && this.javaClass == o.javaClass) {
//            val key = o as Key
//            if (this.partitionValue != null) {
//                if (this.partitionValue == key.partitionValue) {
//                    return if (this.sortValue != null) (this.sortValue == key.sortValue) else key.sortValue == null
//                }
//            } else if (key.partitionValue == null) {
//                return if (this.sortValue != null) (this.sortValue == key.sortValue) else key.sortValue == null
//            }
//
//            return false
//        } else {
//            return false
//        }
//    }
//
//    override fun hashCode(): Int {
//        var result = if (this.partitionValue != null) partitionValue.hashCode() else 0
//        result = 31 * result + (if (this.sortValue != null) sortValue.hashCode() else 0)
//        return result
//    }
//
//    @NotThreadSafe
//    class Builder private constructor() {
//        private var partitionValue: AttributeValue? = null
//        private var sortValue: AttributeValue? = null
//
//        fun partitionValue(partitionValue: AttributeValue?): Key.Builder {
//            this.partitionValue = partitionValue
//            return this
//        }
//
//        fun partitionValue(partitionValue: String?): Key.Builder {
//            this.partitionValue = AttributeValues.stringValue(partitionValue)
//            return this
//        }
//
//        fun partitionValue(partitionValue: Number?): Key.Builder {
//            this.partitionValue = AttributeValues.numberValue(partitionValue)
//            return this
//        }
//
//        fun partitionValue(partitionValue: SdkBytes?): Key.Builder {
//            this.partitionValue = AttributeValues.binaryValue(partitionValue)
//            return this
//        }
//
//        fun sortValue(sortValue: AttributeValue?): Key.Builder {
//            this.sortValue = sortValue
//            return this
//        }
//
//        fun sortValue(sortValue: String?): Key.Builder {
//            this.sortValue = AttributeValues.stringValue(sortValue)
//            return this
//        }
//
//        fun sortValue(sortValue: Number?): Key.Builder {
//            this.sortValue = AttributeValues.numberValue(sortValue)
//            return this
//        }
//
//        fun sortValue(sortValue: SdkBytes?): Key.Builder {
//            this.sortValue = AttributeValues.binaryValue(sortValue)
//            return this
//        }
//
//        fun build(): Key {
//            return Key(this)
//        }
//    }
//
//    companion object {
//        fun builder(): Key.Builder {
//            return Key.Builder()
//        }
//    }
//}
