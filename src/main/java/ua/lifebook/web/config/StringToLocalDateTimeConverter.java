package ua.lifebook.web.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    private static final ThreadLocal<DateTimeFormatter> DATE_TIME_FORMATTER = ThreadLocal.withInitial(() -> DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"));

    @Override
    public LocalDateTime convert(String source) {
        return LocalDateTime.parse(source, DATE_TIME_FORMATTER.get());
    }
}
