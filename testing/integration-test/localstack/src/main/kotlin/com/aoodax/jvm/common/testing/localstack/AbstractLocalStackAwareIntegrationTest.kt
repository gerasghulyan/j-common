package com.aoodax.jvm.common.testing.localstack

import com.aoodax.jvm.common.testing.localstack.configuration.AwsLocalStackConfig
import com.aoodax.jvm.common.testing.localstack.container.AwsLocalStackContainer
import org.junit.jupiter.api.BeforeAll
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.localstack.LocalStackContainer

@ContextConfiguration(classes = [AwsLocalStackConfig::class])
abstract class AbstractLocalStackAwareIntegrationTest {
    companion object {
        private val container: LocalStackContainer = AwsLocalStackContainer.prepareContainer()

        @JvmStatic
        @BeforeAll
        fun setUp() {
            container.start()
        }

        @JvmStatic
        @DynamicPropertySource
        fun setDatasourceProperties(registry: DynamicPropertyRegistry) {
            AwsLocalStackContainer.setProperties(registry, container)
        }
    }
}