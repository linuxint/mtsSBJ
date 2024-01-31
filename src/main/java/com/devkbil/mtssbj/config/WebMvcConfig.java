package com.devkbil.mtssbj.config;

import com.devkbil.mtssbj.common.interceptor.DeviceDetectorInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.CacheControl;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.devkbil.mtssbj.common.interceptor.AdminInterceptor;
import com.devkbil.mtssbj.common.interceptor.CommonInterceptor;
import com.devkbil.mtssbj.common.interceptor.LoginInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final String REQUEST_URL = "/index";
    private static final String REDIRECT_URL = "/memberLogin";
    private static final String ERROR_URL = "/error/**";
    private static final String ERROR_PAGE_CLASSPATH = "classpath:templates/error/";

    @Value("${server.indexPage}")
    private final String indexPage = "index.jsp";

    @Profile("Prod")
    @Configuration
    public static class ProdMvcConfiguration {
    }

    @Profile("local")
    @Configuration
    public static class LocalMvcConfiguration {
    }

    @Profile("dev")
    @Configuration
    public static class DevMvcConfiguration {
    }

    @Profile("stag")
    @Configuration
    public static class StagMvcConfiguration {
    }

    /**
     * Interceptor 정의 - admin,login
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        AdminInterceptor adminInterceptor = new AdminInterceptor();
        registry.addInterceptor(adminInterceptor).order(3) // 우선순위 3
                .addPathPatterns(adminInterceptor.adminEssential); // 사용될 URL
        //.excludePathPatterns(adminInterceptor.adminInessential); // 제외될 URL

        LoginInterceptor loginIntercepter = new LoginInterceptor();
        registry.addInterceptor(loginIntercepter).order(2) // 우선순위 2
                .addPathPatterns(loginIntercepter.loginEssential)  // 사용될 URL
                .excludePathPatterns(loginIntercepter.loginInessential);  // 제외될 URL

        CommonInterceptor commonInterceptor = new CommonInterceptor();
        registry.addInterceptor(commonInterceptor).order(1); // 우선순위 1

        DeviceDetectorInterceptor deviceDetectorInterceptor = new DeviceDetectorInterceptor();
        registry.addInterceptor(deviceDetectorInterceptor).order(1);

    }
    
    /*
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        
        //전체 resource를 허용할 경우 registry.addMapping("/**")...
        registry.addMapping("/**")   //mapping할 resource를 지정합니다.
                .allowedOrigins("http://localhost:18080");  //CORS를 허용할 origin을 지정합니다.
    }
     */

    /**
     * resourceHandlers정의 (/js, /css)
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("resources/js/**")    //지정된 path pattern에 대한 handler를 추가합니다. ex) "/m/**"
                .addResourceLocations("classpath:/static/js/")  //반드시 /로 끝나야 정상적으로 동작함에 주의합니다. ex) "classpath:/m/"
                .setCacheControl(CacheControl.noCache().cachePrivate());
        registry.addResourceHandler("resources/images/**")
                .addResourceLocations("classpath:/static/images/")
                .setCachePeriod(20);    //caching period를 20초로 지정합니다. ex) 60 * 60 * 24 * 365
        registry.addResourceHandler("resources/css/**")
                .addResourceLocations("classpath:/static/css/")
                .setCacheControl(CacheControl.noCache().cachePrivate());
        registry.addResourceHandler(ERROR_URL)
                .addResourceLocations(ERROR_PAGE_CLASSPATH);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController(REQUEST_URL, REDIRECT_URL);
        if (!"".equals(indexPage)) {
            registry.addViewController("/").setViewName("forward:/" + indexPage);
        }
    }

    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        DefaultMessageCodesResolver codesResolver = new DefaultMessageCodesResolver();
        codesResolver.setMessageCodeFormatter(DefaultMessageCodesResolver.Format.POSTFIX_ERROR_CODE);
        return codesResolver;
    }

}
