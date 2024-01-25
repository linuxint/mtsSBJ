package com.devkbil.mtssbj.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class DeviceDetectorInterceptor implements HandlerInterceptor {

    public static final String CURRENT_DEVICE_ATTRIBUTE = "currentDevice";
    public static final String DEVICE_MOBILE = "MOBILE";
    public static final String DEVICE_TABLET = "TABLET";
    public static final String DEVICE_NORMAL = "NORMAL";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String userAgent = request.getHeader("User-Agent");
        String device = DEVICE_NORMAL;

        if(StringUtils.hasText(userAgent)) {
            userAgent = userAgent.toLowerCase();
            if(userAgent.contains("android")) {
                device = userAgent.contains("mobile") ? DEVICE_NORMAL : DEVICE_TABLET;
            } else if(userAgent.contains("ipad") || userAgent.contains("playbook") ||
                    userAgent.contains("kindle")) {
                device = DEVICE_TABLET;
            } else if (userAgent.contains("mobil") || userAgent.contains("ipod") ||
                    userAgent.contains("nintendo DS")
            ) {
                device = DEVICE_MOBILE;
            }
        }
        request.setAttribute(CURRENT_DEVICE_ATTRIBUTE, device);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
