package com.devkbil.common.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Message Source Configuration
 */
@Configuration
public class MsgConfig {
    /**
     * MessageSource Define
     * @return
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("message/message");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}

