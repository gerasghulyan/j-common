package com.aoodax.jvm.common.persistence.dynamodb.query.dynamodb.converter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class LocalDateTimeStringConverter implements DynamoDBTypeConverter<String, LocalDateTime> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public String convert(final LocalDateTime localDateTime) {
        return localDateTime.format(DATE_TIME_FORMATTER);
    }

    @Override
    public LocalDateTime unconvert(final String stringValue) {
        return LocalDateTime.parse(stringValue, DATE_TIME_FORMATTER);
    }
}
