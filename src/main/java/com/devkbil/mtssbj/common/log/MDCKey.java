package com.devkbil.mtssbj.common.log;

import lombok.Getter;

@Getter
public enum MDCKey {
    TRX_ID("trxId");

    private final String key;

    MDCKey(String key) {
        this.key = key;
    }
}
