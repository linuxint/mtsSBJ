package com.devkbil.schedule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@ApiModel(value = "프로젝트 : CalendarVO", description = "CalendarVO")
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
 