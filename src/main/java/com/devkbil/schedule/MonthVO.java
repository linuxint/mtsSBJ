package com.devkbil.schedule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@ApiModel(value = "프로젝트 : MonthVO", description = "MonthVO")
@XmlRootElement(name = "MonthVO")
@XmlType(propOrder = {"year", "month"})
@Getter
@Setter
public class MonthVO {
    
    @ApiModelProperty(value = "연도")
    private String year; // 연도
    @ApiModelProperty(value = "월")
    private String month; // 월
    
    
}
 