package com.devkbil.mtssbj.etc;

import com.devkbil.mtssbj.admin.organ.DeptService;
import com.devkbil.mtssbj.admin.organ.UserService;
import com.devkbil.mtssbj.search.SearchVO;
import com.devkbil.mtssbj.common.tree.TreeMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PopUserController {
    
    @Autowired
    private DeptService deptService;
    
    @Autowired
    private UserService userService;
    
    /**
     * 부서리스트.
     */
    @RequestMapping(value = "/popupDept")
    public String popupDept(ModelMap modelMap) {
        List<?> listview = deptService.selectDepartment();
        
        TreeMaker tm = new TreeMaker();
        String treeStr = tm.makeTreeByHierarchy(listview);
        
        modelMap.addAttribute("treeStr", treeStr);
        
        return "etc/popupDept";
    }
    
    /**
     * 부서리스트 for 사용자.
     */
    @RequestMapping(value = "/popupUser")
    public String popupUser(ModelMap modelMap) {
        List<?> listview = deptService.selectDepartment();
        
        TreeMaker tm = new TreeMaker();
        String treeStr = tm.makeTreeByHierarchy(listview);
        
        modelMap.addAttribute("treeStr", treeStr);
        
        return "etc/popupUser";
    }
    
    /**
     * 선택된 부서의 User 리스트.
     */
    @RequestMapping(value = "/popupUsersByDept")
    public String popupUsersByDept(HttpServletRequest request, SearchVO searchVO, ModelMap modelMap) {
        String deptno = request.getParameter("deptno");
        searchVO.setSearchExt1(deptno);
        
        List<?> listview = userService.selectUserListWithDept(searchVO);
        
        modelMap.addAttribute("listview", listview);
        
        return "etc/popupUsersByDept";
    }
    
    /**
     * 부서리스트 for 사용자들.
     */
    @RequestMapping(value = "/popupUsers")
    public String popupUsers(ModelMap modelMap) {
        popupUser(modelMap);
        
        return "etc/popupUsers";
    }
    
    /**
     * 부서리스트 for 사용자들 - 결재 경로 지정용.
     */
    @RequestMapping(value = "/popupUsers4SignPath")
    public String popupUsers4SignPath(ModelMap modelMap) {
        popupUser(modelMap);
        
        return "etc/popupUsers4SignPath";
    }
    
    /**
     * User 리스트  for 사용자들.
     */
    @RequestMapping(value = "/popupUsers4Users")
    public String popupUsers4Users(HttpServletRequest request, SearchVO searchVO, ModelMap modelMap) {
        popupUsersByDept(request, searchVO, modelMap);
        
        return "etc/popupUsers4Users";
    }
}
