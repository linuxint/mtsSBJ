package com.devkbil.mtssbj.common;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "확장필드 : ExtFieldVO", description = "확장필드")
@XmlRootElement(name = "ExtFieldVO")
@XmlType(propOrder = {"field1", "field2", "field3"})
@Getter
@Setter
public class ExtFieldVO {
    @ApiModelProperty(value = "확장필드1")
    private String field1;
    @ApiModelProperty(value = "확장필드2")
    private String field2;
    @ApiModelProperty(value = "확장필드3")
    private String field3;

    /**
     * 한번에 값 설정.
     */
    public ExtFieldVO() {
    }

    public ExtFieldVO(String field1, String field2, String field3) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
    }

}
