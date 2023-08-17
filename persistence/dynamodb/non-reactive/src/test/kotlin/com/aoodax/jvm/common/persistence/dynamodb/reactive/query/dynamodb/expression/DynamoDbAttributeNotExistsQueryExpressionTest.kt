package com.aoodax.jvm.common.persistence.dynamodb.reactive.query.dynamodb.expression;

import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression.DynamoDbAttributeNotExistsQueryExpression
import com.aoodax.jvm.common.test.toolkit.Randomizer.Companion.generateRandomString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows

import org.junit.jupiter.api.Test;

class DynamoDbAttributeNotExistsQueryExpressionTest {

    @Test
    fun `Should throw an exception dynamodb not exists query expression of method when passing key null`() {
        assertThrows<IllegalArgumentException> { DynamoDbAttributeNotExistsQueryExpression.of(null) }
    }

    @Test
    fun `Should successfully create dynamodb not exists query expression`() {
        val key = generateRandomString()
        val expression = DynamoDbAttributeNotExistsQueryExpression.of(key)

        assertThat(expression).isNotNull
        assertThat(expression.key).isEqualTo(key)
        assertThat(expression.expressionAttributes).isEmpty()
        assertThat(expression.valueExpression.key).isEqualTo(key)
        assertThat(expression.expression).isEqualTo("attribute_not_exists($key)")
    }

}