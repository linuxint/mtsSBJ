package com.devkbil.mtssbj.sign;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "결재 : SignVO")
@XmlRootElement(name = "SignVO")
@XmlType(propOrder = {"ssno", "docno", "ssstep", "sstype", "ssresult", "sscomment", "receivedate", "signdate", "userno",
        "usernm", "userpos"})
@Getter
@Setter
public class SignVO {

    @Schema(description = "결재번호")
    private String ssno;        // 결재번호
    @Schema(description = "문서번호")
    private String docno;        // 문서번호
    @Schema(description = "결재단계")
    private String ssstep;    // 결재단계
    @Schema(description = "결재종류")
    private String sstype;        // 결재종류
    @Schema(description = "결재결과")
    private String ssresult;    // 결재결과
    @Schema(description = "코멘트")
    private String sscomment;    // 코멘트
    @Schema(description = "받은일자")
    private String receivedate; // 받은일자
    @Schema(description = "결재일자")
    private String signdate; // 결재일자
    @Schema(description = "사용자번호")
    private String userno; // 사용자번호
    @Schema(description = "사용자명")
    private String usernm; // 사용자명
    @Schema(description = "직위")
    private String userpos;    // 직위

}
