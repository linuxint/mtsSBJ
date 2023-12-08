package com.devkbil.mtssbj.common.Listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;

import java.util.Date;

public class ApplicationFailedEventListener implements ApplicationListener<ApplicationFailedEvent> {

    private final Logger logSystem = LoggerFactory.getLogger("SYSTEM");

    /**
     * 시작시 실패할 경우에 생기는 이벤트
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ApplicationFailedEvent event) {
        logSystem.debug("***********************************************************");
        logSystem.debug("*                                                         *");
        logSystem.debug("* ApplicationFailedEvent " + new Date(event.getTimestamp()));
        logSystem.debug("*                                                         *");
        logSystem.debug("***********************************************************");
    }
}
