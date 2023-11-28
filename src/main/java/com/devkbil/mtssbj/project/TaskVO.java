package com.devkbil.mtssbj.project;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "프로젝트타스크 : TaskVO")
@XmlRootElement(name = "TaskVO")
@XmlType(propOrder = {"prno", "tsno", "tsparent", "tssort", "tstitle", "tsstartdate", "tsenddate", "tsendreal",
        "tsrate", "userno", "usernm", "statuscolor", "uploadfile"})
@Getter
@Setter
public class TaskVO {

    @Schema(description = "프로젝트 번호")
    private String prno;        //프로젝트 번호
    @Schema(description = "업무번호")
    private String tsno;        //업무번호
    @Schema(description = "부모업무번호")
    private String tsparent;    //부모업무번호
    @Schema(description = "정렬")
    private String tssort;    //정렬
    @Schema(description = "업무 제목")
    private String tstitle;    //업무 제목
    @Schema(description = "시작일자")
    private String tsstartdate; //시작일자
    @Schema(description = "종료일자")
    private String tsenddate;    //종료일자
    @Schema(description = "종료일자(실제)")
    private String tsendreal;    //종료일자(실제)
    @Schema(description = "진행율")
    private String tsrate;    //진행율
    @Schema(description = "작업자번호")
    private String userno; // 작업자번호
    @Schema(description = "작업자명")
    private String usernm; // 작업자명
    @Schema(description = "업무 진행 상태용 색")
    private String statuscolor;    // 업무 진행 상태용 색
    @Schema(description = "첨부파일")
    private List<MultipartFile> uploadfile; // 첨부파일

}
