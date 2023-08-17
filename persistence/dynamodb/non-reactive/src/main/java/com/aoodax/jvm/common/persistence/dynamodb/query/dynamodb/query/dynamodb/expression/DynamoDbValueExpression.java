package com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression;

import static com.aoodax.jvm.common.utils.validation.ParameterValidator.assertHasTextParameterArgument;
import static com.aoodax.jvm.common.utils.validation.ParameterValidator.assertNotNullParameterArgument;

import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.expression.ValueExpression;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.apache.commons.codec.binary.Hex;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public final class DynamoDbValueExpression implements ValueExpression<String, Object> {

    private static final String KEY_PREFIX = ":";
    private static final String DELIMITER = "_";

    private final String key;
    private final Object value;

    public static DynamoDbValueExpression of(final String key, final String value) {
        assertHasTextParameterArgument(key, "key");
        assertNotNullParameterArgument(value, "value");

        return new DynamoDbValueExpression(key, value);
    }

    public static DynamoDbValueExpression of(final String value) {
        return of("key", value);
    }

    @Override
    public String getKey() {
        final String encodedValue = Hex.encodeHexString(value.toString().getBytes(StandardCharsets.UTF_8));
        return KEY_PREFIX + key + DELIMITER + encodedValue;
    }

    @Override
    public Optional<Object> getValue() {
        return Optional.of(value);
    }
}
