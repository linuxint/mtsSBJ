package com.devkbil.mtssbj.crud;

import com.devkbil.mtssbj.common.SearchVO;
import com.devkbil.mtssbj.etc.EtcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CrudController {
    
    static final Logger LOGGER = LoggerFactory.getLogger(CrudController.class);
    @Autowired
    private CrudService crudService;
    @Autowired
    private EtcService etcService;
    
    /**
     * 리스트.
     */
    @RequestMapping(value = "/crudList")
    public String crudList(HttpServletRequest request, SearchVO searchVO, ModelMap modelMap) {
        // 페이지 공통: alert
        String userno = request.getSession().getAttribute("userno").toString();
        
        etcService.setCommonAttribute(userno, modelMap);
        
        // CRUD 관련
        searchVO.pageCalculate(crudService.selectCrudCount(searchVO)); // startRow, endRow
        List<?> listview = crudService.selectCrudList(searchVO);
        
        modelMap.addAttribute("searchVO", searchVO);
        modelMap.addAttribute("listview", listview);
        
        return "crud/CrudList";
    }
    
    /**
     * 쓰기.
     */
    @RequestMapping(value = "/crudForm")
    public String crudForm(HttpServletRequest request, CrudVO crudInfo, ModelMap modelMap) {
        // 페이지 공통: alert
        String userno = request.getSession().getAttribute("userno").toString();
        
        etcService.setCommonAttribute(userno, modelMap);
        
        // CRUD 관련
        if(crudInfo.getCrno() != null) {
            crudInfo = crudService.selectCrudOne(crudInfo);
            
            modelMap.addAttribute("crudInfo", crudInfo);
        }
        
        return "crud/CrudForm";
    }
    
    /**
     * 저장.
     */
    @RequestMapping(value = "/crudSave")
    public String crudSave(HttpServletRequest request, CrudVO crudInfo, ModelMap modelMap) {
        String userno = request.getSession().getAttribute("userno").toString();
        crudInfo.setUserno(userno);
        
        crudService.insertCrud(crudInfo);
        
        return "redirect:/crudList";
    }
    
    /**
     * 읽기.
     */
    @RequestMapping(value = "/crudRead")
    public String crudRead(HttpServletRequest request, CrudVO crudVO, ModelMap modelMap) {
        // 페이지 공통: alert
        String userno = request.getSession().getAttribute("userno").toString();
        
        etcService.setCommonAttribute(userno, modelMap);
        
        // CRUD 관련
        
        CrudVO crudInfo = crudService.selectCrudOne(crudVO);
        
        modelMap.addAttribute("crudInfo", crudInfo);
        
        return "crud/CrudRead";
    }
    
    /**
     * 삭제.
     */
    @RequestMapping(value = "/crudDelete")
    public String crudDelete(HttpServletRequest request, CrudVO crudVO) {
        
        crudService.deleteCrud(crudVO);
        
        return "redirect:/crudList";
    }
    
}
