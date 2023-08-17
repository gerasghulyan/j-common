package com.aoodax.jvm.common.testing.postgres

import com.aoodax.jvm.common.testing.postgres.container.PostgresContainer
import org.junit.jupiter.api.BeforeAll

abstract class AbstractPostgresAwareIntegrationTest {
    companion object {
        private val container = PostgresContainer.prepareContainer()

        @JvmStatic
        @BeforeAll
        fun setUp() {
            container.start()
        }

        /**
         * implement in microservice where the library will be used
         * @JvmStatic
         * @DynamicPropertySource
         * fun setDatasourceProperties(registry: DynamicPropertyRegistry) {
         *      registerProperties(registry)
         *  }
         * private fun registerProperties(registry: DynamicPropertyRegistry) {
         *      register properties in microservice project per need spring.r2dbc.url, spring.liquibase.url or spring.datasource
         *  }
        * */

    }
}