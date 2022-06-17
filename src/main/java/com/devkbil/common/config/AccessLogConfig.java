package com.devkbil.common.config;

import org.apache.catalina.valves.AccessLogValve;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

/**
 * AccessLog Configuration
 */
@Configuration
public class AccessLogConfig implements WebServerFactoryCustomizer {
    /**
     * Log custimze
     *
     * @param factory the web server factory to customize
     */
    @Override
    public void customize(final WebServerFactory factory) {
        final TomcatServletWebServerFactory containerFactory = (TomcatServletWebServerFactory) factory;
        
        final AccessLogValve accessLogValve = new AccessLogValve();
        //accessLogValve.setDirectory();
        //accessLogValve.setPattern("%{yyyy-MM-dd HH:mm:ss}t\t%s\t%r\t%{User-Agent}i\t%{Referer}i\t%a\t%b");
        //accessLogValve.setDirectory(".");
        //accessLogValve.setSuffix(".log");
        accessLogValve.setCondition("ignoreLogging");
        containerFactory.addContextValves(accessLogValve);
    }
}