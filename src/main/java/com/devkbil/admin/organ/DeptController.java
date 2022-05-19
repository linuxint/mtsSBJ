package com.devkbil.admin.organ;

import com.devkbil.common.TreeMaker;
import com.devkbil.common.UtilEtc;
import com.devkbil.etc.EtcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class DeptController {
    
    @Autowired
    private DeptService deptService;
    
    @Autowired
    private EtcService etcService;
    
    /**
     * 리스트.
     */
    @RequestMapping(value = "/adDepartment")
    public String department(HttpServletRequest request, ModelMap modelMap) {
        String userno = request.getSession().getAttribute("userno").toString();
        
        etcService.setCommonAttribute(userno, modelMap);
        
        List<?> listview = deptService.selectDepartment();
        
        TreeMaker tm = new TreeMaker();
        String treeStr = tm.makeTreeByHierarchy(listview);
        
        modelMap.addAttribute("treeStr", treeStr);
        
        return "admin/organ/Department";
    }
    
    /**
     * 부서 등록.
     */
    @RequestMapping(value = "/adDepartmentSave")
    public void departmentSave(HttpServletResponse response, DepartmentVO deptInfo) {
        
        deptService.insertDepartment(deptInfo);
        
        UtilEtc.responseJsonValue(response, deptInfo);
    }
    
    /**
     * 부서 정보(하나).
     */
    @RequestMapping(value = "/adDepartmentRead")
    public void departmentRead(HttpServletRequest request, HttpServletResponse response) {
        
        String deptno = request.getParameter("deptno");
        
        DepartmentVO deptInfo = deptService.selectDepartmentOne(deptno);
        
        UtilEtc.responseJsonValue(response, deptInfo);
    }
    
    /**
     * 부서 삭제.
     */
    @RequestMapping(value = "/adDepartmentDelete")
    public void departmentDelete(HttpServletRequest request, HttpServletResponse response) {
        
        String deptno = request.getParameter("deptno");
        
        deptService.deleteDepartment(deptno);
        
        UtilEtc.responseJsonValue(response, "OK");
    }
    
}
