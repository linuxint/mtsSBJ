package com.devkbil.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("prod")
@Configuration
public class SpecificConfigurationProd {
    @Bean
    public String cache() {
        return "Prod Cache Configuration";
    }
}
