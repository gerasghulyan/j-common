package com.aoodax.jvm.common.testing.memcached.container

import org.springframework.test.context.DynamicPropertyRegistry
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

class MemcachedContainer {


    companion object {
        private val DOCKER_TAG = DockerImageName.parse("memcached:1.6.19")

        fun prepareContainer(): GenericContainer<*> = GenericContainer(DOCKER_TAG)
                .withExposedPorts(11211)

        fun setProperties(registry: DynamicPropertyRegistry,container: GenericContainer<*>) {
            registry.add("memcached.cache.servers") { "${container.host}:${container.firstMappedPort}" }
            registry.add("memcached.cache.operation-timeout") { "1000" }
            registry.add("memcached.cache.provider") { "static" }
        }
    }

}
