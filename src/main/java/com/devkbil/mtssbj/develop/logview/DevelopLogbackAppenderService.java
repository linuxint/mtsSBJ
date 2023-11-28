package com.devkbil.mtssbj.develop.logview;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * 로컬 queue에 로그 내용을 담는 기능을 제공
 * @param <E>
 */
public class DevelopLogbackAppenderService<E> {

    private static final int queueSize = 500;

    @Getter
    private final Queue<LogObject<E>> logQueue = new LinkedBlockingQueue<>(queueSize);

    public void addLog(E eventObject, String logMessage) {
        // 500개가 초과하면 가장 오래된 로그를 비우고 추가하는 식으로 동작
        if (logQueue.size() >= queueSize) {
            logQueue.remove();
        }
        logQueue.offer(new LogObject<E>(eventObject, logMessage));
    }

    @Data
    @AllArgsConstructor
    public static class LogObject<E> {
        E eventObject;
        String logMessage;
    }
}
