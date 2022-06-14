package com.devkbil.mail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.servlet.http.HttpSession;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "메일수신 : ImportMail", description = "ImportMail")
@XmlRootElement(name = "ClassCodeVO")
@XmlType(propOrder = {"mailService", "userno", "session"})
@Getter
@Setter
public class ImportMail implements Runnable {
    @ApiModelProperty(value = "메일서비스")
    private MailService mailService;
    @ApiModelProperty(value = "사용자")
    private String userno;
    @ApiModelProperty(value = "세션")
    private HttpSession session;
    
    public ImportMail(MailService mailService, String userno, HttpSession session) {
        this.mailService = mailService;
        this.userno = userno;
        this.session = session;
    }
    
    public void run() {
        Imap mail = new Imap();
        List<?> list = mailService.selectMailInfoList(userno);
        try {
            for (int i = 0; i < list.size(); i++) {
                MailInfoVO mivo = (MailInfoVO) list.get(i);
                String lastdate = mailService.selectLastMail(mivo.getEmino());
                
                mail.connect(mivo.getEmiimap(), mivo.getEmiuser(), mivo.getEmipw());
                Integer total = mail.patchMessage(lastdate);
                
                int cnt = 0;
                while (cnt < total) {
                    ArrayList<MailVO> msgList = mail.getMail(cnt);
                    mailService.insertMails(msgList, userno, mivo.getEmino());
                    cnt += msgList.size();
                }
                mail.disconnect();
            }
        } catch (Exception e) {
        }
        session.removeAttribute("mail"); // 중복 실행 방지
    }
    
}
