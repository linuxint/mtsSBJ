package com.devkbil.mtssbj.crud;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "CRUD : CrudVO")
@XmlRootElement(name = "CrudVO")
@XmlType(propOrder = {"crno", "crtitle", "userno", "crmemo", "regdate", "usernm"})
@Getter
@Setter
public class CrudVO {

    @Schema(description = "번호")
    private String crno;        // 번호
    @Schema(description = "제목")
    private String crtitle;    // 제목
    @Schema(description = "작성자")
    private String userno;        // 작성자
    @Schema(description = "내용")
    private String crmemo;        // 내용
    @Schema(description = "작성일자")
    private String regdate;        // 작성일자
    @Schema(description = "작성자 이름")
    private String usernm;        // 작성자 이름

}
