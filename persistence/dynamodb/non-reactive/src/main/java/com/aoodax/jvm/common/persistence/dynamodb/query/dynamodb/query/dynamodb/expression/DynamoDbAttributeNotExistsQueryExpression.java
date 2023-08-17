package com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class DynamoDbAttributeNotExistsQueryExpression extends AbstractDynamoDbSingleStatementExpression {

    private static final String EXPRESSION = "attribute_not_exists";

    public static DynamoDbAttributeNotExistsQueryExpression of(final String key) {
        return new DynamoDbAttributeNotExistsQueryExpression(key);
    }

    private DynamoDbAttributeNotExistsQueryExpression(final String key) {
        super(key, DynamoDbNotExistsValueExpression.of(key));
    }

    @Override
    public String getExpression() {
        return EXPRESSION + "(" + key + ")";
    }
}
