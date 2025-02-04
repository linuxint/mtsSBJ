package com.devkbil.mtssbj.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.devkbil.mtssbj.common.ExtFieldVO;
import com.devkbil.mtssbj.common.util.DateUtil;
import com.devkbil.mtssbj.etc.EtcService;
import com.devkbil.mtssbj.project.ProjectService;
import com.devkbil.mtssbj.schedule.DateVO;
import com.devkbil.mtssbj.search.SearchVO;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private IndexService indexService;
    @Autowired
    private EtcService etcService;
    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = "/thymeleaftest")
    public String test(Model model) {
        model.addAttribute("name", "kbil test");
        return "thymeleaf/thymeleaftest";
    }

    /**
     * main page.
     */
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, SearchVO searchVO, ModelMap modelMap) {
        String userno = request.getSession().getAttribute("userno").toString();
        etcService.setCommonAttribute(userno, modelMap);

        Date today = DateUtil.getToday();

        calCalen(userno, today, modelMap);

        if (!"".equals(searchVO.getSearchKeyword())) {
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

        Date today = DateUtil.stringToDate(date);

        calCalen(userno, today, modelMap);

        return "main/indexCalen";
    }

    private String calCalen(String userno, Date targetDay, ModelMap modelMap) {
        List<DateVO> calenList = new ArrayList<DateVO>();

        Date today = DateUtil.getToday();
        int month = DateUtil.getMonth(targetDay);
        int week = DateUtil.getWeekOfMonth(targetDay);

        Date fweek = DateUtil.getFirstOfWeek(targetDay);
        Date lweek = DateUtil.getLastOfWeek(targetDay);
        Date preWeek = DateUtil.dateAdd(fweek, -1);
        Date nextWeek = DateUtil.dateAdd(lweek, 1);

        ExtFieldVO fld = new ExtFieldVO();
        fld.setField1(userno);

        while (fweek.compareTo(lweek) <= 0) {
            DateVO dvo = DateUtil.date2VO(fweek);
            dvo.setIstoday(DateUtil.dateDiff(fweek, today) == 0);
            dvo.setDate(DateUtil.date2Str(fweek));

            fld.setField2(dvo.getDate());
            dvo.setList(indexService.selectSchList4Calen(fld));

            calenList.add(dvo);

            fweek = DateUtil.dateAdd(fweek, 1);
        }

        modelMap.addAttribute("month", month);
        modelMap.addAttribute("week", week);
        modelMap.addAttribute("calenList", calenList);
        modelMap.addAttribute("preWeek", DateUtil.date2Str(preWeek));
        modelMap.addAttribute("nextWeek", DateUtil.date2Str(nextWeek));

        return "main/index";
    }

}
