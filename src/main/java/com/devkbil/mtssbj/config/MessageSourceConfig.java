package com.devkbil.mtssbj.config;

import java.time.Duration;

import org.springframework.boot.autoconfigure.context.MessageSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.devkbil.mtssbj.common.ExtendReloadableResourceBundleMessageSource;

import lombok.extern.slf4j.Slf4j;

/**
 * Message Source Configuration
 */
@Configuration
@EnableConfigurationProperties
@Slf4j
public class MessageSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.messages")
    public MessageSourceProperties properties() {
        return new MessageSourceProperties();
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setLocaleAttributeName("current.locale");
        slr.setTimeZoneAttributeName("current.timezone");
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("language");
        return localeChangeInterceptor;
    }

    @Bean
    public MessageSource messageSource(MessageSourceProperties properties) {

        ExtendReloadableResourceBundleMessageSource messageSource = new ExtendReloadableResourceBundleMessageSource();

        // comma Delimited To List
        if (StringUtils.hasText(properties.getBasename())) {
            String baseName = properties.getBasename();
            baseName = StringUtils.trimAllWhitespace(baseName);
            String[] baseNames = StringUtils.commaDelimitedListToStringArray(baseName);
            messageSource.setBasenames(baseNames);
        }

        if (properties.getEncoding() != null) {
            messageSource.setDefaultEncoding(properties.getEncoding().name());
        }
        messageSource.setFallbackToSystemLocale(properties.isFallbackToSystemLocale());
        Duration cacheDuration = properties.getCacheDuration();
        if (cacheDuration != null) {
            messageSource.setCacheMillis(cacheDuration.toMillis());
        }
        messageSource.setAlwaysUseMessageFormat(properties.isAlwaysUseMessageFormat());
        messageSource.setUseCodeAsDefaultMessage(properties.isUseCodeAsDefaultMessage());

        return messageSource;
    }
}

