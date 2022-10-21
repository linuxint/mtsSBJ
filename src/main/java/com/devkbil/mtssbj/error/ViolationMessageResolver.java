package com.devkbil.mtssbj.error;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.validation.MessageCodesResolver;

import javax.validation.ConstraintViolation;
import javax.validation.metadata.ConstraintDescriptor;
import java.util.Locale;
import java.util.Map;

/**
 * MessageCodesResolver 를 통해서 생성된 코드를 이용해서
 * properties 파일의 argument 를 name 기준으로 치환할 수 있는 클래스.
 * <pre>
 * 상품가격은 {validatedValue}은 사용할 수 없습니다. {min} ~ {max} 사이만 입력할 수 있습니다.
 * => 상품가격은 -1은 사용할 수 없습니다. 0 ~ 99999999 사이만 입력할 수 있습니다.
 * </pre>
 */
public class ViolationMessageResolver {
    private final MessageSource messageSource;
    private final MessageCodesResolver codesResolver;

    public ViolationMessageResolver(MessageSource messageSource,
                                    MessageCodesResolver codesResolver) {
        this.messageSource = messageSource;
        this.codesResolver = codesResolver;
    }

    public String message(ConstraintViolation<?> violation) {
        ConstraintDescriptor<?> descriptor = violation.getConstraintDescriptor();
        Map<String, Object> attributes = descriptor.getAttributes();

        String annotationName = descriptor.getAnnotation().annotationType().getSimpleName();
        String rootBeanName = violation.getRootBeanClass().getSimpleName();
        rootBeanName = rootBeanName.substring(0, 1).toLowerCase() + rootBeanName.substring(1);
        String path = violation.getPropertyPath().toString();

        // 애노테이션, 클래스, 필드 조합으로 코드 생성
        String[] codes = codesResolver.resolveMessageCodes(
                annotationName, rootBeanName, path, null);

        String result = null;
        for (String code : codes) {
            try {
                // 코드로 메시지 조회
                result = messageSource.getMessage(code, null, Locale.getDefault());
                for (Map.Entry<String, Object> es : attributes.entrySet()) {
                    // 애노테이션 attribute 를 기준으로 {...} 형태의 메시지 치환.
                    result = result.replace(
                            "{" + es.getKey() + "}", es.getValue().toString());
                }

                // 검증 대상값 치환
                result = result.replace("{validatedValue}",
                        violation.getInvalidValue().toString());
            } catch (NoSuchMessageException ignored) {
            }
        }
        // code 로 메시지를 찾지 못한 경우 기본값 사용
        if (result == null) {
            result = violation.getMessage();
        }
        return result;
    }
}