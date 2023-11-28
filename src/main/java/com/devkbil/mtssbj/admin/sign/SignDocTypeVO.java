package com.devkbil.mtssbj.admin.sign;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "문서양식 : SignDocTypeVO")
@XmlRootElement(name = "SignDocTypeVO")
@XmlType(propOrder = {"dtno", "dttitle", "dtcontents"})
@Getter
@Setter
public class SignDocTypeVO {

    @Schema(description = "번호")
    private String dtno;        // 번호
    @Schema(description = "제목")
    private String dttitle;    // 제목
    @Schema(description = "양식 내용")
    private String dtcontents;    // 양식 내용

}
