package com.aoodax.jvm.common.persistence.dynamodb.reactive.query.dynamodb.expression


import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression.DynamoDbEqualsQueryExpression
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression.DynamoDbOrQueryExpression
import com.aoodax.jvm.common.test.toolkit.Randomizer.Companion.generateRandomString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DynamoDbOrQueryExpressionTest {

    @Test
    fun `Should throw an exception when passing first expression null`() {
        assertThrows<IllegalArgumentException> {
            DynamoDbOrQueryExpression.of(
                    null,
                    DynamoDbEqualsQueryExpression.of(generateRandomString(), generateRandomString())
            )
        }
    }

    @Test
    fun `Should throw an exception when passing second expression null`() {
        assertThrows<IllegalArgumentException> {
            DynamoDbOrQueryExpression.of(
                    DynamoDbEqualsQueryExpression.of(
                            generateRandomString(),
                            generateRandomString()
                    ), null
            )
        }
    }

    @Test
    fun `Should successfully create or query expression`() {
        val firstExpression = DynamoDbEqualsQueryExpression.of(generateRandomString(), generateRandomString())
        val secondExpression = DynamoDbEqualsQueryExpression.of(generateRandomString(), generateRandomString())
        val expression = DynamoDbOrQueryExpression.of(firstExpression, secondExpression)

        assertThat(expression).isNotNull
        assertThat(expression.expression).isEqualTo("${firstExpression.expression} or ${secondExpression.expression}")
        assertThat(expression.expressionAttributes).isNotEmpty
        assertThat(expression.expressionAttributes).containsKeys(firstExpression.valueExpression.key, secondExpression.valueExpression.key)
        assertThat(expression.expressionAttributes).containsValues(
            AttributeValue(firstExpression.valueExpression.value.get().toString()),
            AttributeValue(secondExpression.valueExpression.value.get().toString())
        )
    }
}