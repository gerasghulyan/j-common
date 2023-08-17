package com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.expression.QueryExpression;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public final class DynamoDbGreaterThanQueryExpression extends AbstractDynamoDbSingleStatementExpression implements QueryExpression<String, String, AttributeValue> {

    private static final String GREATER_THAN_EXPRESSION = " > ";

    public static DynamoDbGreaterThanQueryExpression of(final String key, final String value) {
        return new DynamoDbGreaterThanQueryExpression(key, DynamoDbValueExpression.of(key, value));
    }

    private DynamoDbGreaterThanQueryExpression(final String key, final DynamoDbValueExpression valueExpression) {
        super(key, valueExpression);
    }

    @Override
    public String getExpression() {
        return key + GREATER_THAN_EXPRESSION + valueExpression.getKey();
    }
}
