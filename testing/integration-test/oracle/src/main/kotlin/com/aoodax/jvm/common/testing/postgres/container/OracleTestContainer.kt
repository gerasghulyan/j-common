package com.aoodax.jvm.common.testing.postgres.container

import org.springframework.test.context.DynamicPropertyRegistry
import org.testcontainers.containers.OracleContainer

class OracleTestContainer {
    companion object {
        private const val DOCKER_TAG = "gvenzl/oracle-xe:21-slim-faststart"

        fun prepareContainer(schemaName: String): OracleContainer {
            return OracleContainer(DOCKER_TAG)
        }

        fun prepareContainer(): OracleContainer {
            return prepareContainer(System.getProperty("spring.datasource.schema", "test"))
        }

        fun setProperties(registry: DynamicPropertyRegistry, container: OracleContainer) {
            registry.add("spring.datasource.schema") { container.jdbcUrl }
        }

        private fun buildPostgresSqlUrl(container: OracleContainer): String {
            return container.jdbcUrl
        }
    }
}
