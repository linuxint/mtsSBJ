package com.devkbil.mtssbj.admin.sign;

import com.devkbil.mtssbj.config.security.AdminAuthorize;
import com.devkbil.mtssbj.etc.EtcService;
import com.devkbil.mtssbj.search.SearchVO;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@AdminAuthorize
public class SignDocController {

    @Autowired
    private SignDocService signDocService;
    @Autowired
    private EtcService etcService;

    /**
     * 리스트.
     */
    @RequestMapping(value = "/adSignDocTypeList")
    public String signDocTypeList(HttpServletRequest request, SearchVO searchVO, ModelMap modelMap) {
        // 페이지 공통: alert
        String userno = request.getSession().getAttribute("userno").toString();

        etcService.setCommonAttribute(userno, modelMap);

        searchVO.pageCalculate(signDocService.selectSignDocTypeCount(searchVO)); // startRow, endRow
        List<?> listview = signDocService.selectSignDocTypeList(searchVO);

        modelMap.addAttribute("searchVO", searchVO);
        modelMap.addAttribute("listview", listview);

        return "admin/sign/SignDocTypeList";
    }

    /**
     * 쓰기.
     */
    @RequestMapping(value = "/adSignDocTypeForm")
    public String signDocTypeForm(HttpServletRequest request, SignDocTypeVO signInfo, ModelMap modelMap) {
        // 페이지 공통: alert
        String userno = request.getSession().getAttribute("userno").toString();

        etcService.setCommonAttribute(userno, modelMap);

        // 개별 작업
        if (signInfo.getDtno() != null) {
            signInfo = signDocService.selectSignDocTypeOne(signInfo.getDtno());

            modelMap.addAttribute("signInfo", signInfo);
        }

        return "admin/sign/SignDocTypeForm";
    }

    /**
     * 저장.
     */
    @RequestMapping(value = "/adSignDocTypeSave")
    public String signDocTypeSave(HttpServletRequest request, SignDocTypeVO signInfo, ModelMap modelMap) {

        signDocService.insertSignDocType(signInfo);

        return "redirect:/adSignDocTypeList";
    }

    /**
     * 삭제.
     */
    @RequestMapping(value = "/adSignDocTypeDelete")
    public String signDocTypeDelete(HttpServletRequest request, SignDocTypeVO signVO) {

        signDocService.deleteSignDocType(signVO);

        return "redirect:/adSignDocTypeList";
    }

}
