package com.devkbil.mtssbj.member.exception;

import com.global.error.exception.EntityNotFoundException;

public class EmailNotFoundException extends EntityNotFoundException {

    public EmailNotFoundException(String target) {
        super(target + " is not found");
    }
}
