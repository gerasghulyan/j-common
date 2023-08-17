package com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression;

import static com.aoodax.jvm.common.utils.validation.ParameterValidator.assertNotNullParameterArgument;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.expression.QueryExpression;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@EqualsAndHashCode
public abstract class AbstractDynamoDbConditionExpression implements QueryExpression<String, String, AttributeValue> {

    protected final QueryExpression<String, String, AttributeValue> firstExpression;
    protected final QueryExpression<String, String, AttributeValue> secondExpression;

    protected AbstractDynamoDbConditionExpression(
        final QueryExpression<String, String, AttributeValue> firstExpression,
        final QueryExpression<String, String, AttributeValue> secondExpression) {

        assertNotNullParameterArgument(firstExpression, "firstExpression");
        assertNotNullParameterArgument(secondExpression, "secondExpression");

        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
    }

    @Override
    public final Map<String, AttributeValue> getExpressionAttributes() {
        return Stream.concat(
                firstExpression.getExpressionAttributes().entrySet().stream(),
                secondExpression.getExpressionAttributes().entrySet().stream())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
