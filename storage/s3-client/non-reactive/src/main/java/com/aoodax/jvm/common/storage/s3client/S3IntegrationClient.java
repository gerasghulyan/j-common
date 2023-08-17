package com.aoodax.jvm.common.storage.s3client;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public interface S3IntegrationClient {

    S3ObjectInputStream read(String bucketName, String key);

    CompletableFuture<S3ObjectInputStream> readAsync(String bucketName, String key);

    <T> Stream<T> readSequenceFromStream(InputStream stream, Class<T> cls);

    PutObjectResult save(String bucketName, String key, Object data);

    CompletableFuture<PutObjectResult> saveAsync(String bucketName, String key, Object data);
}
