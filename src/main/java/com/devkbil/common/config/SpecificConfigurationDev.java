package com.devkbil.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@Configuration
public class SpecificConfigurationDev {
    @Bean
    public String cache() {
        return "Dev Cache Configuration";
    }
}
