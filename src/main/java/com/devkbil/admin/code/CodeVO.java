package com.devkbil.admin.code;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@ApiModel(value = "공통코드 : CodeVO", description = "공통코드")
@XmlRootElement(name = "CodeVO")
@XmlType(propOrder = {"classno", "codecd", "codenm"})
@Getter
@Setter
@ToString
public class CodeVO {
    
    @ApiModelProperty(value = "대분류")
    private String classno;    // 대분류
    @ApiModelProperty(value = "코드")
    private String codecd;    // 코드
    @ApiModelProperty(value = "코드명")
    private String codenm;    // 코드명
    
}
