package com.devkbil.mtssbj.develop.filesearch;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.devkbil.mtssbj.common.util.FileSearchUtil.showFIlesInDir3;

@Controller
public class FileSearchController {

    @RequestMapping(value = "/fileAllIndex")
    public String fileAllIndex(HttpServletRequest request, ModelMap modelMap) {
        //showFilesInDIr("C:\\dev_x64\\apache-tomcat-8.5.78", ".txt");
        String filePath = System.getProperty("user.dir") + "/fileupload/"; //localeMessage.getMessage("info.filePath") + "/";  //  첨부 파일 경로

        List<?> list = showFIlesInDir3(filePath);

        modelMap.put("listview", list);

        return "etc/fileAllIndex";
    }
}
