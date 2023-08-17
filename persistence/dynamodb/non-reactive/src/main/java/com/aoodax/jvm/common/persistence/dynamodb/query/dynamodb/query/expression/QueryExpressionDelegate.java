package com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.expression;

import kotlin.jvm.functions.Function2;

public interface QueryExpressionDelegate<T, K, V> extends Function2<QueryExpression<T, K, V>, QueryExpression<T, K, V>, QueryExpression<T, K, V>> {
}
