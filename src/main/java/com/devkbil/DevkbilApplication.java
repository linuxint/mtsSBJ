package com.devkbil;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@SpringBootApplication
@MapperScan(basePackages = "com.devkbil.*")
public class DevkbilApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevkbilApplication.class, args);
    }
    
    /*
     * 사용자 언어 환경을 설정해주기 위한 bean 설정
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.KOREA);      // <---- 해당 값을 수정하여 언어 결정
        return sessionLocaleResolver;
    }
}
