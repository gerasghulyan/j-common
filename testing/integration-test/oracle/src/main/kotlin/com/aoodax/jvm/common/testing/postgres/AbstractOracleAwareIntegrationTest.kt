package com.aoodax.jvm.common.testing.postgres

import com.aoodax.jvm.common.testing.postgres.container.OracleTestContainer
import org.junit.jupiter.api.BeforeAll

abstract class AbstractOracleAwareIntegrationTest {
    companion object {
        private val container = OracleTestContainer.prepareContainer()

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