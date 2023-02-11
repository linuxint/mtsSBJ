package com.devkbil.mtssbj.error;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String name();

    HttpStatus getResultCode();

    String getResultMsg();
}
