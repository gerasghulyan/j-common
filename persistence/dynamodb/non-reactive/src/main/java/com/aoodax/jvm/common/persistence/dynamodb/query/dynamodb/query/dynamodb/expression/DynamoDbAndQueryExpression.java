package com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.expression.QueryExpression;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public final class DynamoDbAndQueryExpression extends AbstractDynamoDbConditionExpression {

    private static final String AND_EXPRESSION = " and ";

    public static QueryExpression<String, String, AttributeValue> of(
        final QueryExpression<String, String, AttributeValue> firstExpression,
        final QueryExpression<String, String, AttributeValue> secondExpression) {
        return new DynamoDbAndQueryExpression(firstExpression, secondExpression);
    }

    private DynamoDbAndQueryExpression(
        final QueryExpression<String, String, AttributeValue> firstExpression,
        final QueryExpression<String, String, AttributeValue> secondExpression) {
        super(firstExpression, secondExpression);
    }

    @Override
    public String getExpression() {
        return firstExpression.getExpression() + AND_EXPRESSION + secondExpression.getExpression();
    }
}
