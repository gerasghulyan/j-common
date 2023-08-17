package com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query;

import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.expression.QueryExpression;

public interface RootQueryExpressionContainer<T, K, V> {
    QueryExpression<T, K, V> get();

    void set(QueryExpression<T, K, V> expression);
}