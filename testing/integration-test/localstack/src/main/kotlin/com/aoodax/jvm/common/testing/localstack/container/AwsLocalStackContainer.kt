package com.aoodax.jvm.common.testing.localstack.container

import com.aoodax.jvm.common.testing.localstack.AbstractLocalStackAwareIntegrationTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.testcontainers.containers.BindMode
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.utility.DockerImageName

class AwsLocalStackContainer {
    companion object {
        private const val DOCKER_TAG = "localstack/localstack:1.4"
        private const val QUEUE_NAME = "test-queue-name"
        private const val TOPIC_NAME = "aoodax-test-topic-name"
        private const val DYNAMO_TABLE_NAME = "aoodax-test-dynamo-table-name"

        fun prepareContainer(): LocalStackContainer {
            return LocalStackContainer(DockerImageName.parse(DOCKER_TAG))
                .withEnv("QUEUE_NAME", QUEUE_NAME)
                .withEnv("TOPIC_NAME", TOPIC_NAME)
                .withEnv("DYNAMO_TABLE_NAME", DYNAMO_TABLE_NAME)
                .withClasspathResourceMapping(
                    "/localstack", "/docker-entrypoint-initaws.d", BindMode.READ_ONLY
                )
                .withServices(
                    LocalStackContainer.Service.SQS,
                    LocalStackContainer.Service.SNS,
                    LocalStackContainer.Service.DYNAMODB
                )
                .waitingFor(Wait.forLogMessage(".*Initialized.\n", 1))
        }

        fun setProperties(registry: DynamicPropertyRegistry, container: LocalStackContainer) {
            registry.add("aws.accessKeyId") { container.accessKey }
            registry.add("aws.secretKey") { container.secretKey }
            registry.add("aws.queue.sqs.url") {
                container.getEndpointOverride(
                    LocalStackContainer.Service.SQS
                )
            }
            registry.add("aws.queue.sns.url") {
                container.getEndpointOverride(
                    LocalStackContainer.Service.SNS
                )
            }
            registry.add("aws.dynamodb.url") {
                container.getEndpointOverride(
                    LocalStackContainer.Service.DYNAMODB
                )
            }
            registry.add("aws.region.static") { container.region }
        }
    }
}
