package com.aoodax.jvm.common.storage.s3client.impl;

import static com.aoodax.jvm.common.storage.s3client.impl.S3IntegrationClientValidator.assertBucketAndKey;
import static com.aoodax.jvm.common.utils.validation.ParameterValidator.assertNotNullParameterArgument;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.aoodax.jvm.common.storage.s3client.ReactiveS3IntegrationClient;
import com.aoodax.jvm.common.storage.s3client.S3IntegrationClient;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class ReactiveS3IntegrationClientImpl implements ReactiveS3IntegrationClient {
   private final S3IntegrationClient s3IntegrationClient;

    @Override
    public Mono<S3ObjectInputStream> read(final String bucketName, final String key) {
        assertBucketAndKey(bucketName, key);

        return Mono.fromFuture(() -> s3IntegrationClient.readAsync(bucketName, key))
                .doFirst(() -> log.trace("fetching from s3 bucket: {} - {}", bucketName, key))
                .doOnSuccess(it -> log.debug("Successfully fetched from s3 bucket: {} - {}", bucketName, key))
                .doOnError(th -> log.error("Exception occurred during reading data in s3", th));
    }

    @Override
    public <T> Flux<T> readSequence(final String bucketName, final String key, Class<T> cls) {
        assertBucketAndKey(bucketName, key);
        assertNotNullParameterArgument(cls, "cls");

        return read(bucketName, key)
                .map(it -> Try.ofCallable(() -> Flux.fromStream(s3IntegrationClient.readSequenceFromStream(it, cls))))
                .flatMapMany(Try::get)
                .doOnError(th -> log.error("Exception occurred during reading from s3", th));
    }

    @Override
    public Mono<PutObjectResult> save(final String bucketName, final String key, final Object data) {
        assertBucketAndKey(bucketName, key);
        assertNotNullParameterArgument(data, "data");

        return Mono.fromFuture(() -> s3IntegrationClient.saveAsync(bucketName, key, data))
                .doFirst(() -> log.trace("Uploading into s3 bucket: {} - {}", bucketName, key))
                .doOnSuccess(it -> log.debug("Successfully uploaded into s3 bucket: {} - {}", bucketName, key))
                .doOnError(cause -> log.error("Exception occurred during saving data in s3", cause));
    }
}
