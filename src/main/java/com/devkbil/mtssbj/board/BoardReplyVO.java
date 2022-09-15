package com.devkbil.mtssbj.board;

import com.devkbil.mtssbj.common.UtilEtc;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@ApiModel(value = "게시글 댓글 : Board Reply", description = "게시글 댓글")
@XmlRootElement(name = "boardreplyvo")
@XmlType(propOrder = {"brdno", "reno", "rewriter", "rememo", "redate", "reparent", "redepth", "reorder", "userno", "usernm", "photo"})
@Getter
@Setter
public class BoardReplyVO {
    
    @ApiModelProperty(value = "게시물 번호")
    private String brdno;
    @ApiModelProperty(value = "댓글 번호")
    private String reno;
    @ApiModelProperty(value = "댓글 작성자")
    private String rewriter;
    @ApiModelProperty(value = "댓글 내용")
    private String rememo;
    @ApiModelProperty(value = "작성일자")
    private String redate;
    @ApiModelProperty(value = "부모댓글")
    private String reparent;
    @ApiModelProperty(value = "깊이")
    private String redepth;
    @ApiModelProperty(value = "순서")
    private Integer reorder;
    @ApiModelProperty(value = "작성자")
    private String userno;
    @ApiModelProperty(value = "작성자명")
    private String usernm;
    @ApiModelProperty(value = "사용자사진")
    private String photo;
    
    @ApiModelProperty(value = "댓글Html")
    public String getRememoByHTML() {
        return UtilEtc.text2Html(rememo);
    }
    
}
