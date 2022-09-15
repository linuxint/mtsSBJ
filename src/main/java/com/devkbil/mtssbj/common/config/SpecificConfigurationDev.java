package com.devkbil.mtssbj.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Dev Cache Configuration
 */
@Profile("dev")
@Configuration
public class SpecificConfigurationDev {
    /**
     * Dev Cache Configuration
     *
     * @return
     */
    @Bean
    public String cache() {
        return "Dev Cache Configuration";
    }
}
