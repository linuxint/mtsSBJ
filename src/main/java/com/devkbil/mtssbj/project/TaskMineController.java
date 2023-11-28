package com.devkbil.mtssbj.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.devkbil.mtssbj.common.ExtFieldVO;
import com.devkbil.mtssbj.common.util.FileUtil;
import com.devkbil.mtssbj.common.util.FileVO;
import com.devkbil.mtssbj.etc.EtcService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class TaskMineController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private EtcService etcService;
    @Autowired
    private ProjectService projectService;

    /**
     * Task.
     */
    @RequestMapping(value = "/taskMine")
    public String taskMine(HttpServletRequest request, ModelMap modelMap) {

        String userno = request.getSession().getAttribute("userno").toString();
        String prno = request.getParameter("prno");

        ProjectVO projectInfo = projectService.selectProjectOne(prno);
        if (projectInfo == null) {
            return "common/noAuth";
        }

        Integer alertcount = etcService.selectAlertCount(userno);
        modelMap.addAttribute("alertcount", alertcount);

        List<?> listview = taskService.selectTaskMineList(new ExtFieldVO(prno, userno, null));

        modelMap.addAttribute("listview", listview);
        modelMap.addAttribute("prno", prno);
        modelMap.addAttribute("projectInfo", projectInfo);

        return "project/TaskMine";
    }

    @RequestMapping(value = "/taskMineForm")
    public String taskMineForm(HttpServletRequest request, ModelMap modelMap) {
        String tsno = request.getParameter("tsno");

        TaskVO taskInfo = taskService.selectTaskOne(tsno);
        List<?> listview = taskService.selectTaskFileList(tsno);

        modelMap.addAttribute("taskInfo", taskInfo);
        modelMap.addAttribute("listview", listview);

        return "project/TaskMineForm";
    }

    @RequestMapping(value = "/taskMineSave")
    public String taskMineSave(HttpServletRequest request, HttpServletResponse response, TaskVO taskInfo) {

        String[] fileno = request.getParameterValues("fileno");
        FileUtil fs = new FileUtil();
        List<FileVO> filelist = fs.saveAllFiles(taskInfo.getUploadfile());

        taskService.insertTaskMine(taskInfo, filelist, fileno);

        return "redirect:taskMine?prno=" + taskInfo.getPrno();
    }
}
