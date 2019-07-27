package ua.lifebook.web.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class StringToLocalDateConverter implements Converter<String, LocalDate> {

    private static final ThreadLocal<DateTimeFormatter> DATE_TIME_FORMATTER = ThreadLocal.withInitial(() -> DateTimeFormatter.ofPattern("MM/dd/yyyy"));

    @Override
    public LocalDate convert(String source) {
        return LocalDate.parse(source, DATE_TIME_FORMATTER.get());
    }
}
