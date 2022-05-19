package com.devkbil.schedule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@ApiModel(value = "일정 : SchVO", description = "SchVO")
@XmlRootElement(name = "SchVO")
@XmlType(propOrder = {"ssno", "sstitle", "sstype", "ssstartdate", "ssstarthour", "ssstartminute", "ssenddate", "ssendhour", "ssendminute", "ssrepeattype", "ssrepeattypenm", "ssrepeatoption", "ssrepeatend", "sscontents", "ssisopen", "userno", "usernm"})
@Getter
@Setter
@ToString
public class SchVO {
    
    @ApiModelProperty(value = "일정번호")
    private String ssno;    //일정번호
    @ApiModelProperty(value = "일정명")
    private String sstitle;    //일정명
    @ApiModelProperty(value = "구분")
    private String sstype;    //구분
    @ApiModelProperty(value = "시작일")
    private String ssstartdate; //시작일
    @ApiModelProperty(value = "시작일-시간")
    private String ssstarthour; //시작일-시간
    @ApiModelProperty(value = "시작일-분")
    private String ssstartminute; //시작일-분
    @ApiModelProperty(value = "종료일")
    private String ssenddate; //종료일
    @ApiModelProperty(value = "종료일-시간")
    private String ssendhour; //종료일-시간
    @ApiModelProperty(value = "종료일-분")
    private String ssendminute; //종료일-분
    @ApiModelProperty(value = "반복")
    private String ssrepeattype; //반복
    @ApiModelProperty(value = "반복")
    private String ssrepeattypenm; // 반복
    @ApiModelProperty(value = "반복 옵션- 주")
    private String ssrepeatoption; //반복 옵션- 주
    @ApiModelProperty(value = "반복종료")
    private String ssrepeatend; //반복종료
    @ApiModelProperty(value = "내용")
    private String sscontents; //내용
    @ApiModelProperty(value = "공개여부")
    private String ssisopen; //공개여부
    @ApiModelProperty(value = "사용자번호")
    private String userno; //사용자번호
    @ApiModelProperty(value = "사용자명")
    private String usernm; //사용자명
    
}
