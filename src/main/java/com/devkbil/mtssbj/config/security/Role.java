package com.devkbil.mtssbj.config.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {

    ROLE_ADMIN("ADMIN")  // 관리자
    , ROLE_USER("USER")  // 인가 사용자
    , ROLE_GUEST("GUEST"); // 미인가 사용자
    
    private String value;
}