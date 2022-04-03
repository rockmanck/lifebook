package pp.ua.lifebook.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private DateUtils() {
        // hide public constructor
    }

    private static final ThreadLocal<DateTimeFormatter> dateTimeFormatter = ThreadLocal.withInitial(() -> DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    private static final ThreadLocal<DateTimeFormatter> localDateFormatter = ThreadLocal.withInitial(() -> DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    private static final ThreadLocal<SimpleDateFormat> dateFormatter = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));
    private static final ThreadLocal<DateTimeFormatter> shortDateFormatter = ThreadLocal.withInitial(() -> DateTimeFormatter.ofPattern("MMM-dd"));

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
        if (value == null) return -1;

        return localDateTimeToDate(value).getTime();
    }

    public static Date localDateToDate(LocalDate date) {
        if (date == null) return null;

        final Instant instant = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static String format(LocalDateTime dateTime) {
        return dateTimeFormatter.get().format(dateTime);
    }

    public static String formatShortDate(LocalDate date) {
        if (date == null) return "-";

        return shortDateFormatter.get().format(date);
    }

    public static String format(Date date) {
        return dateFormatter.get().format(date);
    }

    public static String format(LocalDate date) {
        return localDateFormatter.get().format(date);
    }

    private static Instant dateToInstant(Date date) {
        return Instant.ofEpochMilli(date.getTime());
    }
}
