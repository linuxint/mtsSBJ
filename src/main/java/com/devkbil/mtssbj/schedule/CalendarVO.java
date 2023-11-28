package com.devkbil.mtssbj.schedule;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "달력 : CalendarVO")
@XmlRootElement(name = "CalendarVO")
@XmlType(propOrder = {"cddate", "cddd", "cddayofweek", "list"})
@Getter
@Setter
public class CalendarVO {

    @Schema(description = "날짜")
    private String cddate; //  날짜
    @Schema(description = "일")
    private Integer cddd; // 일
    @Schema(description = "주별일자")
    private Integer cddayofweek;
    @Schema(description = "")
    private List<?> list;

}
