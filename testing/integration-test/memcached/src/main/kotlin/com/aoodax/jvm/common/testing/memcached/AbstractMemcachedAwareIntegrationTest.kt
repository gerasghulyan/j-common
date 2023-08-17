package com.aoodax.jvm.common.testing.memcached

import com.aoodax.jvm.common.testing.memcached.container.MemcachedContainer
import org.junit.jupiter.api.BeforeAll
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource

abstract class AbstractMemcachedAwareIntegrationTest {
    companion object {
        private val container = MemcachedContainer.prepareContainer()

        @JvmStatic
        @BeforeAll
        fun setUp() {
            container.start()
        }

        @JvmStatic
        @DynamicPropertySource
        fun setDatasourceProperties(registry: DynamicPropertyRegistry) {
            MemcachedContainer.setProperties(registry, container)
        }
    }
}