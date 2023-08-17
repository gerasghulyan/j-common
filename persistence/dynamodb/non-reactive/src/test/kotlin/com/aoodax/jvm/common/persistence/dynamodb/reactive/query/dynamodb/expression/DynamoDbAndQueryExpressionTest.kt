package com.aoodax.jvm.common.persistence.dynamodb.reactive.query.dynamodb.expression


import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression.DynamoDbAndQueryExpression
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression.DynamoDbEqualsQueryExpression
import com.aoodax.jvm.common.test.toolkit.Randomizer.Companion.generateRandomString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DynamoDbAndQueryExpressionTest {

    @Test
    fun `Should throw an exception when passing first expression null`() {
        assertThrows<IllegalArgumentException> {
            DynamoDbAndQueryExpression.of(
                    null,
                    DynamoDbEqualsQueryExpression.of(generateRandomString(), generateRandomString())
            )
        }
    }

    @Test
    fun `Should throw an exception when passing second expression null`() {
        assertThrows<IllegalArgumentException> {
            DynamoDbAndQueryExpression.of(
                    DynamoDbEqualsQueryExpression.of(
                            generateRandomString(),
                            generateRandomString()
                    ), null
            )
        }
    }

    @Test
    fun `Should successfully create and query expression`() {
        val firstExpression = DynamoDbEqualsQueryExpression.of(generateRandomString(), generateRandomString())
        val secondExpression = DynamoDbEqualsQueryExpression.of(generateRandomString(), generateRandomString())
        val expression = DynamoDbAndQueryExpression.of(firstExpression, secondExpression)

        assertThat(expression).isNotNull
        assertThat(expression.expression).isEqualTo("${firstExpression.expression} and ${secondExpression.expression}")
        assertThat(expression.expressionAttributes).isNotEmpty
        assertThat(expression.expressionAttributes).containsKeys(firstExpression.valueExpression.key, secondExpression.valueExpression.key)
        assertThat(expression.expressionAttributes).containsValues(
            AttributeValue(firstExpression.valueExpression.value.get().toString()),
            AttributeValue(secondExpression.valueExpression.value.get().toString())
        )
    }
}
