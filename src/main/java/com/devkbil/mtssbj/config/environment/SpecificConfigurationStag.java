package com.devkbil.mtssbj.config.environment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

/**
 * Stag Cache Configuration
 */
@Profile("stag")
@Configuration
@RequiredArgsConstructor
@Slf4j
public class SpecificConfigurationStag implements EnvConfiguration {


    private final Environment environment;

    @Override
    @Bean
    public String getMessage() {
        log.info("[" + environment.getProperty("spring.config.activate.on-profile") + "] environment is running");
        return environment.getProperty("spring.config.activate.on-profile");
    }

    /**
     * Stag Cache Configuration
     *
     * @return
     */
    @Override
    @Bean
    public String cache() {
        return "Stag Cache Configuration";
    }
}
