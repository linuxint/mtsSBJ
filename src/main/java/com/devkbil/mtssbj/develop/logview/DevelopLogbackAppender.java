package com.devkbil.mtssbj.develop.logview;

import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.encoder.Encoder;
import lombok.Setter;

/**
 * 웹 요청으로 응답을 처리하는 appender
 * @param <E>
 */
public class DevelopLogbackAppender<E> extends UnsynchronizedAppenderBase<E> {

    private final DevelopLogbackAppenderService<E> developLogbackAppenderService;

    @Setter
    private Encoder<E> encoder;

    public DevelopLogbackAppender(DevelopLogbackAppenderService<E> developLogbackAppenderService) {
        this.developLogbackAppenderService = developLogbackAppenderService;
    }

    @Override
    protected void append(E eventObject) {
        if (!isStarted()) {
            return;
        }
        developLogbackAppenderService.addLog(eventObject, new String(encoder.encode(eventObject)));
    }

}
