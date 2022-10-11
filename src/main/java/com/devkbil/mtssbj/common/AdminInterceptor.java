package com.devkbil.mtssbj.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class AdminInterceptor implements HandlerInterceptor {
    
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
            log.error("AdminInterceptor");
        }
        
        return true;
    }
    
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView mv) {
    }
    
    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception ex) {
    }
    
}
