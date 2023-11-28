package com.devkbil.mtssbj.common;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "조회수 : CountVO")
@XmlRootElement(name = "CountVO")
@XmlType(propOrder = {"field1", "cnt1"})
@Getter
@Setter
public class CountVO {
    @Schema(description = "조회필드")
    private String field1;
    @Schema(description = "조회수")
    private Integer cnt1;

}
