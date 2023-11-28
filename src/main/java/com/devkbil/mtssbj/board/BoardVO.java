package com.devkbil.mtssbj.board;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "게시글 정보 : Board")
@XmlRootElement(name = "BoardVO")
@XmlType(propOrder = {"bgno", "bgname", "brdno", "brdtitle", "brdwriter", "brdmemo", "regdate", "regtime", "brdhit",
        "deleteflag", "filecnt", "replycnt", "userno", "usernm", "brdnotice", "brdlike", "brdlikechk", "extfield1",
        "etc1", "etc2", "etc3", "etc4", "etc5"})
@Getter
@Setter
public class BoardVO {

    @Schema(description = "게시글 그룹번호")
    private String bgno;

    @Schema(description = "게시글 번호")
    private String bgname;

    @Schema(description = "게시글 번호")
    private String brdno;

    @Schema(description = "게시글 제목")
    private String brdtitle;

    @Schema(description = "게시글 작성자")
    private String brdwriter;

    @Schema(description = "게시글 메모")
    private String brdmemo;

    @Schema(description = "게시글 작성일")
    private String regdate;

    @Schema(description = "게시글 작성시간")
    private String regtime;

    @Schema(description = "게시글 조회수")
    private String brdhit;

    @Schema(description = "게시글 삭제여부")
    private String deleteflag;

    @Schema(description = "게시글 첨부파일개수")
    private String filecnt;

    @Schema(description = "게시글 댓글수")
    private String replycnt;

    @Schema(description = "게시글 작성자")
    private String userno;

    @Schema(description = "게시글 작성자명")
    private String usernm;

    @Schema(description = "게시글 공지사항여부")
    private String brdnotice;

    @Schema(description = "게시글 좋아요수")
    private String brdlike;

    @Schema(description = "게시글")
    private String brdlikechk;

    @Schema(description = "게시글 기타필드1")
    private String extfield1;

    @Schema(description = "게시글 임시필드1")
    private String etc1;

    @Schema(description = "게시글 임시필드2")
    private String etc2;

    @Schema(description = "게시글 임시필드3")
    private String etc3;

    @Schema(description = "게시글 임시필드4")
    private String etc4;

    @Schema(description = "게시글 임시필드5")
    private String etc5;

    /* 첨부파일 */
    @Schema(description = "게시글 업로드파일")
    private List<MultipartFile> uploadfile;

    public String getBrdmemo() {
        if (brdmemo == null || brdmemo == "") {
            return "";
        }
        return brdmemo.replaceAll("(?i)<script", "&lt;script");
    }
}
