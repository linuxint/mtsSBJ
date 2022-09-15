package com.devkbil.mtssbj.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@ApiModel(value = "부서 : CountVO", description = "게시판조회수")
@XmlRootElement(name = "CountVO")
@XmlType(propOrder = {"field1", "cnt1"})
@Getter
@Setter
public class CountVO {
    @ApiModelProperty(value = "조회필드")
    private String field1;
    @ApiModelProperty(value = "조회수")
    private Integer cnt1;
    
}
