package com.devkbil.mtssbj.schedule;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "년월 : MonthVO")
@XmlRootElement(name = "MonthVO")
@XmlType(propOrder = {"year", "month"})
@Getter
@Setter
public class MonthVO {

    @Schema(description = "연도")
    private String year; // 연도
    @Schema(description = "월")
    private String month; // 월

}
