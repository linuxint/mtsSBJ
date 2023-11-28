package com.devkbil.mtssbj.schedule;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "일정 : SchVO")
@XmlRootElement(name = "SchVO")
@XmlType(propOrder = {"ssno", "sstitle", "sstype", "ssstartdate", "ssstarthour", "ssstartminute", "ssenddate",
        "ssendhour", "ssendminute", "ssrepeattype", "ssrepeattypenm", "ssrepeatoption", "ssrepeatend", "sscontents",
        "ssisopen", "userno", "usernm"})
@Getter
@Setter
public class SchVO {

    @Schema(description = "일정번호")
    private String ssno;    //일정번호
    @Schema(description = "일정명")
    private String sstitle;    //일정명
    @Schema(description = "구분")
    private String sstype;    //구분
    @Schema(description = "시작일")
    private String ssstartdate; //시작일
    @Schema(description = "시작일-시간")
    private String ssstarthour; //시작일-시간
    @Schema(description = "시작일-분")
    private String ssstartminute; //시작일-분
    @Schema(description = "종료일")
    private String ssenddate; //종료일
    @Schema(description = "종료일-시간")
    private String ssendhour; //종료일-시간
    @Schema(description = "종료일-분")
    private String ssendminute; //종료일-분
    @Schema(description = "반복")
    private String ssrepeattype; //반복
    @Schema(description = "반복")
    private String ssrepeattypenm; // 반복
    @Schema(description = "반복 옵션- 주")
    private String ssrepeatoption; //반복 옵션- 주
    @Schema(description = "반복종료")
    private String ssrepeatend; //반복종료
    @Schema(description = "내용")
    private String sscontents; //내용
    @Schema(description = "공개여부")
    private String ssisopen; //공개여부
    @Schema(description = "사용자번호")
    private String userno; //사용자번호
    @Schema(description = "사용자명")
    private String usernm; //사용자명

}
