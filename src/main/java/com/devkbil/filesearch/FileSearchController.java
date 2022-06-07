package com.devkbil.filesearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static com.devkbil.util.FileSearchUtil.showFIlesInDir3;

@Controller
public class FileSearchController {
    
    static final Logger LOGGER = LoggerFactory.getLogger(FileSearchController.class);
    
    @RequestMapping(value = "/fileAllIndex")
    public String fileAllIndex(HttpServletRequest request, ModelMap modelMap) {
        //showFilesInDIr("C:\\dev_x64\\apache-tomcat-8.5.78", ".txt");
        showFIlesInDir3("C:\\dev_x64\\apache-tomcat-8.5.78");
        return "";
    }
}
