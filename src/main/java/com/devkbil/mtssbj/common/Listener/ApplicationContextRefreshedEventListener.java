package com.devkbil.mtssbj.common.Listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Date;

public class ApplicationContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    private final Logger logSystem = LoggerFactory.getLogger("SYSTEM");

    /**
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logSystem.debug("***********************************************************");
        logSystem.debug("*                                                         *");
        logSystem.debug("* ContextRefreshedEvent " + new Date(event.getTimestamp()));
        logSystem.debug("*                                                         *");
        logSystem.debug("***********************************************************");
    }
}
