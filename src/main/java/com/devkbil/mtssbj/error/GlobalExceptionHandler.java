package com.devkbil.mtssbj.error;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.devkbil.mtssbj.common.util.JsonUtil;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //     public ResponseEntity<User> getUser() {
    //         throw new RestApiException(UserErrorCode.INACTIVE_USER);
    //     }
/*
    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<Object> handleCustomException(RestApiException e) {
        log.error(e.getMessage(), e);
        ErrorCode errorCode = CommonErrorCode.NOT_FOUND;
        return handleExceptionInternal(errorCode);
    }
*/
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException e) {
        log.warn("handleIllegalArgument", e);
        ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
        //return handleExceptionInternal(errorCode, e.getMessage());
        return handleExceptionInternal(errorCode);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        log.warn("handleIllegalArgument", e);
        ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(e, errorCode);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAllException(Exception ex) {
        log.warn("handleAllException", ex);
        ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<Object> handleNoHandlerFoundException(RestApiException e) {
        log.error(e.getMessage(), e);
        ErrorCode errorCode = CommonErrorCode.NOT_FOUND;
        return handleExceptionInternal(errorCode);
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getResultCode())
                .body(makeErrorResponse(errorCode));
    }

    private ResponseEntity<Object> handleExceptionInternal(BindException e, ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getResultCode())
                .body(makeErrorResponse(e, errorCode));
    }

    private String makeErrorResponse(ErrorCode errorCode) {
        HeaderVO header = new HeaderVO();
        header.setError(errorCode.name());
        header.setStatus(errorCode.getResultCode().value());
        header.setMessage(errorCode.getResultMsg());
        //return CmmnVar.GSON.toJson(ErrorResponse.builder().header(header).build());
        return JsonUtil.toJson(ErrorResponse.builder().header(header).build());
    }

    private ErrorResponse makeErrorResponse(BindException e, ErrorCode errorCode) {
        List<ErrorResponse.ValidationError> validationErrorList = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ErrorResponse.ValidationError::of)
                .collect(Collectors.toList());

        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getResultMsg())
                .errors(validationErrorList)
                .build();
    }
}
