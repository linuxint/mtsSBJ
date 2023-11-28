package com.devkbil.mtssbj.board;

import com.devkbil.mtssbj.common.util.UtilEtc;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "게시글 댓글 : Board Reply")
@XmlRootElement(name = "boardreplyvo")
@XmlType(propOrder = {"brdno", "reno", "rewriter", "rememo", "regdate", "reparent", "redepth", "reorder", "userno",
        "usernm", "photo"})
@Getter
@Setter
public class BoardReplyVO {

    @Schema(description = "게시물 번호")
    private String brdno;
    @Schema(description = "댓글 번호")
    private String reno;
    @Schema(description = "댓글 작성자")
    private String rewriter;
    @Schema(description = "댓글 내용")
    private String rememo;
    @Schema(description = "작성일자")
    private String regdate;
    @Schema(description = "부모댓글")
    private String reparent;
    @Schema(description = "깊이")
    private String redepth;
    @Schema(description = "순서")
    private Integer reorder;
    @Schema(description = "작성자")
    private String userno;
    @Schema(description = "작성자명")
    private String usernm;
    @Schema(description = "사용자사진")
    private String photo;

    @Schema(description = "댓글Html")
    public String getRememoByHTML() {
        return UtilEtc.text2Html(rememo);
    }

}
