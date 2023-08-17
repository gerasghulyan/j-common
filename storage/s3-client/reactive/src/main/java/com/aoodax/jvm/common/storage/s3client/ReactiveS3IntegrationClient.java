package com.aoodax.jvm.common.storage.s3client;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveS3IntegrationClient {
    Mono<S3ObjectInputStream> read(String bucketName, String key);

    <T> Flux<T> readSequence(final String bucketName, final String key, Class<T> cls);

    Mono<PutObjectResult> save(String bucketName, String key, Object data);
}
