package com.devkbil.mtssbj.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Prod Cache Configuration
 */
@Profile("prod")
@Configuration
public class SpecificConfigurationProd {
    /**
     * Prod Cache Configuration
     *
     * @return
     */
    @Bean
    public String cache() {
        return "Prod Cache Configuration";
    }
}
