package ru.javawebinar.topjava.web.converter;

import ru.javawebinar.topjava.util.DateTimeUtil;
import java.util.Locale;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.text.ParseException;
import org.springframework.format.Formatter;

/**
 * DateTimeFormatters.
 *
 * @author Stanislav (376825@gmail.com)
 * @since 20.08.2018
 */
public class DateTimeFormatters {
    public static class LocalDateFormatter implements Formatter<LocalDate> {
        @Override
        public LocalDate parse(String text, Locale locale) throws ParseException {
            return DateTimeUtil.parseLocalDate(text);
        }

        @Override
        public String print(LocalDate object, Locale locale) {
            return object.format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }

    public static class LocalTimeFormatter implements Formatter<LocalTime> {
        @Override
        public LocalTime parse(String text, Locale locale) throws ParseException {
            return DateTimeUtil.parseLocalTime(text);
        }

        @Override
        public String print(LocalTime object, Locale locale) {
            return object.format(DateTimeFormatter.ISO_LOCAL_TIME);
        }
    }
}