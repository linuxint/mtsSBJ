package com.devkbil.mtssbj.mail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@ApiModel(value = "메일정보 : MailInfoVO", description = "MailInfoVO")
@XmlRootElement(name = "MailInfoVO")
@XmlType(propOrder = {"emino", "emiimap", "emiimapport", "emismtp", "emismtpport", "emiuser", "emipw", "userno", "usernm"})
@Getter
@Setter
public class MailInfoVO {
    
    @ApiModelProperty(value = "메일정보번호")
    private String emino; // 메일정보번호
    @ApiModelProperty(value = "IMAP서버주소")
    private String emiimap; // IMAP서버주소
    @ApiModelProperty(value = "IMAP서버포트")
    private String emiimapport; // IMAP서버포트
    @ApiModelProperty(value = "SMTP 서버주소")
    private String emismtp; // SMTP 서버주소
    @ApiModelProperty(value = "SMTP 서버포트")
    private String emismtpport; // SMTP 서버포트
    @ApiModelProperty(value = "계정")
    private String emiuser; // 계정
    @ApiModelProperty(value = "비밀번호")
    private String emipw; // 비밀번호
    @ApiModelProperty(value = "사용자번호")
    private String userno; // 사용자번호
    @ApiModelProperty(value = "사용자명")
    private String usernm; // 사용자명
    
}