package com.devkbil.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("local")
@Configuration
public class SpecificConfigurationLocal {
    @Bean
    public String cache() {
        return "Local Cache Configuration";
    }
}
