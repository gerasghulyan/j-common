package com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression;

import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.expression.ValueExpression;
import io.vavr.Tuple2;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static com.aoodax.jvm.common.utils.validation.ParameterValidator.assertHasTextParameterArgument;
import static com.aoodax.jvm.common.utils.validation.ParameterValidator.assertNotNullParameterArgument;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public final class DynamoDbBetweenValueExpression implements ValueExpression<Tuple2<String, String>, Tuple2<String, String>> {

    private static final String KEY_PREFIX = ":";
    private static final String DELIMITER = "_";

    private final String key;
    private final String fromValue;
    private final String toValue;

    public static DynamoDbBetweenValueExpression of(final String key, final String from, final String to) {
        assertHasTextParameterArgument(key, "key");
        assertNotNullParameterArgument(from, "from");
        assertNotNullParameterArgument(to, "to");

        return new DynamoDbBetweenValueExpression(key, from, to);
    }

    public static DynamoDbBetweenValueExpression of(final String from, final String to) {
        return of("key", from, to);
    }

    @Override
    public Tuple2<String, String> getKey() {
        final String encodedFrom = Hex.encodeHexString(fromValue.getBytes(StandardCharsets.UTF_8));
        final String encodedTo = Hex.encodeHexString(toValue.getBytes(StandardCharsets.UTF_8));

        final String keyForFrom = KEY_PREFIX + key + DELIMITER + encodedFrom;
        final String keyForTo = KEY_PREFIX + key + DELIMITER + encodedTo;

        return new Tuple2<>(keyForFrom, keyForTo);
    }

    @Override
    public Optional<Tuple2<String, String>> getValue() {
        return Optional.of(new Tuple2<>(fromValue, toValue));
    }
}
