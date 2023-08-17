package com.aoodax.jvm.common.persistence.dynamodb.reactive.query.dynamodb.expression

import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression.DynamoDbValueExpression
import com.aoodax.jvm.common.test.toolkit.Randomizer.Companion.generateEmptyString
import com.aoodax.jvm.common.test.toolkit.Randomizer.Companion.generateRandomString
import org.apache.commons.codec.binary.Hex
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DynamoDbValueExpressionTest {

    @Test
    fun `Should throw exception when creating value expression by using of method when passing key empty string`() {
        assertThrows<IllegalArgumentException> {
            DynamoDbValueExpression.of(
                    generateEmptyString(),
                    generateRandomString()
            )
        }
    }

    @Test
    fun `Should throw exception when creating value expression by using of method when passing value null`() {
        assertThrows<IllegalArgumentException> { DynamoDbValueExpression.of(generateRandomString(), null) }
    }

    @Test
    fun `Should throw exception when creating value expression by using of with only value method when passing value null`() {
        assertThrows<IllegalArgumentException> { DynamoDbValueExpression.of(null) }
    }

    @Test
    fun `Should successfully create value expression by using of method with two arguments`() {
        val key = generateRandomString()
        val value = generateRandomString()
        val expression = DynamoDbValueExpression.of(key, value)

        assertThat(expression).isNotNull
        assertThat(expression.key).isEqualTo(":${key}_${Hex.encodeHexString(value.toByteArray())}")
        assertThat(expression.value).contains(value)
    }

    @Test
    fun `Should successfully create value expression by using of method with one arguments`() {
        val value = generateRandomString()
        val expression = DynamoDbValueExpression.of(value)

        assertThat(expression).isNotNull
        assertThat(expression.key).isEqualTo(":key_${Hex.encodeHexString(value.toByteArray())}")
        assertThat(expression.value).contains(value)
    }
}
