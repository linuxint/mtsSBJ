package com.devkbil.mtssbj.project;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "프로젝트 : ProjectVO")
@XmlRootElement(name = "ProjectVO")
@XmlType(propOrder = {"prno", "prstartdate", "prenddate", "prtitle", "regdate", "userno", "usernm", "prstatus",
        "deleteflag"})
@Getter
@Setter
public class ProjectVO {

    @Schema(description = "프로젝트 번호")
    private String prno;        //프로젝트 번호
    @Schema(description = "시작일자")
    private String prstartdate;    //시작일자
    @Schema(description = "종료일자")
    private String prenddate;    //종료일자
    @Schema(description = "프로젝트 제목")
    private String prtitle;        //프로젝트 제목
    @Schema(description = "작성일자")
    private String regdate;        //작성일자
    @Schema(description = "사용자번호")
    private String userno;        //사용자번호
    @Schema(description = "사용자명")
    private String usernm;        //사용자명
    @Schema(description = "상태")
    private String prstatus;    //상태
    @Schema(description = "삭제")
    private String deleteflag;    //삭제

}
