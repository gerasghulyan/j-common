package com.aoodax.jvm.common.testing.mongodb

import com.aoodax.jvm.common.testing.mongodb.container.MongoContainer
import org.junit.jupiter.api.BeforeAll
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource

abstract class AbstractMongoDbAwareIntegrationTest {
    companion object {
        private val container = MongoContainer.prepareContainer()

        @JvmStatic
        @BeforeAll
        fun setUp() {
            container.start()
        }

        @JvmStatic
        @DynamicPropertySource
        fun setDatasourceProperties(registry: DynamicPropertyRegistry) {
            MongoContainer.setProperties(registry, container)
        }
    }
}