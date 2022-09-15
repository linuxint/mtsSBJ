package com.devkbil.mtssbj.admin.organ;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@ApiModel(value = "부서 : DepartmentVO", description = "부서")
@XmlRootElement(name = "DepartmentVO")
@XmlType(propOrder = {"deptno", "deptnm", "parentno"})
@Getter
@Setter
public class DepartmentVO {
    
    @ApiModelProperty(value = "부서코드")
    private String deptno;
    
    @ApiModelProperty(value = "부서명")
    private String deptnm;
    
    @ApiModelProperty(value = "상위부서코드")
    private String parentno;
    
}
