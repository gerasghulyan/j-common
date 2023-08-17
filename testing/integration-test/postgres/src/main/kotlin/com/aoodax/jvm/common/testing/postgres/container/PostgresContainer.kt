package com.aoodax.jvm.common.testing.postgres.container

import com.github.dockerjava.api.command.InspectContainerResponse
import org.springframework.test.context.DynamicPropertyRegistry
import org.testcontainers.containers.PostgreSQLContainer

class PostgresContainer {
    companion object {
        private const val DOCKER_TAG = "postgres:14.1"
        private const val DB_INIT_SCRIPT = "database/init.sql"

        fun prepareContainer(schemaName: String): SchemaNameAwarePostgreSQLContainer {
            return SchemaNameAwarePostgreSQLContainer(DOCKER_TAG, schemaName)
                .withInitScript(DB_INIT_SCRIPT)
        }

        fun prepareContainer(): SchemaNameAwarePostgreSQLContainer {
            return prepareContainer(System.getProperty("spring.datasource.schema","test"))
        }

        fun setProperties(registry: DynamicPropertyRegistry, postgresSQLContainer: SchemaNameAwarePostgreSQLContainer) {
            //r2dbc credentials
            registry.add("spring.r2dbc.url") { buildPostgresSqlUrl("r2dbc", postgresSQLContainer) }
            registry.add("spring.r2dbc.username") { postgresSQLContainer.username }
            registry.add("spring.r2dbc.password") { postgresSQLContainer.password }
            registry.add("spring.datasource.schema") { postgresSQLContainer.getSchemaName() }
            //liquibase credentials
            registry.add("spring.liquibase.user") { postgresSQLContainer.username }
            registry.add("spring.liquibase.password") { postgresSQLContainer.password }
            registry.add("spring.liquibase.url") { buildPostgresSqlUrl("jdbc", postgresSQLContainer) }
            registry.add("spring.liquibase.enabled") { "true" }
        }

        private fun buildPostgresSqlUrl(driverPrefix: String, postgresSQLContainer: SchemaNameAwarePostgreSQLContainer): String =
            "$driverPrefix: postgresql://${postgresSQLContainer.host}:${postgresSQLContainer.firstMappedPort}/${postgresSQLContainer.databaseName}?currentSchema=${postgresSQLContainer.getSchemaName()}"
    }

    class SchemaNameAwarePostgreSQLContainer(dockerImageName: String, private val schemaName: String) :
        PostgreSQLContainer<SchemaNameAwarePostgreSQLContainer>(dockerImageName) {

        override fun containerIsStarted(containerInfo: InspectContainerResponse?) {
            databaseDelegate.execute("CREATE SCHEMA ${schemaName};", "cretae-schema-inline", 0, false, false)
            super.containerIsStarted(containerInfo)
        }

        fun getSchemaName(): String {
            return schemaName
        }
    }
}
