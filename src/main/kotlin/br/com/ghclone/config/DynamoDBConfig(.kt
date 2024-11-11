package br.com.ghclone.config

import br.com.ghclone.constants.DYNAMODB_URL_PROPERTY
import br.com.ghclone.enums.ProfileEnv
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.core.env.get
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.regions.providers.AwsRegionProvider
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import java.net.URI

@Configuration
class DynamoDBConfig(
    private val env: Environment, private val awsRegionProvider: AwsRegionProvider
) {

    private val logger = KotlinLogging.logger {}

    @Bean
    fun dynamoDb(): DynamoDbEnhancedAsyncClient {
        return DynamoDbEnhancedAsyncClient.builder().dynamoDbClient(dynamoDBConfiguration()).build()
    }

    fun dynamoDBConfiguration(): DynamoDbAsyncClient {
        logger.info { "Amazon DYNAMODB running with profile = ${env.activeProfiles.contentToString()}" }
        val builder = DynamoDbAsyncClient.builder()
            .region(awsRegionProvider.region)
        if (ProfileEnv.LOCAL.profile in env.activeProfiles) {
            val endpoint = env[DYNAMODB_URL_PROPERTY]
            endpoint?.let {
                logger.info { "Amazon DYNAMODB running with endpoint = $endpoint" }
                builder.endpointOverride(URI(endpoint))
            }
            builder.region(awsRegionProvider.region)
            return builder.build()
        }
        return DynamoDbAsyncClient.builder()
            .region(awsRegionProvider.region).build()

    }
}