package com.aoodax.jvm.common.rest.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode
@ToString
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PageableRequest implements RequestModel {
    
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_SIZE = 10;

    @Min(1)
    @NotNull
    int page;
    
    @Min(1)
    @Max(1000)
    @NotNull
    int size;

    public PageableRequest() {
        this.page = DEFAULT_PAGE;
        this.size = DEFAULT_SIZE;
    }

    @Builder
    protected PageableRequest(final int page,
                              final int size) {
        if (page < 0) {
            this.page = DEFAULT_PAGE;
        } else {
            this.page = page;
        }
        if (size <= 0 || size >= 1000) {
            this.size = DEFAULT_SIZE;
        } else {
            this.size = size;
        }
    }
}