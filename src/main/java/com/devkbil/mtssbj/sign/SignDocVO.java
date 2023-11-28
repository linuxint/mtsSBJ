package com.devkbil.mtssbj.sign;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "결재문서 : SignDocVO")
@XmlRootElement(name = "SignDocVO")
@XmlType(propOrder = {"docno", "doctitle", "doccontents", "docstatus", "docstep", "dtno", "dttitle", "userno", "usernm",
        "deptnm", "chgdate", "docsignpath"})
@Getter
@Setter
public class SignDocVO {

    @Schema(description = "문서번호")
    private String docno;        // 문서번호
    @Schema(description = "제목")
    private String doctitle;        // 제목
    @Schema(description = "문서내용")
    private String doccontents;        // 문서내용
    @Schema(description = "문서상태")
    private String docstatus;        // 문서상태
    @Schema(description = "결재단계")
    private String docstep;        // 결재단계
    @Schema(description = "양식번호")
    private String dtno;            // 양식번호
    @Schema(description = "양식명")
    private String dttitle;        // 양식명
    @Schema(description = "사용자번호")
    private String userno;        // 사용자번호
    @Schema(description = "사용자명")
    private String usernm;        // 사용자명
    @Schema(description = "부서명")
    private String deptnm;        // 부서명
    @Schema(description = "수정일자")
    private String chgdate;    // 수정일자
    @Schema(description = "결재경로문자열")
    private String docsignpath;        // 결재경로문자열

}
