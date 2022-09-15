package com.devkbil.mtssbj.main;

import com.devkbil.mtssbj.common.DateVO;
import com.devkbil.mtssbj.common.Field3VO;
import com.devkbil.mtssbj.common.SearchVO;
import com.devkbil.mtssbj.common.Util4calen;
import com.devkbil.mtssbj.etc.EtcService;
import com.devkbil.mtssbj.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class IndexController {
    @Autowired
    private IndexService indexService;
    @Autowired
    private EtcService etcService;
    @Autowired
    private ProjectService projectService;
    
    /**
     * main page.
     */
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, SearchVO searchVO, ModelMap modelMap) {
        String userno = request.getSession().getAttribute("userno").toString();
        etcService.setCommonAttribute(userno, modelMap);
        
        Date today = Util4calen.getToday();
        
        calCalen(userno, today, modelMap);
        
        if(!"".equals(searchVO.getSearchKeyword())) {
            searchVO.setSearchType("prtitle");
        }
        searchVO.setDisplayRowCount(12);
        searchVO.pageCalculate(projectService.selectProjectCount(searchVO)); // startRow, endRow
        
        List<?> projectlistview = projectService.selectProjectList(searchVO);
        
        List<?> listview = indexService.selectRecentNews();
        List<?> noticeList = indexService.selectNoticeListTop5();
        List<?> listtime = indexService.selectTimeLine();
        
        modelMap.addAttribute("searchVO", searchVO);
        modelMap.addAttribute("projectlistview", projectlistview);
        
        modelMap.addAttribute("listview", listview);
        modelMap.addAttribute("noticeList", noticeList);
        modelMap.addAttribute("listtime", listtime);
        
        return "main/index";
    }
    
    /**
     * week calendar in main page.
     * Ajax.
     */
    @RequestMapping(value = "/moveDate")
    public String moveDate(HttpServletRequest request, ModelMap modelMap) {
        String userno = request.getSession().getAttribute("userno").toString();
        String date = request.getParameter("date");
        
        Date today = Util4calen.getToday(date);
        
        calCalen(userno, today, modelMap);
        
        return "main/indexCalen";
    }
    
    private String calCalen(String userno, Date targetDay, ModelMap modelMap) {
        List<DateVO> calenList = new ArrayList<DateVO>();
        
        Date today = Util4calen.getToday();
        int month = Util4calen.getMonth(targetDay);
        int week = Util4calen.getWeekOfMonth(targetDay);
        
        Date fweek = Util4calen.getFirstOfWeek(targetDay);
        Date lweek = Util4calen.getLastOfWeek(targetDay);
        Date preWeek = Util4calen.dateAdd(fweek, -1);
        Date nextWeek = Util4calen.dateAdd(lweek, 1);
        
        Field3VO fld = new Field3VO();
        fld.setField1(userno);
        
        while (fweek.compareTo(lweek) <= 0) {
            DateVO dvo = Util4calen.date2VO(fweek);
            dvo.setIstoday(Util4calen.dateDiff(fweek, today) == 0);
            dvo.setDate(Util4calen.date2Str(fweek));
            
            fld.setField2(dvo.getDate());
            dvo.setList(indexService.selectSchList4Calen(fld));
            
            calenList.add(dvo);
            
            fweek = Util4calen.dateAdd(fweek, 1);
        }
        
        modelMap.addAttribute("month", month);
        modelMap.addAttribute("week", week);
        modelMap.addAttribute("calenList", calenList);
        modelMap.addAttribute("preWeek", Util4calen.date2Str(preWeek));
        modelMap.addAttribute("nextWeek", Util4calen.date2Str(nextWeek));
        
        return "main/index";
    }
    
}
