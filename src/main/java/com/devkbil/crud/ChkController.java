package com.devkbil.crud;

import com.devkbil.common.SearchVO;
import com.devkbil.etc.EtcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ChkController {
    
    static final Logger LOGGER = LoggerFactory.getLogger(CrudController.class);
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
