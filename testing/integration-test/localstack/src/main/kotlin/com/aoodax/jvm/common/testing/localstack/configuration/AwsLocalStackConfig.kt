package com.aoodax.jvm.common.testing.localstack.configuration

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.sns.AmazonSNS
import com.amazonaws.services.sns.AmazonSNSClient
import com.amazonaws.services.sqs.AmazonSQSAsync
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary

@TestConfiguration
class AwsLocalStackConfig(
    @Value("\${aws.region.static}")
    private val region: String,

    @Value("\${aws.accessKeyId}")
    private val accessKeyId: String,

    @Value("\${aws.secretKey}")
    private val secretAccessKey: String,

    @Value("\${aws.queue.sqs.url}")
    private val sqsUrl: String,

    @Value("\${aws.queue.sns.url}")
    private val snsUrl: String,

    @Value("\${aws.dynamodb.url}")
    private val dynamoDbUrl: String,
) {

    @Bean(destroyMethod = "shutdown")
    @Primary
    fun amazonSQS(): AmazonSQSAsync {
        return AmazonSQSAsyncClientBuilder.standard()
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(sqsUrl, region))
            .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(accessKeyId, secretAccessKey)))
            .build()
    }

    @Bean(destroyMethod = "shutdown")
    @Primary
    fun amazonSNS(): AmazonSNS {
        return AmazonSNSClient.builder()
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(snsUrl, region))
            .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(accessKeyId, secretAccessKey)))
            .build()
    }

    @Bean(destroyMethod = "shutdown")
    @Primary
    fun dynamoDb(): AmazonDynamoDB {
        return AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(dynamoDbUrl, region))
            .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(accessKeyId, secretAccessKey)))
            .build()
    }
}