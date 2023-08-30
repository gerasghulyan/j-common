package com.aoodax.jvm.common.rest.dto.response;

import com.aoodax.jvm.common.rest.dto.response.error.ErrorResponseModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public abstract class ResultResponseDto<R extends ResponseDto> {

    private static final int DEFAULT_STATUS_CODE = 200;

    @JsonProperty("response")
    private final R response;

    @JsonProperty("errors")
    private final List<ErrorResponseModel> errors;

    @JsonIgnore
    private final int statusCode;

    protected ResultResponseDto() {
        this(null, null, DEFAULT_STATUS_CODE);
    }

    protected ResultResponseDto(final R response,
                                final List<ErrorResponseModel> errors,
                                final int statusCode) {
        this.response = response;
        this.errors = errors;
        this.statusCode = statusCode;
    }

    protected ResultResponseDto(final R response) {
        this(response, null, DEFAULT_STATUS_CODE);
    }

    protected ResultResponseDto(final R response,
                                final int statusCode) {
        this(response, null, statusCode);
    }

    protected ResultResponseDto(final List<ErrorResponseModel> errors) {
        this(null, errors, DEFAULT_STATUS_CODE);
    }

    protected ResultResponseDto(final List<ErrorResponseModel> errors,
                                final int statusCode) {
        this(null, errors, statusCode);
    }

}
