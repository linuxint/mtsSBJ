package com.devkbil.mtssbj.common.exception;

import javax.servlet.ServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParamException extends SysException {
    private static final long serialVersionUID = 1646146341475387504L;

    public ParamException(String paramName) {
        super("입력 필드명 : " + paramName);
    }

    public ParamException(String paramName, ServletRequest request) {
        super("입력 필드명 : " + paramName);
        log.debug(request.toString());
    }

    public String getErrorCode() {
        return "FRM00001";
    }
}
