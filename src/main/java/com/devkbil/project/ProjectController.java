package com.devkbil.project;

import com.devkbil.common.SearchVO;
import com.devkbil.common.Util4calen;
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
public class ProjectController {
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private EtcService etcService;
    
    static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);
    
    /**
     * 리스트.
     */
    @RequestMapping(value = "/projectList")
    public String projectList(HttpServletRequest request, SearchVO searchVO, ModelMap modelMap) {
        String userno = request.getSession().getAttribute("userno").toString();
        
        Integer alertcount = etcService.selectAlertCount(userno);
        modelMap.addAttribute("alertcount", alertcount);
        
        searchVO.pageCalculate(projectService.selectProjectCount(searchVO)); // startRow, endRow
        List<?> listview = projectService.selectProjectList(searchVO);
        
        modelMap.addAttribute("searchVO", searchVO);
        modelMap.addAttribute("listview", listview);
        
        return "project/ProjectList";
    }
    
    /**
     * 리스트 4 Ajax.
     */
    @RequestMapping(value = "/projectList4Ajax")
    public String projectList4Ajax(HttpServletRequest request, SearchVO searchVO, ModelMap modelMap) {
        searchVO.pageCalculate(projectService.selectProjectCount(searchVO)); // startRow, endRow
        List<?> listview = projectService.selectProjectList(searchVO);
        
        modelMap.addAttribute("searchVO", searchVO);
        modelMap.addAttribute("listview", listview);
        
        return "project/ProjectList4Ajax";
    }
    
    /**
     * 쓰기.
     */
    @RequestMapping(value = "/projectForm")
    public String projectForm(HttpServletRequest request, ModelMap modelMap) {
        String userno = request.getSession().getAttribute("userno").toString();
        
        Integer alertcount = etcService.selectAlertCount(userno);
        modelMap.addAttribute("alertcount", alertcount);
        
        String prno = request.getParameter("prno");
        
        ProjectVO projectInfo;
        if(prno != null) {
            projectInfo = projectService.selectProjectOne(prno);
        } else {
            String today = Util4calen.date2Str(Util4calen.getToday());
            projectInfo = new ProjectVO();
            projectInfo.setPrstartdate(today);
            projectInfo.setPrenddate(today);
            projectInfo.setPrstatus("0");
        }
        
        modelMap.addAttribute("projectInfo", projectInfo);
        modelMap.addAttribute("prno", prno);
        
        return "project/ProjectForm";
    }
    
    /**
     * 저장.
     */
    @RequestMapping(value = "/projectSave")
    public String projectSave(HttpServletRequest request, ProjectVO projectInfo) {
        String userno = request.getSession().getAttribute("userno").toString();
        projectInfo.setUserno(userno);
        
        if(projectInfo.getPrno() != null && !"".equals(projectInfo.getPrno())) {    // check auth for update
            String chk = projectService.selectProjectAuthChk(projectInfo);
            if(chk == null) {
                return "common/noAuth";
            }
        }
        
        projectService.insertProject(projectInfo);
        
        return "redirect:projectList";
    }
    
    /**
     * 삭제.
     */
    @RequestMapping(value = "/projectDelete")
    public String projectDelete(HttpServletRequest request) {
        String prno = request.getParameter("prno");
        String userno = request.getSession().getAttribute("userno").toString();
        
        ProjectVO projectInfo = new ProjectVO();        // check auth for delete
        projectInfo.setPrno(prno);
        projectInfo.setUserno(userno);
        String chk = projectService.selectProjectAuthChk(projectInfo);
        if(chk == null) {
            return "common/noAuth";
        }
        
        projectService.deleteProjectOne(prno);
        
        return "redirect:/projectList";
    }
    
}
