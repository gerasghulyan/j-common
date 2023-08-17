package com.aoodax.jvm.common.persistence.dynamodb.reactive.query.dynamodb.expression

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression.DynamoDbGreaterThanQueryExpression
import com.aoodax.jvm.common.test.toolkit.Randomizer.Companion.generateRandomString
import org.apache.commons.codec.binary.Hex
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class DynamoDbGreaterThanQueryExpressionTest {

    @Test
    fun `Should throw an exception dynamodb greater then query expression of method when passing key null`() {
        assertThrows<IllegalArgumentException> { DynamoDbGreaterThanQueryExpression.of(null, generateRandomString()) }
    }

    @Test
    fun `Should throw an exception dynamodb greater then query expression of method when passing value null`() {
        assertThrows<IllegalArgumentException> { DynamoDbGreaterThanQueryExpression.of(generateRandomString(), null) }
    }

    @Test
    fun `Should successfully create dynamodb greater then query expression`() {
        val key = generateRandomString()
        val value = generateRandomString()
        val expression = DynamoDbGreaterThanQueryExpression.of(key, value)

        assertThat(expression).isNotNull
        assertThat(expression.key).isEqualTo(key)
        assertThat(expression.expressionAttributes).containsKeys(expression.valueExpression.key)
        assertThat(expression.expressionAttributes).containsValues(AttributeValue(value))
        assertThat(expression.valueExpression.key).isEqualTo(":" + key + "_" + Hex.encodeHexString(value.toByteArray()))
        assertThat(expression.expression).isEqualTo(key + " > " + expression.valueExpression.key)
    }
}