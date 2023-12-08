package com.devkbil.mtssbj.common.Listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.util.Date;

public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

    private final Logger logSystem = LoggerFactory.getLogger("SYSTEM");

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logSystem.debug("***********************************************************");
        logSystem.debug("*                                                         *");
        logSystem.debug("* ApplicationReadyEvent " + new Date(event.getTimestamp()));
        logSystem.debug("*                                                         *");
        logSystem.debug("***********************************************************");
    }
}
