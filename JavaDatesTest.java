package com.uv.date;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class JavaDatesTest {
    private JavaDates javaDates = new JavaDates();

    private static Stream<Arguments> stringIntAndListProvider() {
        return Stream.of(
                arguments("1996-12-19T16:39:57-08:00",      "1996-12-20 00:39:57 000 Z"),
                arguments("1990-12-31T23:59:60Z",           "1991-01-01 00:00:00 000 Z"),
                arguments("1990-12-31T15:59:60-08:00",      "1991-01-01 00:00:00 000 Z"),
                arguments("1937-01-01T12:00:27.87+00:20",   "1937-01-01 11:40:27 870 Z")
        );
    }

    @ParameterizedTest
    @MethodSource("stringIntAndListProvider")
    void assertDateConversion(String rfc3339Str, String internalStr) {
            assertEquals(internalStr, javaDates.getInternalDateStrFromRFC3339Str(rfc3339Str), "Date conversion failed");
    }
}
