package com.devkbil.mtssbj.schedule;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "일정 : SchDetailVO", description = "일정정보")
@XmlRootElement(name = "SchDetailVO")
@XmlType(propOrder = {"ssno", "sddate", "sdhour", "sdminute", "userno", "sstitle", "fontcolor", "sdseq"})
@Getter
@Setter
public class SchDetailVO {

    @ApiModelProperty(value = "일정번호")
    private String ssno;        //일정번호
    @ApiModelProperty(value = "날짜")
    private String sddate;         //날짜
    @ApiModelProperty(value = "시간")
    private String sdhour;         //시간
    @ApiModelProperty(value = "분")
    private String sdminute;     //분
    @ApiModelProperty(value = "사용자")
    private String userno;    // 사용자
    @ApiModelProperty(value = "일정명")
    private String sstitle; // 일정명
    @ApiModelProperty(value = "글자색")
    private String fontcolor; // 글자색
    @ApiModelProperty(value = "순번")
    private Integer sdseq;         //순번

}
