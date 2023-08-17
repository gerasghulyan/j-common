package com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.expression.QueryExpression;
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.expression.ValueExpression;
import com.aoodax.jvm.common.utils.validation.ParameterValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Map;

@Getter
@EqualsAndHashCode
public abstract class AbstractDynamoDbStatementExpression<T, V> implements QueryExpression<String, String, AttributeValue> {

    protected final String key;
    protected final ValueExpression<T, V> valueExpression;

    protected AbstractDynamoDbStatementExpression(final String key, final ValueExpression<T, V> valueExpression) {
        validateConstructorArguments(key, valueExpression);

        this.key = key;
        this.valueExpression = valueExpression;
    }

    public abstract Map<String, AttributeValue> getExpressionAttributes();

    private void validateConstructorArguments(String key, ValueExpression<T, V> valueExpression) {
        ParameterValidator.assertHasTextParameterArgument(key, "key");
        ParameterValidator.assertNotNullParameterArgument(valueExpression, "valueExpression");
    }
}
