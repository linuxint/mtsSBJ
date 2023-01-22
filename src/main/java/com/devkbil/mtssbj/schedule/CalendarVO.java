package com.devkbil.mtssbj.schedule;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "달력 : CalendarVO", description = "달력항목")
@XmlRootElement(name = "CalendarVO")
@XmlType(propOrder = {"cddate", "cddd", "cddayofweek", "list"})
@Getter
@Setter
public class CalendarVO {

    @ApiModelProperty(value = "날짜")
    private String cddate; //  날짜
    @ApiModelProperty(value = "일")
    private Integer cddd; // 일
    @ApiModelProperty(value = "주별일자")
    private Integer cddayofweek;
    @ApiModelProperty(value = "")
    private List<?> list;

}
