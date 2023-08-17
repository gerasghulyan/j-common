package com.aoodax.jvm.common.storage.s3client.exception;

public class S3JsonLineProcessingException extends RuntimeException {

    public S3JsonLineProcessingException() {
        super();
    }

    public S3JsonLineProcessingException(String message) {
        super(message);
    }

    public S3JsonLineProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public S3JsonLineProcessingException(Throwable cause) {
        super(cause);
    }
}
