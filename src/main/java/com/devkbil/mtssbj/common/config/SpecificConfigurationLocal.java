package com.devkbil.mtssbj.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Local Cache Configuration
 */
@Profile("local")
@Configuration
public class SpecificConfigurationLocal {
    /**
     * Local Cache Configuration
     *
     * @return
     */
    @Bean
    public String cache() {
        return "Local Cache Configuration";
    }
}
