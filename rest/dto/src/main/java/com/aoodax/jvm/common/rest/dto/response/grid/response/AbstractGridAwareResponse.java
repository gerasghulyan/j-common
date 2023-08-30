package com.aoodax.jvm.common.rest.dto.response.grid.response;

import com.aoodax.jvm.common.rest.dto.response.ResponseDto;
import com.aoodax.jvm.common.rest.dto.response.ResponseModel;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static com.aoodax.jvm.common.utils.validation.ParameterValidator.assertNotNullParameterArgument;

@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public abstract class AbstractGridAwareResponse<T extends ResponseDto> implements ResponseModel {

    List<T> items;
    long total;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    protected AbstractGridAwareResponse(final List<T> items,
                                        final long total) {
        assertNotNullParameterArgument(items, "items");
        assertNotNullParameterArgument(total, "total");

        this.items = items;
        this.total = total;
    }
}