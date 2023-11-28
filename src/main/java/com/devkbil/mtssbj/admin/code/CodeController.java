package com.devkbil.mtssbj.admin.code;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.devkbil.mtssbj.search.SearchVO;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CodeController {

    @Autowired
    private CodeService codeService;

    /**
     * 공통 코드 리스트.
     */
    @RequestMapping(value = "/adCodeList")
    public String codeList(HttpServletRequest request, SearchVO searchVO, ModelMap modelMap) {
        searchVO.pageCalculate(codeService.selectCodeCount(searchVO)); // startRow, endRow
        List<?> listview = codeService.selectCodeList(searchVO);

        modelMap.addAttribute("searchVO", searchVO);
        modelMap.addAttribute("listview", listview);

        return "admin/code/CodeList";
    }

    /**
     * 공통 코드 쓰기.
     */
    @RequestMapping(value = "/adCodeForm")
    public String codeForm(HttpServletRequest request, CodeVO codeInfo, ModelMap modelMap) {
        if (codeInfo.getClassno() != null) {
            codeInfo = codeService.selectCodeOne(codeInfo);

            modelMap.addAttribute("codeInfo", codeInfo);
            modelMap.addAttribute("codeFormType", "U");
        }

        return "admin/code/CodeForm";
    }

    /**
     * 공통 코드 저장.
     */
    @RequestMapping(value = "/adCodeSave")
    public String codeSave(HttpServletRequest request, CodeVO codeInfo, ModelMap modelMap) {
        String codeFormType = request.getParameter("codeFormType");

        if (!"U".equals(codeFormType)) { // insert
            CodeVO cvo = codeService.selectCodeOne(codeInfo);
            if (cvo != null) {
                modelMap.addAttribute("msg", "이미 사용중인 코드입니다.");
                return "common/message";
            }
        }
        codeService.insertCode(codeFormType, codeInfo);

        return "redirect:/adCodeList";
    }

    /**
     * 공통 코드 읽기.
     */
    @RequestMapping(value = "/adCodeRead")
    public String codeRead(HttpServletRequest request, CodeVO codeVO, ModelMap modelMap) {

        CodeVO codeInfo = codeService.selectCodeOne(codeVO);

        modelMap.addAttribute("codeInfo", codeInfo);

        return "admin/code/CodeRead";
    }

    /**
     * 공통 코드 삭제.
     */
    @RequestMapping(value = "/adCodeDelete")
    public String codeDelete(HttpServletRequest request, CodeVO codeVO) {

        codeService.deleteCodeOne(codeVO);

        return "redirect:/adCodeList";
    }

}
