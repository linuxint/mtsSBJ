package com.devkbil.mtssbj.admin.sign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@ApiModel(value = "문서양식 : SignDocTypeVO", description = "문서양식")
@XmlRootElement(name = "SignDocTypeVO")
@XmlType(propOrder = {"dtno", "dttitle", "dtcontents"})
@Getter
@Setter
public class SignDocTypeVO {
    
    @ApiModelProperty(value = "번호")
    private String dtno;        // 번호
    @ApiModelProperty(value = "제목")
    private String dttitle;    // 제목
    @ApiModelProperty(value = "양식 내용")
    private String dtcontents;    // 양식 내용
    
}
