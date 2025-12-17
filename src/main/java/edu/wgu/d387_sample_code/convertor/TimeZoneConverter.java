package edu.wgu.d387_sample_code.convertor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Project: d387-advanced-java
 * Package: edu.wgu.d387_sample_code.convertor
 * <p>
 * User: AnDrew
 * Date: 2/25/2025
 * Time: 12:00 AM
 */
public class TimeZoneConverter {
    public static String convertTime(String time, ZoneId fromZone, ZoneId toZone) {
        LocalDateTime localDateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        ZonedDateTime fromZonedDateTime = ZonedDateTime.of(localDateTime, fromZone);
        ZonedDateTime toZonedDateTime = fromZonedDateTime.withZoneSameInstant(toZone);
        return toZonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
