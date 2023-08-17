package com.aoodax.jvm.common.testing.sample

import com.aoodax.jvm.common.testing.localstack.container.AwsLocalStackContainer
import com.aoodax.jvm.common.testing.postgres.container.PostgresContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@ExtendWith(SpringExtension::class)
@Testcontainers
class SampleLocalStackPostgresSQLIntegrationTest {

    @Value("\${spring.r2dbc.username}")
    private lateinit var postgresUsername: String

    @Value("\${aws.region.static}")
    private lateinit var region: String

    @Test
    fun `should register properties before test execution`() {
        assertThat(postgresContainer).hasFieldOrPropertyWithValue("username", postgresUsername)
        assertThat(awsLocalStackContainer).hasFieldOrPropertyWithValue("region", region)
    }

    companion object {

        @Container
        private val postgresContainer = PostgresContainer.prepareContainer()

        @Container
        private val awsLocalStackContainer = AwsLocalStackContainer.prepareContainer()

        @JvmStatic
        @DynamicPropertySource
        fun setDatasourceProperties(registry: DynamicPropertyRegistry) {
            PostgresContainer.setProperties(registry, postgresContainer)
            AwsLocalStackContainer.setProperties(registry, awsLocalStackContainer)
        }
    }

}