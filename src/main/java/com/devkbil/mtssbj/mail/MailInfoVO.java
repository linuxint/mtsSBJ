package com.devkbil.mtssbj.mail;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "메일정보 : MailInfoVO")
@XmlRootElement(name = "MailInfoVO")
@XmlType(propOrder = {"emino", "emiimap", "emiimapport", "emismtp", "emismtpport", "emiuser", "emipw", "userno",
        "usernm"})
@Getter
@Setter
public class MailInfoVO {

    @Schema(description = "메일정보번호")
    private String emino; // 메일정보번호
    @Schema(description = "IMAP서버주소")
    private String emiimap; // IMAP서버주소
    @Schema(description = "IMAP서버포트")
    private String emiimapport; // IMAP서버포트
    @Schema(description = "SMTP 서버주소")
    private String emismtp; // SMTP 서버주소
    @Schema(description = "SMTP 서버포트")
    private String emismtpport; // SMTP 서버포트
    @Schema(description = "계정")
    private String emiuser; // 계정
    @Schema(description = "비밀번호")
    private String emipw; // 비밀번호
    @Schema(description = "사용자번호")
    private String userno; // 사용자번호
    @Schema(description = "사용자명")
    private String usernm; // 사용자명

}
