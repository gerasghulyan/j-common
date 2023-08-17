package com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.expression;

import java.util.Map;

public interface QueryExpression<T, K, V> {
    T getExpression();

    Map<K, V> getExpressionAttributes();
}
