package com.devkbil.mtssbj.admin.board;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@ApiModel(value = "게시판 그룹 : BoardGroupVO", description = "게시판 그룹")
@XmlRootElement(name = "BoardGroupVO")
@XmlType(propOrder = {"bgno", "bgname", "bglevel", "bgparent", "bgdeleteflag", "bgused", "bgreply", "bgreadonly", "bgdate", "bgnotice"})
@Getter
@Setter
public class BoardGroupVO {
    
    @ApiModelProperty(value = "게시글 그룹번호")
    private String bgno;
    @ApiModelProperty(value = "게시글 그룹명")
    private String bgname;
    @ApiModelProperty(value = "게시글 그룹 부모")
    private String bglevel;
    @ApiModelProperty(value = "삭제 여부")
    private String bgparent;
    @ApiModelProperty(value = "사용 여부")
    private String bgdeleteflag;
    @ApiModelProperty(value = "댓글 사용여부")
    private String bgused;
    @ApiModelProperty(value = "글쓰기 가능 여부")
    private String bgreply;
    @ApiModelProperty(value = "공지 쓰기  가능 여부")
    private String bgreadonly;
    @ApiModelProperty(value = "생성일자")
    private String bgdate;
    @ApiModelProperty(value = "게시글 그룹번호")
    private String bgnotice;
    
}
