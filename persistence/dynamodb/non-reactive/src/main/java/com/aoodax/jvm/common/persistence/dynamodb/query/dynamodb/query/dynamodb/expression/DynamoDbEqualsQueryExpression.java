package com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.expression.QueryExpression;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public final class DynamoDbEqualsQueryExpression extends AbstractDynamoDbSingleStatementExpression implements QueryExpression<String, String, AttributeValue> {

    private static final String EQUALS_EXPRESSION = " = ";

    public static DynamoDbEqualsQueryExpression of(final String key, final String value) {
        return new DynamoDbEqualsQueryExpression(key, DynamoDbValueExpression.of(key, value));
    }

    private DynamoDbEqualsQueryExpression(final String key, final DynamoDbValueExpression valueExpression) {
        super(key, valueExpression);
    }

    @Override
    public String getExpression() {
        return key + EQUALS_EXPRESSION + valueExpression.getKey();
    }
}
