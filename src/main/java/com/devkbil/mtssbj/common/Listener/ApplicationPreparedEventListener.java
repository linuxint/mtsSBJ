package com.devkbil.mtssbj.common.Listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;

import java.util.Date;

public class ApplicationPreparedEventListener  implements ApplicationListener<ApplicationPreparedEvent> {

    private final Logger logSystem = LoggerFactory.getLogger("SYSTEM");

    /**
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ApplicationPreparedEvent event) {
        logSystem.debug("***********************************************************");
        logSystem.debug("*                                                         *");
        logSystem.debug("* ApplicationPreparedEvent " + new Date(event.getTimestamp()));
        logSystem.debug("*                                                         *");
        logSystem.debug("***********************************************************");
    }
}
