package com.devkbil.mtssbj.admin.board;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "게시판 그룹 : BoardGroupVO")
@XmlRootElement(name = "BoardGroupVO")
@XmlType(propOrder = {"bgno", "bgname", "bglevel", "bgparent", "deleteflag", "bgused", "bgreply", "bgreadonly",
        "regdate", "chgdate", "bgnotice"})
@Getter
@Setter
public class BoardGroupVO {

    @Schema(description = "게시글 그룹 부모")
    private String bglevel;
    @Schema(description = "게시글 그룹번호")
    private String bgnotice;

    @Schema(description = "게시글 그룹번호")
    private String bgno;
    @Schema(description = "게시글 그룹명")
    private String bgname;
    @Schema(description = "삭제 여부")
    private String bgparent;
    @Schema(description = "사용 여부")
    private String deleteflag;
    @Schema(description = "댓글 사용여부")
    private String bgused;
    @Schema(description = "글쓰기 가능 여부")
    private String bgreply;
    @Schema(description = "공지 쓰기  가능 여부")
    private String bgreadonly;
    @Schema(description = "생성일자")
    private String regdate;
    @Schema(description = "변경일자")
    private String chgdate;

}
