package com.devkbil.mtssbj.common;

import lombok.Getter;

public enum MDCKey {
    TRX_ID("trxId");

    @Getter
    private String key;

    MDCKey(String key) {
        this.key = key;
    }
}