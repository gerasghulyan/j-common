package com.aoodax.jvm.common.rest.dto.response.error;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ValidationErrorResponseModel {

    private final Map<String, String> errors;

}
