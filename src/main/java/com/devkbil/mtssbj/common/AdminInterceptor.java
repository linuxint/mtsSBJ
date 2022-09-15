package com.devkbil.mtssbj.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class AdminInterceptor implements HandlerInterceptor {
    static final Logger LOGGER = LoggerFactory.getLogger(AdminInterceptor.class);
    
    
    public List<String> adminEssential = Collections.singletonList("/ad**"); // Arrays.asList("/ad**");
    
    //public List adminInessential = Arrays.asList("/memberLogin", "/memberLoginChk", "/js/**", "/css/**", "/images/**");
    
    /**
     * 관리자 페이지는 관리자만 접근 허용.
     */
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) {
        HttpSession session = req.getSession();
        
        try {
            if(session == null || session.getAttribute("userno") == null) {
                res.sendRedirect("memberLogin");
                return false;
            }
            if(!"A".equals(session.getAttribute("userrole"))) {
                res.sendRedirect("noAuthMessage");
                return false;
            }
        } catch (IOException ex) {
            LOGGER.error("AdminInterceptor");
        }
        
        return true;
    }
    
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView mv) {
    }
    
    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception ex) {
    }
    
}
