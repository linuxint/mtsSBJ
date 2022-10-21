package com.devkbil.mtssbj.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@ApiModel(value = "프로젝트타스크 : TaskVO", description = "프로젝트 타스크 정보")
@XmlRootElement(name = "TaskVO")
@XmlType(propOrder = {"prno", "tsno", "tsparent", "tssort", "tstitle", "tsstartdate", "tsenddate", "tsendreal", "tsrate", "userno", "usernm", "statuscolor", "uploadfile"})
@Getter
@Setter
public class TaskVO {
    
    @ApiModelProperty(value = "프로젝트 번호")
    private String prno;        //프로젝트 번호
    @ApiModelProperty(value = "업무번호")
    private String tsno;        //업무번호
    @ApiModelProperty(value = "부모업무번호")
    private String tsparent;    //부모업무번호
    @ApiModelProperty(value = "정렬")
    private String tssort;    //정렬
    @ApiModelProperty(value = "업무 제목")
    private String tstitle;    //업무 제목
    @ApiModelProperty(value = "시작일자")
    private String tsstartdate; //시작일자
    @ApiModelProperty(value = "종료일자")
    private String tsenddate;    //종료일자
    @ApiModelProperty(value = "종료일자(실제)")
    private String tsendreal;    //종료일자(실제)
    @ApiModelProperty(value = "진행율")
    private String tsrate;    //진행율
    @ApiModelProperty(value = "작업자번호")
    private String userno; // 작업자번호
    @ApiModelProperty(value = "작업자명")
    private String usernm; // 작업자명
    @ApiModelProperty(value = "업무 진행 상태용 색")
    private String statuscolor;    // 업무 진행 상태용 색
    @ApiModelProperty(value = "첨부파일")
    private List<MultipartFile> uploadfile; // 첨부파일
    
}