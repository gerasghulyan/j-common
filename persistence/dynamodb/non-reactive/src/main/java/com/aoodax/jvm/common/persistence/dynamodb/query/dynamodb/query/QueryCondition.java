package com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query;

public interface QueryCondition<K, V>  {
    QueryStatement<K, V> and();

    QueryStatement<K, V> or();
}
