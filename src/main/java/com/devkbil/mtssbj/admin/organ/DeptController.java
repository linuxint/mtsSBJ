package com.devkbil.mtssbj.admin.organ;

import com.devkbil.mtssbj.common.tree.TreeMaker;
import com.devkbil.mtssbj.common.util.UtilEtc;
import com.devkbil.mtssbj.config.security.AdminAuthorize;
import com.devkbil.mtssbj.etc.EtcService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@AdminAuthorize
public class DeptController {

    @Autowired
    private DeptService deptService;

    @Autowired
    private EtcService etcService;

    /**
     * 리스트.
     */
    @RequestMapping(value = "/adDept")
    public String dept(HttpServletRequest request, ModelMap modelMap) {
        String userno = request.getSession().getAttribute("userno").toString();

        etcService.setCommonAttribute(userno, modelMap);

        List<?> listview = deptService.selectDept();

        TreeMaker tm = new TreeMaker();
        String treeStr = tm.makeTreeByHierarchy(listview);

        modelMap.addAttribute("treeStr", treeStr);

        return "admin/organ/Dept";
    }

    /**
     * 부서 등록.
     */
    @RequestMapping(value = "/adDeptSave")
    public void deptSave(HttpServletResponse response, DeptVO deptInfo) {

        deptService.insertDept(deptInfo);

        UtilEtc.responseJsonValue(response, deptInfo);
    }

    /**
     * 부서 정보(하나).
     */
    @RequestMapping(value = "/adDeptRead")
    public void deptRead(HttpServletRequest request, HttpServletResponse response) {

        String deptno = request.getParameter("deptno");

        DeptVO deptInfo = deptService.selectDeptOne(deptno);

        UtilEtc.responseJsonValue(response, deptInfo);
    }

    /**
     * 부서 삭제.
     */
    @RequestMapping(value = "/adDeptDelete")
    public void deptDelete(HttpServletRequest request, HttpServletResponse response) {

        String deptno = request.getParameter("deptno");

        deptService.deleteDept(deptno);

        UtilEtc.responseJsonValue(response, "OK");
    }

}
