package br.com.ghclone.config

import aws.sdk.kotlin.services.s3.S3Client
import aws.smithy.kotlin.runtime.net.url.Url
import br.com.ghclone.constants.DYNAMODB_URL_PROPERTY
import br.com.ghclone.enums.ProfileEnv
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.core.env.get
import software.amazon.awssdk.regions.providers.AwsRegionProvider


@Configuration
class S3ConfigConfig(
    private val env: Environment, private val awsRegionProvider: AwsRegionProvider
) {

    private val logger = KotlinLogging.logger {}

    @Bean
    fun s3ClientAsync(): S3Client {
        logger.info { "Amazon S3 running with profile = ${env.activeProfiles.contentToString()}" }
        if (ProfileEnv.LOCAL.profile in env.activeProfiles) {
            return S3Client {
                region = awsRegionProvider.region.id()
                endpointUrl = env[DYNAMODB_URL_PROPERTY]?.let { Url.parse(it) }
            }
        }
        return S3Client {
            region = awsRegionProvider.region.id()
        }


    }
}