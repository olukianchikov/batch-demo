package com.lukianchikov.lego.processor.batch.util;

import com.lukianchikov.lego.processor.batch.exception.DateTimeParsingException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DataTypeUtil {

    private DataTypeUtil() {
    }

    public static Integer getIntFromString(String input) {
        return Integer.valueOf(input);
    }

    /**
     * Converts a price value from String to {@link BigDecimal} format
     */
    public static BigDecimal parsePrice(String price) {
        return new BigDecimal(price);
    }

    /**
     * Converts provided datetime string to the number of seconds from the epoch of 1970-01-01T00:00:00Z. in a given
     * timezone.
     *
     * @param input    datetime string
     * @param formats  an array of date formats to try before failing with {@link DateTimeParseException}
     * @param zoneName nane of the timezone to save the result in.
     * @return number of seconds from the epoch time
     */
    public static Long getLongFromStringDateTime(String input, String[] formats, String zoneName) {
        return getLongFromLocalDateTime(getDateTimeFromString(input, formats), zoneName);
    }

    static LocalDateTime getDateTimeFromString(String input, String[] formats) {
        for (String format : formats) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            try {
                return LocalDateTime.parse(input, formatter);
            } catch (DateTimeParseException ignored) {
            }
        }
        throw new DateTimeParsingException(String.format("Input datetime [%s] could not be parsed as date time",
                input));
    }

    static Long getLongFromLocalDateTime(LocalDateTime datetime, String zoneName) {
        ZoneId zoneId = ZoneId.of(zoneName);
        return datetime.atZone(zoneId).toEpochSecond();
    }
}
