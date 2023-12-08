package com.devkbil.mtssbj.common.Listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import java.util.Date;

public class ApplicationContextClosedEventListener  implements ApplicationListener<ContextClosedEvent> {

    private final Logger logSystem = LoggerFactory.getLogger("SYSTEM");

    /**
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        logSystem.debug("***********************************************************");
        logSystem.debug("*                                                         *");
        logSystem.debug("* ContextClosedEvent " + new Date(event.getTimestamp()));
        logSystem.debug("*                                                         *");
        logSystem.debug("***********************************************************");
    }
}
