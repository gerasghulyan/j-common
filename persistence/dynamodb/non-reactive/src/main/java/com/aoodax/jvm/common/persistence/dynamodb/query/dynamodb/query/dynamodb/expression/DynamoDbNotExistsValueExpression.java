package com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.dynamodb.expression;

import static com.aoodax.jvm.common.utils.validation.ParameterValidator.assertHasTextParameterArgument;

import com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.query.expression.ValueExpression;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public final class DynamoDbNotExistsValueExpression implements ValueExpression<String, Object> {

    private final String key;

    public static DynamoDbNotExistsValueExpression of(final String key) {
        assertHasTextParameterArgument(key, "key");
        return new DynamoDbNotExistsValueExpression(key);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Optional<Object> getValue() {
        return Optional.empty();
    }
}
