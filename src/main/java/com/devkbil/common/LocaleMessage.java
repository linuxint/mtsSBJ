package com.devkbil.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class LocaleMessage {
    
    @Autowired
    MessageSource messageSource;

    public String getMessage(String key) {
        return messageSource.getMessage(key,null, Locale.KOREA);
    }
    
    public String getMessage(String key, Object[] objs) {
        return messageSource.getMessage(key,objs, Locale.getDefault());
    
    }
}