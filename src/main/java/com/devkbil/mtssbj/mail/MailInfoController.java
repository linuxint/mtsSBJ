package com.devkbil.mtssbj.mail;

import com.devkbil.mtssbj.search.SearchVO;
import com.devkbil.mtssbj.etc.EtcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MailInfoController {
    
    static final Logger LOGGER = LoggerFactory.getLogger(MailInfoController.class);
    @Autowired
    private MailService mailService;
    @Autowired
    private EtcService etcService;
    
    /**
     * 리스트.
     */
    @RequestMapping(value = "/mailInfoList")
    public String mailInfoList(HttpServletRequest request, SearchVO searchVO, ModelMap modelMap) {
        // 페이지 공통: alert
        String userno = request.getSession().getAttribute("userno").toString();
        
        etcService.setCommonAttribute(userno, modelMap);
        
        List<?> listview = mailService.selectMailInfoList(userno);
        
        modelMap.addAttribute("searchVO", searchVO);
        modelMap.addAttribute("listview", listview);
        
        return "mail/MailInfoList";
    }
    
    /**
     * 쓰기.
     */
    @RequestMapping(value = "/mailInfoForm")
    public String mailInfoForm(HttpServletRequest request, MailInfoVO mailInfoInfo, ModelMap modelMap) {
        // 페이지 공통: alert
        String userno = request.getSession().getAttribute("userno").toString();
        
        etcService.setCommonAttribute(userno, modelMap);
        
        // 
        if(mailInfoInfo.getEmino() != null) {
            mailInfoInfo = mailService.selectMailInfoOne(mailInfoInfo);
            
            modelMap.addAttribute("mailInfoInfo", mailInfoInfo);
        }
        
        return "mail/MailInfoForm";
    }
    
    /**
     * 저장.
     */
    @RequestMapping(value = "/mailInfoSave")
    public String mailInfoSave(HttpServletRequest request, MailInfoVO mailInfoInfo, ModelMap modelMap) {
        HttpSession session = request.getSession();
        
        if(session.getAttribute("mail") != null) {
            modelMap.addAttribute("msg", "이전에 등록한 메일 서버에서 메일을 가지고 오는 중입니다. \n 잠시 뒤에 다시 등록해 주세요.");
            return "common/message";
        }
        
        String userno = request.getSession().getAttribute("userno").toString();
        mailInfoInfo.setUserno(userno);
        
        try {
            Imap mail = new Imap();
            mail.connect(mailInfoInfo.getEmiimap(), mailInfoInfo.getEmiuser(), mailInfoInfo.getEmipw());
            mail.disconnect();
        } catch (Exception e) {
            modelMap.addAttribute("msg", "서버에 접속할 수 없습니다.");
            return "common/message";
        }
        
        mailService.insertMailInfo(mailInfoInfo);
        
        Thread t = new Thread(new ImportMail(mailService, userno, session));
        t.start();
        
        return "redirect:/mailInfoList";
    }
    
    /**
     * 삭제.
     */
    @RequestMapping(value = "/mailInfoDelete")
    public String mailInfoDelete(HttpServletRequest request, MailInfoVO mailInfoVO) {
        
        mailService.deleteMailInfo(mailInfoVO);
        
        return "redirect:/mailInfoList";
    }
    
}
