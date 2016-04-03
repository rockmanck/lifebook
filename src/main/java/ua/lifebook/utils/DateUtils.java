package ua.lifebook.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static Date localDateTimeToDate(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static boolean isPast(Date date) {
        return date != null && date.before(new Date());
    }

    public static boolean isFuture(Date date) {
        return date != null && date.after(new Date());
    }

    public static boolean isFuture(LocalDateTime datetime) {
        return datetime != null && datetime.isAfter(LocalDateTime.now());
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        if (date == null) return null;
        return LocalDateTime.ofInstant(dateToInstant(date), ZoneId.systemDefault());
    }

    public static Instant dateToInstant(Date date) {
        return Instant.ofEpochMilli(date.getTime());
    }

    public static LocalDate dateToLocalDate(Date date) {
        if (date == null) return null;
        return dateToInstant(date).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalTime dateToLocalTime(Date date) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return LocalTime.of(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
    }

    public static long getMillisFromLocalDateTime(LocalDateTime value) {
        return localDateTimeToDate(value).getTime();
    }

    public static Date localDateToDate(LocalDate date) {
        if (date == null) return null;

        final Instant instant = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }
}
