package com.aoodax.jvm.common.utils.validation;

import java.math.BigDecimal;
import java.util.Collection;

import static java.lang.String.format;

public final class ParameterValidator {

    private ParameterValidator() {
        throw new UnsupportedOperationException("The instance of this class shouldn't be instantiated.");
    }

    public static void assertPositiveParameterArgument(byte arg, String parameterName) {
        assertPositiveParameterArgument((double) arg, parameterName);
    }

    public static void assertPositiveParameterArgument(short arg, String parameterName) {
        assertPositiveParameterArgument((double) arg, parameterName);
    }

    public static void assertPositiveParameterArgument(int arg, String parameterName) {
        assertPositiveParameterArgument((double) arg, parameterName);
    }

    public static void assertPositiveParameterArgument(long arg, String parameterName) {
        assertPositiveParameterArgument((double) arg, parameterName);
    }

    public static void assertPositiveParameterArgument(float arg, String parameterName) {
        assertPositiveParameterArgument((double) arg, parameterName);
    }

    public static void assertPositiveParameterArgument(double arg, String parameterName) {
        if (arg <= 0) {
            throwParameterArgumentException(parameterName, "Non positive number was passed");
        }
    }

    public static void assertPositiveParameterArgument(BigDecimal arg, String parameterName) {
        if (arg.compareTo(BigDecimal.ZERO) <= 0) {
            throwParameterArgumentException(parameterName, "Non positive number was passed");
        }
    }

    public static void assertNonNegativeParameterArgument(byte arg, String parameterName) {
        assertNonNegativeParameterArgument((double) arg, parameterName);
    }

    public static void assertNonNegativeParameterArgument(short arg, String parameterName) {
        assertNonNegativeParameterArgument((double) arg, parameterName);
    }

    public static void assertNonNegativeParameterArgument(int arg, String parameterName) {
        assertNonNegativeParameterArgument((double) arg, parameterName);
    }

    public static void assertNonNegativeParameterArgument(long arg, String parameterName) {
        assertNonNegativeParameterArgument((double) arg, parameterName);
    }

    public static void assertNonNegativeParameterArgument(float arg, String parameterName) {
        assertNonNegativeParameterArgument((double) arg, parameterName);
    }

    public static void assertNonNegativeParameterArgument(double arg, String parameterName) {
        if (arg < 0) {
            throwParameterArgumentException(parameterName, "Negative number was passed");
        }
    }

    public static <T> void assertNotNullParameterArgument(T arg, String parameterName) {
        if (arg == null) {
            throw new IllegalArgumentException(format("Null was passed as an argument for parameter '%s'", parameterName));
        }
    }

    public static void assertHasTextParameterArgument(String arg, String parameterName) {
        if (arg == null || arg.isBlank()) {
            throw new IllegalArgumentException(format("Empty string was passed as an argument for parameter '%s'", parameterName));
        }
    }

    public static void assertValidConditionForParameterArgument(boolean condition, String parameterName) {
        if (!condition) {
            throw new IllegalArgumentException(format("Condition was not met for parameter '%s'", parameterName));
        }
    }

    public static void assertNotEmptyCollectionParameterArgument(Collection<?> list, String parameterName) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException(format("Empty collection was passed as an argument for parameter '%s'", parameterName));
        }
    }

    private static void throwParameterArgumentException(String parameterName, String message) {
        throw new IllegalArgumentException(format("%s for parameter '%s'", message, parameterName));
    }
}