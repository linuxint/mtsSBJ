package com.devkbil.mtssbj.mail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@ApiModel(value = "코드 : TreeVO", description = "ClassCodeVO")
@XmlRootElement(name = "ClassCodeVO")
@XmlType(propOrder = {"emno", "eatype", "eaaddress", "easeq"})
@Getter
@Setter
public class MailAddressVO {
    
    @ApiModelProperty(value = "메일번호")
    private String emno; // 메일번호
    @ApiModelProperty(value = "순번")
    private String eatype; // 순번
    @ApiModelProperty(value = "주소종류")
    private String eaaddress; // 주소종류
    @ApiModelProperty(value = "메일주소")
    private Integer easeq; // 메일주소
    
} 