package com.aoodax.jvm.common.testing.mongodb.container

import org.springframework.test.context.DynamicPropertyRegistry
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.utility.DockerImageName

class MongoContainer {
    companion object {
        private val DOCKER_TAG = DockerImageName.parse("mongo:4.0.10")

        fun prepareContainer(): MongoDBContainer {
            return MongoDBContainer(DOCKER_TAG)
        }

        fun setProperties(registry: DynamicPropertyRegistry, container: MongoDBContainer) {
            registry.add("spring.data.mongodb.uri") { container.replicaSetUrl }
        }
    }
}
