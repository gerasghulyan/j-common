package com.aoodax.jvm.common.queue.message;

import static com.aoodax.jvm.common.utils.validation.ParameterValidator.assertHasTextParameterArgument;
import static com.aoodax.jvm.common.utils.validation.ParameterValidator.assertNotNullParameterArgument;
import static com.aoodax.jvm.common.utils.validation.ParameterValidator.assertPositiveParameterArgument;

import java.time.Instant;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class QueueEvent<T> implements Queueable {

    private final String type;

    @EqualsAndHashCode.Exclude
    private final long timestamp;

    protected final T data;

    public static <T> QueueEvent<T> of(final String type, final T data) {
        return new QueueEvent<>(type, Instant.now().toEpochMilli(), data);
    }

    private QueueEvent(final String type, final long timestamp, final T data) {
        assertHasTextParameterArgument(type, "type");
        assertPositiveParameterArgument(timestamp, "timestamp");
        assertNotNullParameterArgument(data, "data");
        this.type = type;
        this.timestamp = timestamp;
        this.data = data;
    }
}
