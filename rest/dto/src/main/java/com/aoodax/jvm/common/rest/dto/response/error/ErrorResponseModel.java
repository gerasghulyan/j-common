package com.aoodax.jvm.common.rest.dto.response.error;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ErrorResponseModel {

    private final ErrorReasonResponseModel error;

}
