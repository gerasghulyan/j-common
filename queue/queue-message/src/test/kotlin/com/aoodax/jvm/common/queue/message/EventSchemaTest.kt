package com.aoodax.jvm.common.queue.message

import com.aoodax.jvm.common.test.toolkit.Randomizer.Companion.generateEmptyString
import com.aoodax.jvm.common.test.toolkit.Randomizer.Companion.generateRandomString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class EventSchemaTest {
    @Test
    fun `should throw an exception when passing type null`() {
        assertThrows<IllegalArgumentException> {
            QueueEvent.of(null, Object())
        }
    }

    @Test
    fun `should throw an exception when passing type empty string`() {
        assertThrows<IllegalArgumentException> {
            QueueEvent.of(generateEmptyString(), Object())
        }
    }

    @Test
    fun `should throw an exception when passing data null`() {
        assertThrows<IllegalArgumentException> {
            QueueEvent.of(generateRandomString(), null)
        }
    }

    @Test
    fun `should successfully create EventSchema by using of method`() {
        val type = generateRandomString()
        val data = Object()
        val message = QueueEvent.of(type, data)
        assertThat(message).isNotNull
        assertThat(message.type).isEqualTo(type)
        assertThat(message.data).isEqualTo(data)
        assertThat(message.timestamp).isPositive
    }

}