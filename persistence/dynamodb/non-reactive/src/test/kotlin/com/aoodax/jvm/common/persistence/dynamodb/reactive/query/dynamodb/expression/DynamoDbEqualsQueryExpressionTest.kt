package com.aoodax.jvm.common.persistence.dynamodb.reactive.query.dynamodb.expression

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression.DynamoDbEqualsQueryExpression
import com.aoodax.jvm.common.test.toolkit.Randomizer.Companion.generateRandomString
import org.apache.commons.codec.binary.Hex
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class DynamoDbEqualsQueryExpressionTest {

    @Test
    fun `Should throw an exception dynamodb equals query expression of method when passing key null`() {
        assertThrows<IllegalArgumentException> { DynamoDbEqualsQueryExpression.of(null, generateRandomString()) }
    }

    @Test
    fun `Should throw an exception dynamodb equals query expression of method when passing value null`() {
        assertThrows<IllegalArgumentException> { DynamoDbEqualsQueryExpression.of(generateRandomString(), null) }
    }

    @Test
    fun `Should successfully create dynamodb equals query expression`() {
        val key = generateRandomString()
        val value = generateRandomString()
        val expression = DynamoDbEqualsQueryExpression.of(key, value)

        assertThat(expression).isNotNull
        assertThat(expression.key).isEqualTo(key)
        assertThat(expression.expressionAttributes).containsKeys(expression.valueExpression.key)
        assertThat(expression.expressionAttributes).containsValues(AttributeValue(value))
        assertThat(expression.valueExpression.key).isEqualTo(":" + key + "_" + Hex.encodeHexString(value.toByteArray()))
        assertThat(expression.expression).isEqualTo(key + " = " + expression.valueExpression.key)
    }
}