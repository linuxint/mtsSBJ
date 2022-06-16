package com.devkbil.mail;

import com.devkbil.common.FileVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;

@ApiModel(value = "메일 : MailVO", description = "MailVO")
@XmlRootElement(name = "MailVO")
@XmlType(propOrder = {"emno", "emtype", "emfrom", "emsubject", "emcontents", "entrydate", "userno", "usernm", "emino", "strTo", "strCc", "strBcc", "emto", "emcc", "embcc", "files"})
@Getter
@Setter
public class MailVO {
    
    @ApiModelProperty(value = "메일서비스")
    private String emno;
    
    @ApiModelProperty(value = " 받은 / 보낸 메일")
    private String emtype;    // 받은 / 보낸 메일
    
    @ApiModelProperty(value = "발신인")
    private String emfrom; //발신인
    
    @ApiModelProperty(value = "제목")
    private String emsubject; // 제목
    
    @ApiModelProperty(value = "내용")
    private String emcontents; // 내용
    
    @ApiModelProperty(value = "작성일")
    private String entrydate; // 작성일
    
    @ApiModelProperty(value = "사용자번호")
    private String userno; // 사용자번호
    
    @ApiModelProperty(value = "사용자명")
    private String usernm; // 사용자명
    
    @ApiModelProperty(value = "메일정보번호")
    private String emino; // 메일정보번호
    
    @ApiModelProperty(value = "수신자")
    private String strTo; // 수신자
    
    @ApiModelProperty(value = "참조자")
    private String strCc; // 참조자
    
    @ApiModelProperty(value = "숨은참조자")
    private String strBcc; // 숨은참조자
    
    @ApiModelProperty(value = "수신인")
    private ArrayList<String> emto = new ArrayList<String>();
    @ApiModelProperty(value = "참조자")
    private ArrayList<String> emcc = new ArrayList<String>();
    @ApiModelProperty(value = "숨은참조자")
    private ArrayList<String> embcc = new ArrayList<String>();
    
    @ApiModelProperty(value = "첨부파일")
    private ArrayList<FileVO> files = new ArrayList<FileVO>();
    
}