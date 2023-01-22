package com.devkbil.mtssbj.common.log;

import lombok.Getter;

public enum MDCKey {
    TRX_ID("trxId");

    @Getter
    private final String key;

    MDCKey(String key) {
        this.key = key;
    }
}
