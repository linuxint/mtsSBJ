package com.devkbil.mtssbj.config.security;

import javax.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {

    @Autowired
    DataSource dataSource;

    @Autowired
    private AuthenticationManagerBuilder auth;

    @Autowired
    private CustomSessionExpiredStrategy customSessionExpiredStrategy;

    @Autowired
    private UserDetailsService userDetailsService;
    private final AuthenticationFailureHandler userLoginFailHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(session -> session
                .sessionFixation().changeSessionId()
                .maximumSessions(1)
                .expiredSessionStrategy(customSessionExpiredStrategy)
                .maxSessionsPreventsLogin(false)
                .sessionRegistry(sessionRegistry())
        );
        http.csrf(c -> c.disable()).cors(c->c.disable())
                .authorizeHttpRequests(request -> request
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .antMatchers(
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/application/**")
                        //antMatcher("/view/join"),
                        //antMatcher("/auth/join")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/memberLogin")
                        .loginProcessingUrl("/login-process")
                        .usernameParameter("userid")
                        .passwordParameter("userpw")
                        .defaultSuccessUrl("/memberLoginChk", true)
                        .failureHandler(userLoginFailHandler)
                        //.failureUrl("/memberLoginError")
                        .permitAll()
                        // successHandler -> LoginController에 구현
                        /*
                        .successHandler(((request, response, authentication) -> {
                            HttpSessionRequestCache requestCache = new HttpSessionRequestCache();

                            // 세션에서 이미 저장되어 있는 이전 요청 정보를 추출!
                            SavedRequest savedRequest = requestCache.getRequest(request, response);
                            String redirectUrl = savedRequest.getRedirectUrl();

                            // 그 이전 요청 위치로 이동!
                            response.sendRedirect(redirectUrl);
                        }))
                         */
                )
                .rememberMe(rememberMe->rememberMe
                        .key("remember-me-key")
                        .rememberMeCookieName("mtsSBJ3-remember-me")
                        .userDetailsService(userDetailsService)
                        .tokenRepository(tokenRepository())
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/memberLogout"))
                        .invalidateHttpSession(true)
                        .deleteCookies("sid","JSESSIONID","mtsSBJ3-remember-me")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
