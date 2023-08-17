package com.aoodax.jvm.common.persistence.dynamodb.reactive;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDeleteExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedList;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionLoadRequest;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionWriteRequest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class ReactiveDynamoDbMapper {

    private final DynamoDBMapper dynamoDbMapper;
    private final Executor executor;

    public <T> Mono<T> load(final T keyObject) {
        return load(keyObject, DynamoDBMapperConfig.DEFAULT);
    }

    public <T> Mono<T> load(final T keyObject, final DynamoDBMapperConfig config) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> dynamoDbMapper.load(keyObject, config), executor));
    }

    public <T> Mono<T> load(final Class<T> clazz, final Object hashKey) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> dynamoDbMapper.load(clazz, hashKey), executor));
    }

    public <T> Mono<T> load(final Class<T> clazz, final Object hashKey, final Object rangeKey) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> dynamoDbMapper.load(clazz, hashKey, rangeKey), executor));
    }

    public <T> Mono<T> load(final Class<T> clazz, final Object hashKey, final Object rangeKey, final DynamoDBMapperConfig config) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> dynamoDbMapper.load(clazz, hashKey, rangeKey, config), executor));
    }

    public <T> Mono<PaginatedQueryList<T>> query(final Class<T> clazz, final DynamoDBQueryExpression<T> expression) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> dynamoDbMapper.query(clazz, expression), executor));
    }

    public <T> Mono<PaginatedQueryList<T>> query(final Class<T> clazz, final DynamoDBQueryExpression<T> expression, DynamoDBMapperConfig config) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> dynamoDbMapper.query(clazz, expression, config), executor));
    }

    public <T> Mono<QueryResultPage<T>> queryPage(final Class<T> clazz, final DynamoDBQueryExpression<T> expression, DynamoDBMapperConfig config) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> dynamoDbMapper.queryPage(clazz, expression, config), executor));
    }

    public <T> Mono<QueryResultPage<T>> queryPage(final Class<T> clazz, final DynamoDBQueryExpression<T> expression) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> dynamoDbMapper.queryPage(clazz, expression), executor));
    }

    public <T> Mono<Void> save(final T object) {
        return Mono.fromFuture(CompletableFuture.runAsync(() -> dynamoDbMapper.save(object), executor));
    }

    public <T> Mono<Void> save(final T object, final DynamoDBSaveExpression expression) {
        return Mono.fromFuture(CompletableFuture.runAsync(() -> dynamoDbMapper.save(object, expression), executor));
    }

    public <T> Mono<Void> save(final T object, final DynamoDBSaveExpression expression, final DynamoDBMapperConfig config) {
        return Mono.fromFuture(CompletableFuture.runAsync(() -> dynamoDbMapper.save(object, expression, config), executor));
    }

    public <T> Mono<Void> delete(final T object) {
        return Mono.fromFuture(CompletableFuture.runAsync(() -> dynamoDbMapper.delete(object, new DynamoDBDeleteExpression(), DynamoDBMapperConfig.DEFAULT), executor));
    }

    public <T> Mono<Void> delete(final T object, final DynamoDBDeleteExpression expression, final DynamoDBMapperConfig config) {
        return Mono.fromFuture(CompletableFuture.runAsync(() -> dynamoDbMapper.delete(object, expression, config), executor));
    }

    public <T> Mono<PaginatedList<T>> scan(final Class<T> clazz, final DynamoDBScanExpression expression) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> dynamoDbMapper.scan(clazz, expression), executor));
    }

    public <T> Mono<PaginatedList<T>> scan(final Class<T> clazz, final DynamoDBScanExpression expression, final DynamoDBMapperConfig config) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> dynamoDbMapper.scan(clazz, expression, config), executor));
    }

    public <T> Mono<PaginatedList<T>> parallelScan(final Class<T> clazz, final DynamoDBScanExpression expression, final int totalSegments) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> dynamoDbMapper.parallelScan(clazz, expression, totalSegments), executor));
    }

    public <T> Mono<PaginatedList<T>> parallelScan(final Class<T> clazz, final DynamoDBScanExpression expression, final int totalSegments, final DynamoDBMapperConfig config) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> dynamoDbMapper.parallelScan(clazz, expression, totalSegments, config), executor));
    }

    public Flux<Object> transactionLoad(final TransactionLoadRequest request) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> dynamoDbMapper.transactionLoad(request), executor))
                .flatMapMany(Flux::fromIterable);
    }

    public Mono<Void> transactionWrite(final TransactionWriteRequest request) {
        return Mono.fromFuture(CompletableFuture.runAsync(() -> dynamoDbMapper.transactionWrite(request), executor));
    }

    public Mono<Void> transactWrite(final TransactionWriteRequest request, final DynamoDBMapperConfig config) {
        return Mono.fromFuture(CompletableFuture.runAsync(() -> dynamoDbMapper.transactionWrite(request, config), executor));
    }

    public <T> Flux<DynamoDBMapper.FailedBatch> batchWrite(final Iterable<T> objectsToWrite,
                                                           final Iterable<T> objectsToDelete,
                                                           final DynamoDBMapperConfig config) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> dynamoDbMapper.batchWrite(objectsToWrite, objectsToDelete, config), executor))
                .flatMapMany(Flux::fromIterable);
    }
}
