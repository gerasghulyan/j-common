package com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.QueryStatement;
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.QueryStatementTransitionOperation;
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.RootQueryExpressionContainer;
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression.DynamoDbBeginsWithQueryExpression;
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression.DynamoDbBetweenQueryExpression;
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression.DynamoDbEqualsQueryExpression;
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression.DynamoDbGreaterThanQueryExpression;
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.expression.QueryExpressionDelegate;
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression.DynamoDbAttributeNotExistsQueryExpression;
import lombok.RequiredArgsConstructor;

import static com.aoodax.jvm.common.utils.validation.ParameterValidator.assertHasTextParameterArgument;
import static com.aoodax.jvm.common.utils.validation.ParameterValidator.assertNotNullParameterArgument;

@RequiredArgsConstructor
public final class DynamoDbQueryDelegatingStatement implements QueryStatement<String, String> {

    private static final String VALUE = "value";
    private static final String KEY = "key";
    private final RootQueryExpressionContainer<String, String, AttributeValue> container;
    private final QueryStatementTransitionOperation<String, String> transitionOperation;
    private final QueryExpressionDelegate<String, String, AttributeValue> delegate;

    @Override
    public QueryStatementTransitionOperation<String, String> valueEquals(final String key, final String value) {
        assertHasTextParameterArgument(key, KEY);
        assertNotNullParameterArgument(value, VALUE);

        container.set(delegate.invoke(container.get(), DynamoDbEqualsQueryExpression.of(key, value)));
        return transitionOperation;
    }

    @Override
    public QueryStatementTransitionOperation<String, String> beginsWith(final String key, final String value) {
        assertHasTextParameterArgument(key, KEY);
        assertNotNullParameterArgument(value, VALUE);

        container.set(delegate.invoke(container.get(), DynamoDbBeginsWithQueryExpression.of(key, value)));
        return transitionOperation;
    }

    @Override
    public QueryStatementTransitionOperation<String, String> notExists(final String key) {
        assertHasTextParameterArgument(key, KEY);

        container.set(delegate.invoke(container.get(), DynamoDbAttributeNotExistsQueryExpression.of(key)));
        return transitionOperation;
    }

    @Override
    public QueryStatementTransitionOperation<String, String> greaterThan(String key, String value) {
        assertHasTextParameterArgument(key, KEY);
        assertNotNullParameterArgument(value, VALUE);

        container.set(delegate.invoke(container.get(), DynamoDbGreaterThanQueryExpression.of(key, value)));
        return transitionOperation;
    }

    @Override
    public QueryStatementTransitionOperation<String, String> between(String key, String from, String to) {
        assertHasTextParameterArgument(key, KEY);
        assertNotNullParameterArgument(from, "from");
        assertNotNullParameterArgument(to, "to");

        container.set(delegate.invoke(container.get(), DynamoDbBetweenQueryExpression.of(key, from, to)));
        return transitionOperation;
    }
}
