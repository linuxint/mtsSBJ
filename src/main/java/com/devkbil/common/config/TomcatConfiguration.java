package com.devkbil.common.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.ajp.AbstractAjpProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfiguration {

    @Value("${tomcat.ajp.protocaol}")
    String ajpProtocol;
    @Value("${tomcat.ajp.port}")
    int ajpPort;
    @Value("${tomcat.ajp.enable}")
    boolean ajpEnabled;
    @Value("${tomcat.ajp.schema}")
    String ajpSchema;
    @Value("${tomcat.ajp.secure}")
    boolean ajpSecure;
    @Value("${tomcat.ajp.allowtrace}")
    boolean ajpAllowTrace;
    @Value("${tomcat.ajp.secretrequired}")
    boolean ajpSecretRequired;

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createAjpConnector());
        return tomcat;
    }

    private Connector createAjpConnector() {
        Connector ajpConnector = new Connector(ajpProtocol);
        ajpConnector.setPort(ajpPort);
        ajpConnector.setSecure(ajpSecure);
        ajpConnector.setAllowTrace(ajpAllowTrace);
        ajpConnector.setScheme(ajpSchema);
        ((AbstractAjpProtocol) ajpConnector.getProtocolHandler()).setSecretRequired(ajpSecretRequired); // 해당 줄을 추가함
        return ajpConnector;
    }
}