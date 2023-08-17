package com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query;

public interface QueryStatement<K, V> {
    QueryStatementTransitionOperation<K, V> valueEquals(K key, K value);

    QueryStatementTransitionOperation<K, V> beginsWith(K key, K value);

    QueryStatementTransitionOperation<K, V> notExists(K key);

    QueryStatementTransitionOperation<K,V> greaterThan(K key, V value);

    QueryStatementTransitionOperation<K,V> between(K key, V from , V to);
}
