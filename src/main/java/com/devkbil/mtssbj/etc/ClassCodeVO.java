package com.devkbil.mtssbj.etc;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "코드 : ClassCodeVO", description = "공통코드")
@XmlRootElement(name = "ClassCodeVO")
@XmlType(propOrder = {"codecd", "codenm", "tmp"})
@Getter
@Setter
public class ClassCodeVO {

    @ApiModelProperty(value = "코드")
    private String codecd;
    @ApiModelProperty(value = "코드명")
    private String codenm;
    @ApiModelProperty(value = "다용도")
    private String tmp; // 다용도

}
