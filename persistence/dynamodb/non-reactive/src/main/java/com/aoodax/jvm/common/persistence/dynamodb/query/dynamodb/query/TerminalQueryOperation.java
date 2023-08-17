package com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query;

public interface TerminalQueryOperation {
    <K, V, T> RootQueryExpressionContainer<K, V, T> end();
}
