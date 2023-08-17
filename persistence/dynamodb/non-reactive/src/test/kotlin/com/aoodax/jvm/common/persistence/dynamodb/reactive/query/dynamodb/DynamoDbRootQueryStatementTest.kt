package com.aoodax.jvm.common.persistence.dynamodb.reactive.query.dynamodb

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.aoodax.jvm.common.test.toolkit.Randomizer.Companion.generateEmptyString
import com.aoodax.jvm.common.test.toolkit.Randomizer.Companion.generateRandomString
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.DynamoDbRootQueryStatement
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DynamoDbRootQueryStatementTest {

    @Test
    fun `Should throw an exception when passing valueEquals method key empty string`() {
        assertThrows<IllegalArgumentException> {
            DynamoDbRootQueryStatement.query().valueEquals(generateEmptyString(), generateRandomString())
        }
    }

    @Test
    fun `Should throw an exception when passing valueEquals method value null`() {
        assertThrows<IllegalArgumentException> {
            DynamoDbRootQueryStatement.query().valueEquals(generateRandomString(), null)
        }
    }

    @Test
    fun `Should throw an exception when passing beginsWith method key empty string`() {
        assertThrows<IllegalArgumentException> {
            DynamoDbRootQueryStatement.query().beginsWith(generateEmptyString(), generateRandomString())
        }
    }

    @Test
    fun `Should throw an exception when passing beginsWith method value null`() {
        assertThrows<IllegalArgumentException> {
            DynamoDbRootQueryStatement.query().beginsWith(generateRandomString(), null)
        }
    }

    @Test
    fun `Should throw an exception when passing notExists method key empty string`() {
        assertThrows<IllegalArgumentException> { DynamoDbRootQueryStatement.query().notExists(generateEmptyString()) }
    }

    @Test
    fun `Should successfully create dynamodb root query statement`() {
        val query = DynamoDbRootQueryStatement.query()

        assertThat(query).isNotNull
    }

    @Test
    fun `Should successfully create valueEquals expression`() {
        val key = generateRandomString()
        val value = generateRandomString()
        val query = DynamoDbRootQueryStatement.query()
            .valueEquals(key, value)
            .end<String, String, AttributeValue>()

        assertThat(query.get()).isEqualTo(DynamoDbEqualsQueryExpression.of(key, value))
    }

    @Test
    fun `Should successfully create greater than expression`() {
        val key = generateRandomString()
        val value = generateRandomString()
        val query = DynamoDbRootQueryStatement.query()
            .greaterThan(key, value)
            .end<String, String, AttributeValue>()

        assertThat(query.get()).isEqualTo(DynamoDbGreaterThanQueryExpression.of(key, value))
    }

    @Test
    fun `Should successfully create between expression`() {
        val key = generateRandomString()
        val from = generateRandomString()
        val to = generateRandomString()
        val query = DynamoDbRootQueryStatement.query()
            .between(key, from, to)
            .end<String, String, AttributeValue>()

        assertThat(query.get()).isEqualTo(DynamoDbBetweenQueryExpression.of(key, from, to))
    }

    @Test
    fun `Should successfully create beginsWith expression`() {
        val key = generateRandomString()
        val value = generateRandomString()
        val query = DynamoDbRootQueryStatement.query()
            .beginsWith(key, value)
            .end<String, String, AttributeValue>()

        assertThat(query.get()).isEqualTo(DynamoDbBeginsWithQueryExpression.of(key, value))
    }

    @Test
    fun `Should successfully create notExists expression`() {
        val key = generateRandomString()
        val query = DynamoDbRootQueryStatement.query()
            .notExists(key)
            .end<String, String, AttributeValue>()

        assertThat(query.get()).isEqualTo(DynamoDbAttributeNotExistsQueryExpression.of(key))
    }

    @Test
    fun `Should successfully create beginsWith and valueEquals expression`() {
        val key = generateRandomString()
        val value = generateRandomString()
        val equalsValue = generateRandomString()
        val query = DynamoDbRootQueryStatement.query()
            .beginsWith(key, value)
            .and()
            .valueEquals(key, equalsValue)
            .end<String, String, AttributeValue>()

        assertThat(query.get()).isEqualTo(
            DynamoDbAndQueryExpression.of(
                DynamoDbBeginsWithQueryExpression.of(key, value),
                DynamoDbEqualsQueryExpression.of(key, equalsValue)
            )
        )
    }

    @Test
    fun `Should successfully create beginsWith or notExists expression`() {
        val key = generateRandomString()
        val value = generateRandomString()
        val query = DynamoDbRootQueryStatement.query()
            .beginsWith(key, value)
            .or()
            .notExists(key)
            .end<String, String, AttributeValue>()

        assertThat(query.get()).isEqualTo(
            DynamoDbOrQueryExpression.of(
                DynamoDbBeginsWithQueryExpression.of(key, value),
                DynamoDbAttributeNotExistsQueryExpression.of(key)
            )
        )
    }

    @Test
    fun `Should successfully create between and greater than expression`() {
        val key = generateRandomString()
        val value = generateRandomString()
        val from = generateRandomString()
        val to = generateRandomString()
        val query = DynamoDbRootQueryStatement.query()
            .greaterThan(key, value)
            .and()
            .between(key, from, to)
            .end<String, String, AttributeValue>()

        assertThat(query.get()).isEqualTo(
            DynamoDbAndQueryExpression.of(
                DynamoDbGreaterThanQueryExpression.of(key, value),
                DynamoDbBetweenQueryExpression.of(key, from, to)
            )
        )
    }

}
