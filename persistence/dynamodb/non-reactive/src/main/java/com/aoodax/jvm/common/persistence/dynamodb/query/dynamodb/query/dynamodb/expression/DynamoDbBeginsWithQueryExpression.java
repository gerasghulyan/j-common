package com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public final class DynamoDbBeginsWithQueryExpression extends AbstractDynamoDbSingleStatementExpression {

    private static final String BEGINS_WITH_EXPRESSION = "begins_with";

    public static DynamoDbBeginsWithQueryExpression of(final String key, final String value) {
        return new DynamoDbBeginsWithQueryExpression(key, DynamoDbValueExpression.of(key, value));
    }

    private DynamoDbBeginsWithQueryExpression(final String key, final DynamoDbValueExpression valueExpression) {
        super(key, valueExpression);
    }

    @Override
    public String getExpression() {
        return BEGINS_WITH_EXPRESSION + "(" + key + ", " + valueExpression.getKey() + ")";
    }
}
