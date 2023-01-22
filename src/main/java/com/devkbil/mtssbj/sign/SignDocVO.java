package com.devkbil.mtssbj.sign;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "결재문서 : SignDocVO", description = "SignDocVO")
@XmlRootElement(name = "SignDocVO")
@XmlType(propOrder = {"docno", "doctitle", "doccontents", "docstatus", "docstep", "dtno", "dttitle", "userno", "usernm",
        "deptnm", "updatedate", "docsignpath"})
@Getter
@Setter
public class SignDocVO {

    @ApiModelProperty(value = "문서번호")
    private String docno;        // 문서번호
    @ApiModelProperty(value = "제목")
    private String doctitle;        // 제목
    @ApiModelProperty(value = "문서내용")
    private String doccontents;        // 문서내용
    @ApiModelProperty(value = "문서상태")
    private String docstatus;        // 문서상태
    @ApiModelProperty(value = "결재단계")
    private String docstep;        // 결재단계
    @ApiModelProperty(value = "양식번호")
    private String dtno;            // 양식번호
    @ApiModelProperty(value = "양식명")
    private String dttitle;        // 양식명
    @ApiModelProperty(value = "사용자번호")
    private String userno;        // 사용자번호
    @ApiModelProperty(value = "사용자명")
    private String usernm;        // 사용자명
    @ApiModelProperty(value = "부서명")
    private String deptnm;        // 부서명
    @ApiModelProperty(value = "수정일자")
    private String updatedate;    // 수정일자
    @ApiModelProperty(value = "결재경로문자열")
    private String docsignpath;        // 결재경로문자열

}
