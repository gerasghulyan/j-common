package com.aoodax.jvm.common.persistence.dynamodb.reactive.query.dynamodb.expression;

import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression.DynamoDbNotExistsValueExpression
import com.aoodax.jvm.common.test.toolkit.Randomizer.Companion.generateEmptyString
import com.aoodax.jvm.common.test.toolkit.Randomizer.Companion.generateRandomString
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DynamoDbNotExistsValueExpressionTest {
    @Test
    fun `Should throw exception when creating not exists value expression by using of method when passing key empty string`() {
        assertThrows<IllegalArgumentException> { DynamoDbNotExistsValueExpression.of(generateEmptyString()) }
    }

    @Test
    fun `Should successfully create not exists value expression by using of method`() {
        val key = generateRandomString()
        val expression = DynamoDbNotExistsValueExpression.of(key)

        Assertions.assertThat(expression).isNotNull
        Assertions.assertThat(expression.key).isEqualTo(key)
        Assertions.assertThat(expression.value).isEmpty
    }
}