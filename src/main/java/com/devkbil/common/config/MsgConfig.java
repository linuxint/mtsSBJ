package com.devkbil.common.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;

/**
 * Message Source Configuration
 */
@Configuration
public class MsgConfig {
    
    private static final String MSG_BASE_PATH = "message/message";
    private static final String MSG_ENCODE = StandardCharsets.UTF_8.name();
    private static final int MSG_RELOAD_SECOND = 60;
    
    
    /**
     * MessageSource Define
     *
     * @return
     */
    @Bean
    public MessageSource messageSource() {

        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(MSG_BASE_PATH);
        messageSource.setDefaultEncoding(MSG_ENCODE);
        messageSource.setCacheSeconds(MSG_RELOAD_SECOND);

        return messageSource;
    }
    
    @Bean
    public MessageSourceAccessor messageSourceAccessor(MessageSource messageSource) {
        return new MessageSourceAccessor(messageSource);
    }
}

