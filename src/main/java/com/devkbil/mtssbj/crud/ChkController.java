package com.devkbil.mtssbj.crud;

import com.devkbil.mtssbj.search.SearchVO;
import com.devkbil.mtssbj.etc.EtcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
public class ChkController {
    
    @Autowired
    private CrudService crudService;
    @Autowired
    private EtcService etcService;
    
    /**
     * 리스트.
     */
    @RequestMapping(value = "/chkList")
    public String chkList(HttpServletRequest request, SearchVO searchVO, ModelMap modelMap) {
        // 페이지 공통: alert
        String userno = request.getSession().getAttribute("userno").toString();
        
        etcService.setCommonAttribute(userno, modelMap);
        
        // CRUD 관련
        searchVO.pageCalculate(crudService.selectCrudCount(searchVO)); // startRow, endRow
        List<?> listview = crudService.selectCrudList(searchVO);
        
        modelMap.addAttribute("searchVO", searchVO);
        modelMap.addAttribute("listview", listview);
        
        return "crud/ChkList";
    }
    
    /**
     * 선택된 행 삭제.
     */
    @RequestMapping(value = "/chkDelete")
    public String chkDelete(HttpServletRequest request, String[] checkRow) {
        
        crudService.deleteChk(checkRow);
        
        return "redirect:/chkList";
    }
    
}
