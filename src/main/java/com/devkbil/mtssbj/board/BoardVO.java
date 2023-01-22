package com.devkbil.mtssbj.board;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "게시글 정보 : Board", description = "게시글 정보")
@XmlRootElement(name = "BoardVO")
@XmlType(propOrder = {"bgno", "bgname", "brdno", "brdtitle", "brdwriter", "brdmemo", "brddate", "brdtime", "brdhit",
        "brddeleteflag", "filecnt", "replycnt", "userno", "usernm", "brdnotice", "brdlike", "brdlikechk", "extfield1",
        "etc1", "etc2", "etc3", "etc4", "etc5"})
@Getter
@Setter
public class BoardVO {

    @ApiModelProperty(value = "게시글 그룹번호")
    private String bgno;

    @ApiModelProperty(value = "게시글 번호")
    private String bgname;

    @ApiModelProperty(value = "게시글 번호")
    private String brdno;

    @ApiModelProperty(value = "게시글 제목")
    private String brdtitle;

    @ApiModelProperty(value = "게시글 작성자")
    private String brdwriter;

    @ApiModelProperty(value = "게시글 메모")
    private String brdmemo;

    @ApiModelProperty(value = "게시글 작성일")
    private String brddate;

    @ApiModelProperty(value = "게시글 작성시간")
    private String brdtime;

    @ApiModelProperty(value = "게시글 조회수")
    private String brdhit;

    @ApiModelProperty(value = "게시글 삭제여부")
    private String brddeleteflag;

    @ApiModelProperty(value = "게시글 첨부파일개수")
    private String filecnt;

    @ApiModelProperty(value = "게시글 댓글수")
    private String replycnt;

    @ApiModelProperty(value = "게시글 작성자")
    private String userno;

    @ApiModelProperty(value = "게시글 작성자명")
    private String usernm;

    @ApiModelProperty(value = "게시글 공지사항여부")
    private String brdnotice;

    @ApiModelProperty(value = "게시글 좋아요수")
    private String brdlike;

    @ApiModelProperty(value = "게시글")
    private String brdlikechk;

    @ApiModelProperty(value = "게시글 기타필드1")
    private String extfield1;

    @ApiModelProperty(value = "게시글 임시필드1")
    private String etc1;

    @ApiModelProperty(value = "게시글 임시필드2")
    private String etc2;

    @ApiModelProperty(value = "게시글 임시필드3")
    private String etc3;

    @ApiModelProperty(value = "게시글 임시필드4")
    private String etc4;

    @ApiModelProperty(value = "게시글 임시필드5")
    private String etc5;

    /* 첨부파일 */
    @ApiModelProperty(value = "게시글 업로드파일")
    private List<MultipartFile> uploadfile;

    public String getBrdmemo() {
        if (brdmemo == null || brdmemo == "") {
            return "";
        }
        return brdmemo.replaceAll("(?i)<script", "&lt;script");
    }
}
