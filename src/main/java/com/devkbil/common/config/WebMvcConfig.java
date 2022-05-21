package com.devkbil.common.config;

import com.beust.ah.A;
import com.devkbil.common.AdminInterceptor;
import com.devkbil.common.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        AdminInterceptor adminInterceptor = new AdminInterceptor();
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns(adminInterceptor.adminEssential);
                //.excludePathPatterns(adminInterceptor.adminInessential);

        LoginInterceptor loginIntercepter = new LoginInterceptor();
        registry.addInterceptor(loginIntercepter)
                .addPathPatterns(loginIntercepter.loginEssential)
                .excludePathPatterns(loginIntercepter.loginInessential);
    }
}