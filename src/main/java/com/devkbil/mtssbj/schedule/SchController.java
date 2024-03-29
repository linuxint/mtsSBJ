package com.devkbil.mtssbj.schedule;

import static org.springframework.http.HttpHeaders.*;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.devkbil.mtssbj.common.util.DateUtil;
import com.devkbil.mtssbj.etc.EtcService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@Tag(name = "SchController", description = "일정관리-")
public class SchController {

    @Autowired
    private SchService schService;
    @Autowired
    private EtcService etcService;

    /**
     * 리스트.
     */
    @Operation(summary = "일정관리-일정목록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The request has succeeded.", headers = @Header(name = AUTHORIZATION, description = "Access Token")),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @RequestMapping(value = "/schList")
    public String schList(HttpServletRequest request, MonthVO searchVO, ModelMap modelMap) {
        // 페이지 공통: alert
        String userno = request.getSession().getAttribute("userno").toString();

        etcService.setCommonAttribute(userno, modelMap);

        if (searchVO.getYear() == null || "".equals(searchVO.getYear())) {
            Date today = DateUtil.getToday();
            searchVO.setYear(DateUtil.getYear(today).toString());
            searchVO.setMonth(DateUtil.getMonth(today).toString());
        }
        if ("0".equals(searchVO.getMonth()) || "13".equals(searchVO.getMonth())) {
            searchVO = DateUtil.monthValid(searchVO);
        }

        Integer dayofweek = DateUtil.getDayOfWeek(DateUtil.str2Date(searchVO.getYear() + "-" + searchVO.getMonth() + "-01"));

        List<?> listview = schService.selectCalendar(searchVO, userno);

        modelMap.addAttribute("listview", listview);
        modelMap.addAttribute("searchVO", searchVO);
        modelMap.addAttribute("dayofweek", dayofweek);

        return "schedule/SchList";
    }

    /**
     * 쓰기.
     */
    @Operation(summary = "일정관리-일정입력")
    @RequestMapping(value = "/schForm")
    public String schForm(HttpServletRequest request, SchVO schInfo, ModelMap modelMap) {
        // 페이지 공통: alert
        String userno = request.getSession().getAttribute("userno").toString();

        etcService.setCommonAttribute(userno, modelMap);

        // 
        if (schInfo.getSsno() != null) {
            schInfo = schService.selectSchOne(schInfo);

        } else {
            schInfo.setSstype("1");
            schInfo.setSsisopen("Y");

            String cddate = request.getParameter("cddate");
            if (cddate == null || "".equals(cddate)) {
                cddate = DateUtil.date2Str(DateUtil.getToday());
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
    @Operation(summary = "일정관리-일정저장")
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
    @Operation(summary = "일정관리-일정읽기Ajax")
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
    @Operation(summary = "일정관리-일정읽기Request")
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
    @Operation(summary = "일정관리-일정삭제")
    @RequestMapping(value = "/schDelete")
    public String schDelete(HttpServletRequest request, SchVO schVO) {

        schService.deleteSch(schVO);

        return "redirect:/schList";
    }

}
