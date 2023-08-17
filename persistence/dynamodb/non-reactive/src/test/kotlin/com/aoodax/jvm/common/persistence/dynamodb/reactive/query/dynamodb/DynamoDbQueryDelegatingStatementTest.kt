package com.aoodax.jvm.common.persistence.dynamodb.reactive.query.dynamodb

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.QueryStatementTransitionOperation
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.RootQueryExpressionContainer
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.expression.QueryExpressionDelegate
import com.aoodax.jvm.common.test.toolkit.Randomizer.Companion.generateEmptyString
import com.aoodax.jvm.common.test.toolkit.Randomizer.Companion.generateRandomString
import com.aoodax.jvm.common.testing.unit.AbstractMockitoAwareUnitTest
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.DynamoDbQueryDelegatingStatement
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.Mockito.*


class DynamoDbQueryDelegatingStatementTest : AbstractMockitoAwareUnitTest() {

    @Mock
    private lateinit var container: RootQueryExpressionContainer<String, String, AttributeValue>

    @Mock
    private lateinit var transitionOperation: QueryStatementTransitionOperation<String, String>

    @Mock
    private lateinit var delegate: QueryExpressionDelegate<String, String, AttributeValue>

    @Test
    fun `Should throw an exception when passing valueEquals method key empty string`() {
        val statement = DynamoDbQueryDelegatingStatement(container, transitionOperation, delegate)

        assertThrows<IllegalArgumentException> { statement.valueEquals(generateEmptyString(), generateRandomString()) }
    }

    @Test
    fun `Should throw an exception when passing valueEquals method value null`() {
        val statement = DynamoDbQueryDelegatingStatement(container, transitionOperation, delegate)

        assertThrows<IllegalArgumentException> { statement.valueEquals(generateRandomString(), null) }
    }

    @Test
    fun `Should throw an exception when passing beginsWith method key empty string`() {
        val statement = DynamoDbQueryDelegatingStatement(container, transitionOperation, delegate)

        assertThrows<IllegalArgumentException> { statement.beginsWith(generateEmptyString(), generateRandomString()) }
    }

    @Test
    fun `Should throw an exception when passing beginsWith method value null`() {
        val statement = DynamoDbQueryDelegatingStatement(container, transitionOperation, delegate)

        assertThrows<IllegalArgumentException> { statement.beginsWith(generateRandomString(), null) }
    }

    @Test
    fun `Should throw an exception when passing notExists method key empty string`() {
        val statement = DynamoDbQueryDelegatingStatement(container, transitionOperation, delegate)

        assertThrows<IllegalArgumentException> { statement.notExists(generateEmptyString()) }
    }

    @Test
    fun `Should successfully create dynamodb query delegating statement`() {
        val statement = DynamoDbQueryDelegatingStatement(container, transitionOperation, delegate)

        assertThat(statement).isNotNull
    }

    @Test
    fun `Should successfully return condition after calling valueEquals method`() {
        val statement = DynamoDbQueryDelegatingStatement(container, transitionOperation, delegate)
        val key = generateRandomString()
        val value = generateRandomString()
        val firstExpression = DynamoDbBeginsWithQueryExpression.of(key, value)
        val secondExpression = DynamoDbEqualsQueryExpression.of(key, value)
        val rootExpression = DynamoDbAndQueryExpression.of(firstExpression, secondExpression)

        `when`(container.get()).thenReturn(firstExpression)
        `when`(delegate.invoke(firstExpression, secondExpression)).thenReturn(rootExpression)

        val condition = statement.valueEquals(key, value)

        assertThat(condition).isNotNull
        verify(container, times(1)).get()
        verify(container, times(1)).set(rootExpression)
        verify(delegate, times(1)).invoke(firstExpression, secondExpression)
    }

    @Test
    fun `Should successfully return condition after calling greater than method`() {
        val statement = DynamoDbQueryDelegatingStatement(container, transitionOperation, delegate)
        val key = generateRandomString()
        val value = generateRandomString()
        val firstExpression = DynamoDbBeginsWithQueryExpression.of(key, value)
        val secondExpression = DynamoDbGreaterThanQueryExpression.of(key, value)
        val rootExpression = DynamoDbAndQueryExpression.of(firstExpression, secondExpression)

        `when`(container.get()).thenReturn(firstExpression)
        `when`(delegate.invoke(firstExpression, secondExpression)).thenReturn(rootExpression)

        val condition = statement.greaterThan(key, value)

        assertThat(condition).isNotNull
        verify(container, times(1)).get()
        verify(container, times(1)).set(rootExpression)
        verify(delegate, times(1)).invoke(firstExpression, secondExpression)
    }

    @Test
    fun `Should successfully return condition after calling between method`() {
        val statement = DynamoDbQueryDelegatingStatement(container, transitionOperation, delegate)
        val key = generateRandomString()
        val value = generateRandomString()
        val from = generateRandomString()
        val to = generateRandomString()
        val firstExpression = DynamoDbBeginsWithQueryExpression.of(key, value)
        val secondExpression = DynamoDbBetweenQueryExpression.of(key, from, to)
        val rootExpression = DynamoDbAndQueryExpression.of(firstExpression, secondExpression)

        `when`(container.get()).thenReturn(firstExpression)
        `when`(delegate.invoke(firstExpression, secondExpression)).thenReturn(rootExpression)

        val condition = statement.between(key, from, to)

        assertThat(condition).isNotNull
        verify(container, times(1)).get()
        verify(container, times(1)).set(rootExpression)
        verify(delegate, times(1)).invoke(firstExpression, secondExpression)
    }

    @Test
    fun `Should successfully return condition after calling beginsWith method`() {
        val statement = DynamoDbQueryDelegatingStatement(container, transitionOperation, delegate)
        val key = generateRandomString()
        val value = generateRandomString()
        val firstExpression = DynamoDbEqualsQueryExpression.of(key, value)
        val secondExpression = DynamoDbBeginsWithQueryExpression.of(key, value)
        val rootExpression = DynamoDbAndQueryExpression.of(firstExpression, secondExpression)

        `when`(container.get()).thenReturn(firstExpression)
        `when`(delegate.invoke(firstExpression, secondExpression)).thenReturn(rootExpression)

        val condition = statement.beginsWith(key, value)

        assertThat(condition).isNotNull
        verify(container, times(1)).get()
        verify(container, times(1)).set(rootExpression)
        verify(delegate, times(1)).invoke(firstExpression, secondExpression)
    }


    @Test
    fun `Should successfully return condition after calling notExists method`() {
        val statement = DynamoDbQueryDelegatingStatement(container, transitionOperation, delegate)
        val key = generateRandomString()
        val value = generateRandomString()
        val firstExpression = DynamoDbEqualsQueryExpression.of(key, value)
        val secondExpression = DynamoDbAttributeNotExistsQueryExpression.of(key)
        val rootExpression = DynamoDbAndQueryExpression.of(firstExpression, secondExpression)

        `when`(container.get()).thenReturn(firstExpression)
        `when`(delegate.invoke(firstExpression, secondExpression)).thenReturn(rootExpression)

        val condition = statement.notExists(key)

        assertThat(condition).isNotNull
        verify(container, times(1)).get()
        verify(container, times(1)).set(rootExpression)
        verify(delegate, times(1)).invoke(firstExpression, secondExpression)
    }

}