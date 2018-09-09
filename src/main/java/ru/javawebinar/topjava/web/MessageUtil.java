package ru.javawebinar.topjava.web;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * MessageUtil.
 *
 * @author Stanislav (376825@gmail.com)
 * @since 09.09.2018
 */
@Component
public class MessageUtil {
    private final MessageSource messageSource;

    @Autowired
    public MessageUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String code) {
        return getMessage(code, LocaleContextHolder.getLocale());
    }

    public String getMessage(String code, Locale locale) {
        return messageSource.getMessage(code, null, locale);
    }
}