package com.devkbil.mtssbj.sign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@ApiModel(value = "결재 : SignVO", description = "SignVO")
@XmlRootElement(name = "SignVO")
@XmlType(propOrder = {"ssno", "docno", "ssstep", "sstype", "ssresult", "sscomment", "receivedate", "signdate", "userno", "usernm", "userpos"})
@Getter
@Setter
public class SignVO {
    
    @ApiModelProperty(value = "결재번호")
    private String ssno;        // 결재번호
    @ApiModelProperty(value = "문서번호")
    private String docno;        // 문서번호
    @ApiModelProperty(value = "결재단계")
    private String ssstep;    // 결재단계
    @ApiModelProperty(value = "결재종류")
    private String sstype;        // 결재종류
    @ApiModelProperty(value = "결재결과")
    private String ssresult;    // 결재결과
    @ApiModelProperty(value = "코멘트")
    private String sscomment;    // 코멘트
    @ApiModelProperty(value = "받은일자")
    private String receivedate; // 받은일자
    @ApiModelProperty(value = "결재일자")
    private String signdate; // 결재일자
    @ApiModelProperty(value = "사용자번호")
    private String userno; // 사용자번호
    @ApiModelProperty(value = "사용자명")
    private String usernm; // 사용자명
    @ApiModelProperty(value = "직위")
    private String userpos;    // 직위
    
}
