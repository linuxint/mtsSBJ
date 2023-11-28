package com.devkbil.mtssbj.schedule;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "일정정보 : SchDetailVO")
@XmlRootElement(name = "SchDetailVO")
@XmlType(propOrder = {"ssno", "sddate", "sdhour", "sdminute", "userno", "sstitle", "fontcolor", "sdseq"})
@Getter
@Setter
public class SchDetailVO {

    @Schema(description = "일정번호")
    private String ssno;        //일정번호
    @Schema(description = "날짜")
    private String sddate;         //날짜
    @Schema(description = "시간")
    private String sdhour;         //시간
    @Schema(description = "분")
    private String sdminute;     //분
    @Schema(description = "사용자")
    private String userno;    // 사용자
    @Schema(description = "일정명")
    private String sstitle; // 일정명
    @Schema(description = "글자색")
    private String fontcolor; // 글자색
    @Schema(description = "순번")
    private Integer sdseq;         //순번

}
