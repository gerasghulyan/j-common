package com.aoodax.jvm.common.storage.s3client.impl;

import static com.aoodax.jvm.common.utils.validation.ParameterValidator.assertHasTextParameterArgument;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class S3IntegrationClientValidator {
    static void assertBucketAndKey(final String bucketName, final String key) {
        assertHasTextParameterArgument(bucketName, "bucketName");
        assertHasTextParameterArgument(key, "key");
    }
}
