package com.aoodax.jvm.common.testing.mongodb.container

import org.springframework.test.context.DynamicPropertyRegistry
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.utility.DockerImageName

class MongoContainer {
    companion object {
        private val DOCKER_TAG = DockerImageName.parse("mongo:6.0.9")

        fun prepareContainer(): MongoDBContainer {
            return MongoDBContainer(DOCKER_TAG).withCommand("--replSet docker-rs")
        }

        fun setProperties(registry: DynamicPropertyRegistry, container: MongoDBContainer) {
            registry.add("spring.data.mongodb.uri") { container.replicaSetUrl }
            registry.add("spring.data.mongodb.database") { "test" }
        }
    }
}
