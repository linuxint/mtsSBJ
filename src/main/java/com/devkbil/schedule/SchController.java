package com.devkbil.schedule;

import com.devkbil.common.Util4calen;
import com.devkbil.etc.EtcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
public class SchController {
    
    static final Logger LOGGER = LoggerFactory.getLogger(SchController.class);
    @Autowired
    private SchService schService;
    @Autowired
    private EtcService etcService;
    
    /**
     * 리스트.
     */
    @RequestMapping(value = "/schList")
    public String schList(HttpServletRequest request, MonthVO searchVO, ModelMap modelMap) {
        // 페이지 공통: alert
        String userno = request.getSession().getAttribute("userno").toString();
        
        etcService.setCommonAttribute(userno, modelMap);
        
        // 
        if(searchVO.getYear() == null || "".equals(searchVO.getYear())) {
            Date today = Util4calen.getToday();
            searchVO.setYear(Util4calen.getYear(today).toString());
            searchVO.setMonth(Util4calen.getMonth(today).toString());
        }
        Integer dayofweek = Util4calen.getDayOfWeek(Util4calen.str2Date(searchVO.getYear() + "-" + searchVO.getMonth() + "-01"));
        
        List<?> listview = schService.selectCalendar(searchVO, userno);
        
        modelMap.addAttribute("listview", listview);
        modelMap.addAttribute("searchVO", searchVO);
        modelMap.addAttribute("dayofweek", dayofweek);
        
        return "schedule/SchList";
    }
    
    /**
     * 쓰기.
     */
    @RequestMapping(value = "/schForm")
    public String schForm(HttpServletRequest request, SchVO schInfo, ModelMap modelMap) {
        // 페이지 공통: alert
        String userno = request.getSession().getAttribute("userno").toString();
        
        etcService.setCommonAttribute(userno, modelMap);
        
        // 
        if(schInfo.getSsno() != null) {
            schInfo = schService.selectSchOne(schInfo);
            
        } else {
            schInfo.setSstype("1");
            schInfo.setSsisopen("Y");
            
            String cddate = request.getParameter("cddate");
            if(cddate == null || "".equals(cddate)) {
                cddate = Util4calen.date2Str(Util4calen.getToday());
            }
            schInfo.setSsstartdate(cddate);
            schInfo.setSsstarthour("09");
            schInfo.setSsenddate(cddate);
            schInfo.setSsendhour("18");
        }
        modelMap.addAttribute("schInfo", schInfo);
        
        List<?> sstypelist = etcService.selectClassCode("4");
        modelMap.addAttribute("sstypelist", sstypelist);
        
        return "schedule/SchForm";
    }
    
    /**
     * 저장.
     */
    @RequestMapping(value = "/schSave")
    public String schSave(HttpServletRequest request, SchVO schInfo, ModelMap modelMap) {
        String userno = request.getSession().getAttribute("userno").toString();
        schInfo.setUserno(userno);
        
        schService.insertSch(schInfo);
        
        return "redirect:/schList";
    }
    
    /**
     * 읽기.
     */
    @RequestMapping(value = "/schRead4Ajax")
    public String schRead4Ajax(HttpServletRequest request, SchVO schVO, String cddate, ModelMap modelMap) {
        SchVO schInfo = schService.selectSchOne4Read(schVO);
        
        modelMap.addAttribute("schInfo", schInfo);
        modelMap.addAttribute("cddate", cddate);
        
        return "schedule/SchRead4Ajax";
    }
    
    /**
     * 읽기.
     */
    @RequestMapping(value = "/schRead")
    public String schRead(HttpServletRequest request, SchVO schVO, ModelMap modelMap) {
        // 페이지 공통: alert
        String userno = request.getSession().getAttribute("userno").toString();
        
        etcService.setCommonAttribute(userno, modelMap);
        // 
        
        SchVO schInfo = schService.selectSchOne4Read(schVO);
        
        modelMap.addAttribute("schInfo", schInfo);
        
        return "schedule/SchRead";
    }
    
    /**
     * 삭제.
     */
    @RequestMapping(value = "/schDelete")
    public String schDelete(HttpServletRequest request, SchVO schVO) {
        
        schService.deleteSch(schVO);
        
        return "redirect:/schList";
    }
    
}
