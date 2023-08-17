package com.aoodax.jvm.common.persistence.dynamodb.reactive.query.dynamodb.expression

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression.DynamoDbBetweenQueryExpression
import com.aoodax.jvm.common.test.toolkit.Randomizer.Companion.generateRandomString
import org.apache.commons.codec.binary.Hex
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class DynamoDbBetweenQueryExpressionTest {

    @Test
    fun `Should throw an exception dynamodb between query expression of method when passing key null`() {
        assertThrows<IllegalArgumentException> {
            DynamoDbBetweenQueryExpression.of(
                    null,
                    generateRandomString(),
                    generateRandomString()
            )
        }
    }

    @Test
    fun `Should throw an exception dynamodb between query expression of method when passing from null`() {
        assertThrows<IllegalArgumentException> {
            DynamoDbBetweenQueryExpression.of(
                    generateRandomString(),
                    null,
                    generateRandomString()
            )
        }
    }

    @Test
    fun `Should throw an exception dynamodb between query expression of method when passing to null`() {
        assertThrows<IllegalArgumentException> {
            DynamoDbBetweenQueryExpression.of(
                    generateRandomString(),
                    generateRandomString(),
                    null
            )
        }
    }

    @Test
    fun `Should successfully create dynamodb between query expression`() {
        val key = generateRandomString()
        val from = generateRandomString()
        val to = generateRandomString()
        val expression = DynamoDbBetweenQueryExpression.of(key, from, to)

        assertThat(expression).isNotNull
        assertThat(expression.key).isEqualTo(key)
        assertThat(expression.expressionAttributes).containsKeys(expression.valueExpression.key._1)
        assertThat(expression.expressionAttributes).containsKeys(expression.valueExpression.key._2)
        assertThat(expression.expressionAttributes).containsValues(AttributeValue(from))
        assertThat(expression.expressionAttributes).containsValues(AttributeValue(to))
        assertThat(expression.valueExpression.key._1).isEqualTo(":" + key + "_" + Hex.encodeHexString(from.toByteArray()))
        assertThat(expression.valueExpression.key._2).isEqualTo(":" + key + "_" + Hex.encodeHexString(to.toByteArray()))
        assertThat(expression.expression).isEqualTo(key + " between " + expression.valueExpression.key._1 + " and " + expression.valueExpression.key._2)
    }
}