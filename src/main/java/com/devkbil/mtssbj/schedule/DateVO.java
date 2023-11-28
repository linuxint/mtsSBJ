package com.devkbil.mtssbj.schedule;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "달력 : DateVO")
@XmlRootElement(name = "DateVO")
@XmlType(propOrder = {"year", "month", "day", "week", "istoday", "list"})
@Getter
@Setter
public class DateVO {
    @Schema(description = "연도")
    private int year;
    @Schema(description = "월")
    private int month;
    @Schema(description = "일")
    private int day;
    @Schema(description = "날짜")
    private String date;
    @Schema(description = "주")
    private String week;
    @Schema(description = "금일")
    private boolean istoday = false;
    @Schema(description = "날짜목록")
    private List<?> list;

}
