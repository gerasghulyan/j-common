package com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.expression.QueryExpression;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public final class DynamoDbOrQueryExpression extends AbstractDynamoDbConditionExpression {

    private static final String OR_EXPRESSION = " or ";

    public static QueryExpression<String, String, AttributeValue> of(
        final QueryExpression<String, String, AttributeValue> firstExpression,
        final QueryExpression<String, String, AttributeValue> secondExpression) {
        return new DynamoDbOrQueryExpression(firstExpression, secondExpression);
    }

    private DynamoDbOrQueryExpression(
        final QueryExpression<String, String, AttributeValue> firstExpression,
        final QueryExpression<String, String, AttributeValue> secondExpression) {
        super(firstExpression, secondExpression);
    }

    @Override
    public String getExpression() {
        return firstExpression.getExpression() + OR_EXPRESSION + secondExpression.getExpression();
    }
}
