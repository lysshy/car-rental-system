package com.also.car.rental.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateUtils {

    public static LocalDateTime toLocalDateTime(Date date) {
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(date.toInstant(), zoneId);
    }

    public static Date minusMillisecond(Date date, long value) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        localDateTime = localDateTime.minus(value, ChronoUnit.MILLIS);
        return LocalDateTimeUtils.toDate(localDateTime);
    }

    public static Date plusMillisecond(Date date, long value) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        localDateTime = localDateTime.plus(value, ChronoUnit.MILLIS);
        return LocalDateTimeUtils.toDate(localDateTime);
    }

}
