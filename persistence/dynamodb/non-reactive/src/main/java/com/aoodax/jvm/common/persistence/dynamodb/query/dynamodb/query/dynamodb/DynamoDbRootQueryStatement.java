package com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.QueryStatement;
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.QueryStatementTransitionOperation;
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.RootQueryExpressionContainer;
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression.DynamoDbAndQueryExpression;
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression.DynamoDbBeginsWithQueryExpression;
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression.DynamoDbBetweenQueryExpression;
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression.DynamoDbEqualsQueryExpression;
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression.DynamoDbGreaterThanQueryExpression;
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression.DynamoDbOrQueryExpression;
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.expression.QueryExpression;
import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression.DynamoDbAttributeNotExistsQueryExpression;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.aoodax.jvm.common.utils.validation.ParameterValidator.assertHasTextParameterArgument;
import static com.aoodax.jvm.common.utils.validation.ParameterValidator.assertNotNullParameterArgument;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DynamoDbRootQueryStatement implements
    QueryStatement<String, String>,
    QueryStatementTransitionOperation<String, String>,
    RootQueryExpressionContainer<String, String, AttributeValue> {

    private static final String VALUE = "value";
    private static final String KEY = "key";
    private QueryExpression<String, String, AttributeValue> expression;

    public static QueryStatement<String, String> query() {
        return new DynamoDbRootQueryStatement();
    }

    @Override
    public QueryExpression<String, String, AttributeValue> get() {
        return expression;
    }

    @Override
    public void set(final QueryExpression<String, String, AttributeValue> expression) {
        assertNotNullParameterArgument(expression, "expression");
        this.expression = expression;
    }

    @Override
    public QueryStatementTransitionOperation<String, String> valueEquals(final String key, final String value) {
        assertHasTextParameterArgument(key, KEY);
        assertNotNullParameterArgument(value, VALUE);
        expression = DynamoDbEqualsQueryExpression.of(key, value);
        return this;
    }

    @Override
    public QueryStatementTransitionOperation<String, String> beginsWith(final String key, final String value) {
        assertHasTextParameterArgument(key, KEY);
        assertNotNullParameterArgument(value, VALUE);
        expression = DynamoDbBeginsWithQueryExpression.of(key, value);
        return this;
    }

    @Override
    public QueryStatementTransitionOperation<String, String> notExists(final String key) {
        assertHasTextParameterArgument(key, KEY);
        expression = DynamoDbAttributeNotExistsQueryExpression.of(key);
        return this;
    }

    @Override
    public QueryStatementTransitionOperation<String, String> greaterThan(String key, String value) {
        assertHasTextParameterArgument(key, KEY);
        assertNotNullParameterArgument(value, VALUE);
        expression = DynamoDbGreaterThanQueryExpression.of(key, value);
        return this;
    }

    @Override
    public QueryStatementTransitionOperation<String, String> between(String key, String from, String to) {
        assertHasTextParameterArgument(key, KEY);
        assertNotNullParameterArgument(from, "from");
        assertNotNullParameterArgument(to, "to");
        expression = DynamoDbBetweenQueryExpression.of(key, from, to);
        return this;
    }

    @Override
    public QueryStatement<String, String> and() {
        return new DynamoDbQueryDelegatingStatement(this, this, DynamoDbAndQueryExpression::of);
    }

    @Override
    public QueryStatement<String, String> or() {
        return new DynamoDbQueryDelegatingStatement(this, this, DynamoDbOrQueryExpression::of);
    }

    @Override
    @SuppressWarnings("unchecked")
    public RootQueryExpressionContainer<String, String, AttributeValue> end() {
        return this;
    }
}
