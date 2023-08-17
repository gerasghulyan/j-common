package com.aoodax.jvm.common.persistence.dynamodb.reactive.query.dynamodb

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.DynamoDbRequestBuilder
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.DynamoDbRootQueryStatement
import com.aoodax.jvm.common.test.toolkit.Randomizer.Companion.generateEmptyString
import com.aoodax.jvm.common.test.toolkit.Randomizer.Companion.generateRandomString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DynamoDbRequestBuilderTest {

    @Test
    fun `Should throw an exception when passing index empty string`() {
        assertThrows<IllegalArgumentException> { DynamoDbRequestBuilder.builder<Any>().fromIndex(generateEmptyString()) }
    }

    @Test
    fun `Should throw an exception when passing key statement null`() {
        assertThrows<IllegalArgumentException> { DynamoDbRequestBuilder.builder<Any>().withKeyStatement(null) }
    }

    @Test
    fun `Should throw an exception when passing filter statement null`() {
        assertThrows<IllegalArgumentException> { DynamoDbRequestBuilder.builder<Any>().withFilterStatement(null) }
    }

    @Test
    fun `Should successfully create dynamo db expression`() {
        val index = generateRandomString()
        val keyStatement = DynamoDbRootQueryStatement.query()
            .valueEquals(generateRandomString(), generateRandomString())
            .end<String, String, AttributeValue>()
        val filterStatement = DynamoDbRootQueryStatement.query()
            .valueEquals(generateRandomString(), generateRandomString())
            .end<String, String, AttributeValue>()


        val dynamoDbExpression = DynamoDbRequestBuilder.builder<Any>()
            .fromIndex(index)
            .withKeyStatement(keyStatement)
            .withFilterStatement(filterStatement)
            .withConsistentRead(true)
            .build()

        assertThat(dynamoDbExpression).isNotNull
        assertThat(dynamoDbExpression.indexName).isEqualTo(index)
        assertThat(dynamoDbExpression.keyConditionExpression).isEqualTo(keyStatement.get().expression)
        assertThat(dynamoDbExpression.filterExpression).isEqualTo(filterStatement.get().expression)
        assertThat(dynamoDbExpression.isConsistentRead).isTrue
    }
}