package com.devkbil.common.config;

import com.beust.ah.A;
import com.devkbil.common.AdminInterceptor;
import com.devkbil.common.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
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
    
    /*
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        
        //전체 resource를 허용할 경우 registry.addMapping("/**")...
        registry.addMapping("/**")   //mapping할 resource를 지정합니다.
                .allowedOrigins("http://localhost:18080");  //CORS를 허용할 origin을 지정합니다.
    }
     */
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("resources/**")    //지정된 path pattern에 대한 handler를 추가합니다. ex) "/m/**"
                .addResourceLocations("classpath:/static/js")  //반드시 /로 끝나야 정상적으로 동작함에 주의합니다. ex) "classpath:/m/"
                .setCacheControl(CacheControl.noCache().cachePrivate());
        registry.addResourceHandler("resources/css/**")
                .addResourceLocations("classpath:/static/css/")
                .setCachePeriod(20);    //caching period를 20초로 지정합니다. ex) 60 * 60 * 24 * 365
    }
    
}