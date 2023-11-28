package com.devkbil.mtssbj.admin.code;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "공통코드 : CodeVO")
@XmlRootElement(name = "CodeVO")
@XmlType(propOrder = {"classno", "codecd", "codenm"})
@Getter
@Setter
public class CodeVO {

    @Schema(description = "대분류")
    private String classno;    // 대분류
    @Schema(description = "코드")
    private String codecd;    // 코드
    @Schema(description = "코드명")
    private String codenm;    // 코드명

}
