package com.devkbil.mtssbj.mail;

import java.util.ArrayList;

import com.devkbil.mtssbj.common.util.FileVO;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "메일 : MailVO")
@XmlRootElement(name = "MailVO")
@XmlType(propOrder = {"emno", "emtype", "emfrom", "emsubject", "emcontents", "regdate", "userno", "usernm", "emino",
        "strTo", "strCc", "strBcc", "emto", "emcc", "embcc", "files"})
@Getter
@Setter
public class MailVO {

    @Schema(description = "메일서비스")
    private String emno;

    @Schema(description = " 받은 / 보낸 메일")
    private String emtype;    // 받은 / 보낸 메일

    @Schema(description = "발신인")
    private String emfrom; //발신인

    @Schema(description = "제목")
    private String emsubject; // 제목

    @Schema(description = "내용")
    private String emcontents; // 내용

    @Schema(description = "작성일")
    private String regdate; // 작성일

    @Schema(description = "사용자번호")
    private String userno; // 사용자번호

    @Schema(description = "사용자명")
    private String usernm; // 사용자명

    @Schema(description = "메일정보번호")
    private String emino; // 메일정보번호

    @Schema(description = "수신자")
    private String strTo; // 수신자

    @Schema(description = "참조자")
    private String strCc; // 참조자

    @Schema(description = "숨은참조자")
    private String strBcc; // 숨은참조자

    @Schema(description = "수신인")
    private ArrayList<String> emto = new ArrayList<String>();
    @Schema(description = "참조자")
    private ArrayList<String> emcc = new ArrayList<String>();
    @Schema(description = "숨은참조자")
    private ArrayList<String> embcc = new ArrayList<String>();

    @Schema(description = "첨부파일")
    private ArrayList<FileVO> files = new ArrayList<FileVO>();

}
