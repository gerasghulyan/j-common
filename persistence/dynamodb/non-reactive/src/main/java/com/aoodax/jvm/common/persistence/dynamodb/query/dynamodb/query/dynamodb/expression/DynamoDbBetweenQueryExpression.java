package com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import io.vavr.Tuple2;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
public final class DynamoDbBetweenQueryExpression extends AbstractDynamoDbStatementExpression<Tuple2<String, String>, Tuple2<String, String>> {

    private static final String BETWEEN_EXPRESSION = " between ";

    public static DynamoDbBetweenQueryExpression of(final String key, final String from, final String to) {
        return new DynamoDbBetweenQueryExpression(key, from, to);
    }

    private DynamoDbBetweenQueryExpression(final String key, final String from, final String to) {
        super(key, DynamoDbBetweenValueExpression.of(key, from, to));
    }

    @Override
    public String getExpression() {
        return key + BETWEEN_EXPRESSION + valueExpression.getKey()._1() + " and " + valueExpression.getKey()._2();
    }

    @Override
    public Map<String, AttributeValue> getExpressionAttributes() {
        Optional<Tuple2<String, String>> value = valueExpression.getValue();

        if (value.isPresent()) {
            String firstAttribute = valueExpression.getKey()._1();
            String secondAttribute = valueExpression.getKey()._2();

            AttributeValue firstAttributeValue = new AttributeValue(value.get()._1());
            AttributeValue secondAttributeValue = new AttributeValue(value.get()._2());

            Map<String, AttributeValue> expressionAttributes = new HashMap<>();
            expressionAttributes.put(firstAttribute, firstAttributeValue);
            expressionAttributes.put(secondAttribute, secondAttributeValue);

            return expressionAttributes;
        } else {
            return Collections.emptyMap();
        }
    }
}
