package com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.expression;

import java.util.Optional;

public interface ValueExpression<K, V> {
    K getKey();

    Optional<V> getValue();
}
