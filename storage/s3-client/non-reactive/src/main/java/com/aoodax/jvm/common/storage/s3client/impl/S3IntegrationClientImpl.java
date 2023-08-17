package com.aoodax.jvm.common.storage.s3client.impl;

import static com.aoodax.jvm.common.storage.s3client.impl.S3IntegrationClientValidator.assertBucketAndKey;
import static com.aoodax.jvm.common.utils.validation.ParameterValidator.assertNotNullParameterArgument;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.aoodax.jvm.common.storage.s3client.S3IntegrationClient;
import com.aoodax.jvm.common.storage.s3client.exception.S3JsonLineProcessingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Stream;

@AllArgsConstructor
public class S3IntegrationClientImpl implements S3IntegrationClient {

    private final AmazonS3 amazonS3;
    private final ObjectMapper objectMapper;
    private final Executor s3Executor;

    @Override
    public S3ObjectInputStream read(final String bucketName, final String key) {
        assertBucketAndKey(bucketName, key);
        return getS3ObjectInputStream(bucketName, key);
    }

    @Override
    public CompletableFuture<S3ObjectInputStream> readAsync(final String bucketName, final String key) {
        assertBucketAndKey(bucketName, key);
        return CompletableFuture.supplyAsync(() -> getS3ObjectInputStream(bucketName, key), s3Executor);
    }

    @Override
    public <T> Stream<T> readSequenceFromStream(final InputStream stream, final Class<T> cls) {
        return new BufferedReader(new InputStreamReader(stream)).lines()
                .map(it -> Try.ofCallable(() -> objectMapper.readValue(it, cls)))
                .map(Try::get);
    }

    @Override
    public PutObjectResult save(final String bucketName, final String key, final Object data) {
        assertBucketAndKey(bucketName, key);
        assertNotNullParameterArgument(data, "data");
        return getPutObjectResult(bucketName, key, data);
    }

    @Override
    public CompletableFuture<PutObjectResult> saveAsync(final String bucketName, final String key, final Object data) {
        assertBucketAndKey(bucketName, key);
        assertNotNullParameterArgument(data, "data");
        return CompletableFuture.supplyAsync(() -> getPutObjectResult(bucketName, key, data), s3Executor);
    }

    private byte[] createJsonDataFromDataList(final Object data) {
        try {
            if (data instanceof JSONObject || data instanceof JSONArray) {
                final String str = data.toString();
                return str.getBytes();
            }
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException cause) {
            throw new S3JsonLineProcessingException("Exception occurred during json serialization");
        }
    }

    private S3ObjectInputStream getS3ObjectInputStream(final String bucketName, final String key) {
        final var request = new GetObjectRequest(bucketName, key);
        return amazonS3.getObject(request).getObjectContent();
    }

    private PutObjectResult getPutObjectResult(String bucketName, String key, Object data) {
        final var jsonData = createJsonDataFromDataList(data);
        final var metadata = new ObjectMetadata();
        metadata.setContentLength(jsonData.length);
        final var request = new PutObjectRequest(bucketName, key, new ByteArrayInputStream(jsonData), metadata);
        return amazonS3.putObject(request);
    }
}
