package com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.expression.ValueExpression;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
public abstract class AbstractDynamoDbSingleStatementExpression extends AbstractDynamoDbStatementExpression<String, Object> {

    protected AbstractDynamoDbSingleStatementExpression(final String key, final ValueExpression<String, Object> valueExpression) {
        super(key, valueExpression);
    }

    @Override
    public Map<String, AttributeValue> getExpressionAttributes() {
        Optional<Object> value = valueExpression.getValue();

        if (value.isPresent()) {
            String attributeKey = valueExpression.getKey();
            AttributeValue attributeValue = new AttributeValue(value.get().toString());
            return Collections.singletonMap(attributeKey, attributeValue);
        } else {
            return Collections.emptyMap();
        }
    }
}
