package com.devkbil.mtssbj.develop.logview;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.CoreConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DevelopLogbackDevCheckController {

    @Autowired
    private DevelopLogbackAppenderService<ILoggingEvent> developLogbackAppenderService;

    @GetMapping(value = "/dvLogView", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> logview() {
        return developLogbackAppenderService
                .getLogQueue()
                .stream()
                .map(queue -> queue
                        .getLogMessage()
                        .replaceAll(CoreConstants.LINE_SEPARATOR, "")
                        .replaceAll("\t", ""))
                .collect(Collectors.toList());
    }

}
