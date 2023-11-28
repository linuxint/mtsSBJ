package com.devkbil.mtssbj.error;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseEntity {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String code;
    private final String message;

    public static ResponseEntity<ErrorResponseEntity> toResponseEntity(ErrorCode errorCode){
        return ResponseEntity
                .status(errorCode.getResultCode())
                .body(ErrorResponseEntity.builder()
                        .status(errorCode.getResultCode().value())
                        .error(errorCode.getResultCode().name())
                        .code(errorCode.name())
                        .message(errorCode.getResultMsg())
                        .build()
                );
    }
}
