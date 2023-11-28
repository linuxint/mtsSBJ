package com.devkbil.mtssbj.admin.organ;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "부서 : DepartmentVO")
@XmlRootElement(name = "DepartmentVO")
@XmlType(propOrder = {"deptno", "deptnm", "parentno"})
@Getter
@Setter
public class DepartmentVO {

    @Schema(description = "부서코드")
    private String deptno;

    @Schema(description = "부서명")
    private String deptnm;

    @Schema(description = "상위부서코드")
    private String parentno;

}
