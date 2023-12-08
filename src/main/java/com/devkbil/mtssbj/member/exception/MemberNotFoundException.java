package com.devkbil.mtssbj.member.exception;

import com.global.error.exception.EntityNotFoundException;

public class MemberNotFoundException extends EntityNotFoundException {

    public MemberNotFoundException(String target) {
        super(target + " - 없는 회원입니다.");
    }
}
