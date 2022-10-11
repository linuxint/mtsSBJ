package com.devkbil.mtssbj.filesearch;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.devkbil.mtssbj.common.util.FileSearchUtil.showFIlesInDir3;

@Controller
public class FileSearchController {
    
    @RequestMapping(value = "/fileAllIndex")
    public String fileAllIndex(HttpServletRequest request, ModelMap modelMap) {
        //showFilesInDIr("C:\\dev_x64\\apache-tomcat-8.5.78", ".txt");
        List<?> list = showFIlesInDir3("C:\\dev_x64\\apache-tomcat-8.5.78");
        
        modelMap.put("listview", list);
        
        return "";
    }
}
