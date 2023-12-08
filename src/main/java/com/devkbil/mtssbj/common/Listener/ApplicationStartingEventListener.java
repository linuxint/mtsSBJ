package com.devkbil.mtssbj.common.Listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

import java.util.Date;

@Slf4j
public class ApplicationStartingEventListener implements ApplicationListener<ApplicationStartingEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        System.out.println("***********************************************************");
        System.out.println("*                                                         *");
        System.out.println("* ApplicationStartingEvent " + new Date(event.getTimestamp()));
        System.out.println("*                                                         *");
        System.out.println("***********************************************************");
    }
}
