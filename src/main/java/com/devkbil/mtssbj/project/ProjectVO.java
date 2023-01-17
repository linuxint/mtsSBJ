package com.devkbil.mtssbj.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@ApiModel(value = "프로젝트 : ProjectVO", description = "프로젝트정보")
@XmlRootElement(name = "ProjectVO")
@XmlType(propOrder = {"prno", "prstartdate", "prenddate", "prtitle", "prdate", "userno", "usernm", "prstatus", "deleteflag"})
@Getter
@Setter
public class ProjectVO {

    @ApiModelProperty(value = "프로젝트 번호")
    private String prno;        //프로젝트 번호
    @ApiModelProperty(value = "시작일자")
    private String prstartdate;    //시작일자
    @ApiModelProperty(value = "종료일자")
    private String prenddate;    //종료일자
    @ApiModelProperty(value = "프로젝트 제목")
    private String prtitle;        //프로젝트 제목
    @ApiModelProperty(value = "작성일자")
    private String prdate;        //작성일자
    @ApiModelProperty(value = "사용자번호")
    private String userno;        //사용자번호
    @ApiModelProperty(value = "사용자명")
    private String usernm;        //사용자명
    @ApiModelProperty(value = "상태")
    private String prstatus;    //상태
    @ApiModelProperty(value = "삭제")
    private String deleteflag;    //삭제

}
