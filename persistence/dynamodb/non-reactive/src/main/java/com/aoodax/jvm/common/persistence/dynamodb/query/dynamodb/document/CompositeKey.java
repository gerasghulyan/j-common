package com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.document;

import static com.aoodax.jvm.common.utils.validation.ParameterValidator.assertNotNullParameterArgument;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class CompositeKey implements Comparable<CompositeKey>, Serializable {

    private static final String DELIMITER = "#";

    private final String prefix;
    private final String suffix;

    public static CompositeKey from(final String prefix, final String suffix) {
        return new CompositeKey(prefix, suffix);
    }

    @Override
    public int compareTo(@NotNull final CompositeKey key) {
        assertNotNullParameterArgument(key, "key");
        final int cmp = this.prefix.compareTo(key.getPrefix());
        if (cmp != 0) {
            return cmp;
        }
        return this.suffix.compareTo(key.getSuffix());
    }

    @Override
    public String toString() {
        return prefix + DELIMITER + suffix;
    }

    public static class CompositeKeyConverter implements DynamoDBTypeConverter<String, CompositeKey> {

        @Override
        public String convert(final CompositeKey object) {
            return object.toString();
        }

        @Override
        public CompositeKey unconvert(final String str) {
            if (str.isBlank()) {
                return CompositeKey.from("", "");
            }
            final String[] parts = str.split(DELIMITER);
            return CompositeKey.from(parts[0], parts[1]);
        }
    }
}
