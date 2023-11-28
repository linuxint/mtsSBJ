package com.devkbil.mtssbj.etc;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "코드 : ClassCodeVO")
@XmlRootElement(name = "ClassCodeVO")
@XmlType(propOrder = {"codecd", "codenm", "tmp"})
@Getter
@Setter
public class ClassCodeVO {

    @Schema(description = "코드")
    private String codecd;
    @Schema(description = "코드명")
    private String codenm;
    @Schema(description = "다용도")
    private String tmp; // 다용도

}
