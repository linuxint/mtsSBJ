package com.devkbil.mtssbj.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@ApiModel(value = "달력 : DateVO", description = "달력")
@XmlRootElement(name = "달력")
@XmlType(propOrder = {"year", "month", "day", "week", "istoday", "list"})
@Getter
@Setter
public class DateVO {
    @ApiModelProperty(value = "연도")
    private int year;
    @ApiModelProperty(value = "월")
    private int month;
    @ApiModelProperty(value = "일")
    private int day;
    @ApiModelProperty(value = "날짜")
    private String date;
    @ApiModelProperty(value = "주")
    private String week;
    @ApiModelProperty(value = "금일")
    private boolean istoday = false;
    @ApiModelProperty(value = "날짜목록")
    private List<?> list;
    
}