package com.devkbil.mtssbj.develop.logview;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;

/**
 * logback 설정에 위 custom appender를 추가
 */
/*
위의 경우 logback.xml에 FILE이란 이름의 appender가 있는 경우 해당 appender의 encoder를 사용하는 설정이다.
파일로 남기는 로그의 로그 패턴을 그대로 사용하기 위해 가져왔다.
이제 log 가 발생할 때마다 blueskyLogbackAppenderService에 queue가 쌓이게 된다.
쌓아놓은 queue를 확인하는 호출 주소를 추가한다.
소스참조 : https://luvstudy.tistory.com/65
 */
@Configuration
public class DevelopLogbackConfig implements InitializingBean {

    @Bean
    public DevelopLogbackAppenderService<ILoggingEvent> developLogbackAppenderService() {
        return new DevelopLogbackAppenderService<ILoggingEvent>();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LoggerContext loggerContext = (LoggerContext)LoggerFactory.getILoggerFactory();

        DevelopLogbackAppender<ILoggingEvent> developLogbackAppender = new DevelopLogbackAppender<>(developLogbackAppenderService());

        developLogbackAppender.setContext(loggerContext);
        developLogbackAppender.setName("developLogbackAppender");
        RollingFileAppender<ILoggingEvent> appender = (RollingFileAppender<ILoggingEvent>)loggerContext.getLogger("ROOT").getAppender("FILE");
        developLogbackAppender.setEncoder(appender == null ? new PatternLayoutEncoder() : appender.getEncoder());

        developLogbackAppender.start();
        loggerContext.getLogger("ROOT").addAppender(developLogbackAppender);

    }

}
