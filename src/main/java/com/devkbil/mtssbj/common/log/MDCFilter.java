package com.devkbil.mtssbj.common.log;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MDCFilter extends OncePerRequestFilter {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        try {

            String user = getUserPrincipal(request);
            System.out.println("MdcFilter save user=" + user);
            MDC.put("userName", user);

            final String trxId = UUID.randomUUID().toString().substring(0, 8);
            MDC.put(MDCKey.TRX_ID.getKey(), trxId);

            filterChain.doFilter(request, response);
        } finally {
            MDC.remove("userName");
        }
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

    private String getUserPrincipal(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userid = "";
        String usernm = "";
        try {
            userid = session.getAttribute("userid").toString();
            usernm = session.getAttribute("usernm").toString();
            userid = userid + "@" + usernm;
        } catch (NullPointerException e) {
            userid = "";
        }
        return userid;
        
    /*
        Principal principal = request.getUserPrincipal();
        if (principal == null)
            return null;
        String name = principal.getName();
        String[] parts = name.split("@");
        int iWord = Math.min(0, parts.length - 2);
        String userId = parts[iWord];
        return userId;
     */
    }

}
