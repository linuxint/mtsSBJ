package com.devkbil.mtssbj.error;

import lombok.Data;

@Data
public class HeaderVO {
    private int status;
    private String message;
    private Integer size;
    private String error;
}
