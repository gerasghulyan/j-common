package com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb;

import static com.aoodax.jvm.common.utils.validation.ParameterValidator.assertHasTextParameterArgument;
import static com.aoodax.jvm.common.utils.validation.ParameterValidator.assertNotNullParameterArgument;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.RootQueryExpressionContainer;
import java.util.HashMap;
import java.util.Objects;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DynamoDbRequestBuilder<T> {

    private final DynamoDBQueryExpression<T> dynamoDbQueryExpression = new DynamoDBQueryExpression<>();
    private RootQueryExpressionContainer<String, String, AttributeValue> keyStatementContainer;
    private RootQueryExpressionContainer<String, String, AttributeValue> filterStatementContainer;

    public static <T> DynamoDbRequestBuilder<T> builder() {
        return new DynamoDbRequestBuilder<>();
    }

    public DynamoDbRequestBuilder<T> fromIndex(final String name) {
        assertHasTextParameterArgument(name, "name");
        dynamoDbQueryExpression.withIndexName(name);
        return this;
    }

    public DynamoDbRequestBuilder<T> withConsistentRead(final boolean consistentRead) {
        dynamoDbQueryExpression.withConsistentRead(consistentRead);
        return this;
    }

    public DynamoDbRequestBuilder<T> withKeyStatement(final RootQueryExpressionContainer<String, String, AttributeValue> statement) {
        assertNotNullParameterArgument(statement, "statement");
        this.keyStatementContainer = statement;
        return this;
    }

    public DynamoDbRequestBuilder<T> withFilterStatement(final RootQueryExpressionContainer<String, String, AttributeValue> statement) {
        assertNotNullParameterArgument(statement, "statement");
        this.filterStatementContainer = statement;
        return this;
    }

    public DynamoDBQueryExpression<T> build() {
        final var attributeValues = new HashMap<String, AttributeValue>();
        if (Objects.nonNull(keyStatementContainer)) {
            dynamoDbQueryExpression.withKeyConditionExpression(keyStatementContainer.get().getExpression());
            attributeValues.putAll(keyStatementContainer.get().getExpressionAttributes());
        }
        if (Objects.nonNull(filterStatementContainer)) {
            dynamoDbQueryExpression.withFilterExpression(filterStatementContainer.get().getExpression());
            attributeValues.putAll(filterStatementContainer.get().getExpressionAttributes());
        }
        dynamoDbQueryExpression.withExpressionAttributeValues(attributeValues);
        return dynamoDbQueryExpression;
    }
}
