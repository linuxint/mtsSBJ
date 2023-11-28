package com.devkbil.mtssbj.common.interceptor;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.devkbil.mtssbj.common.log.MDCFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    public List loginEssential = Arrays.asList("/**");

    public List loginInessential = Arrays.asList("/memberLogin", "/memberLoginChk", "/js/**", "/css/**", "/images/**");

    @Bean
    public FilterRegistrationBean mdcFilterRegisterationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        MDCFilter mdcFilter = new MDCFilter();
        registrationBean.setFilter(mdcFilter);
        registrationBean.setOrder(10);
        return registrationBean;
    }

    /**
     * Controller 실행 요청전.
     * 일반 사용자의 로그인 체크.
     */
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) {
        HttpSession session = req.getSession();
        String userid = (String)req.getSession().getAttribute("userno");
        /**
         session.setAttribute("userid", "admin");
         session.setAttribute("userrole", "A");
         session.setAttribute("userno",   "1");
         session.setAttribute("usernm", "관리자");
         */

        try {
            if (session == null || session.getAttribute("userno") == null) {
                res.sendRedirect("memberLogin");
                return false;
            }
        } catch (IOException ex) {
            log.error("LoginInterceptor");
        }

        return true;
    }

    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView mv) {
    }

    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception ex) {
    }

}
