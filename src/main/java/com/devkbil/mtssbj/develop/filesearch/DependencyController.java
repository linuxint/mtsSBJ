package com.devkbil.mtssbj.develop.filesearch;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DependencyController {

    @RequestMapping(value = "/dependencySearch")
    public String dependencySearch(HttpServletRequest request, ModelMap modelMap) {

        List<String> list = new ArrayList<>();

        String[] cmd;
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
        String s;
        try {
            Process p;
            if(isWindows) {
                cmd = new String[]{"cmd.exe", "/c", "gradlew dependencies --configuration compileClasspath"};
            } else {
                cmd = new String[]{"sh", "-c", "gradle dependencies --configuration compileClasspath"};
            }
            p = Runtime.getRuntime().exec(cmd);
            BufferedReader sI = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while((s = sI.readLine()) != null) {
                list.add(s);
            }
        }
        catch(IOException e) {
            e.getMessage();
        }

        modelMap.put("listview", list);

        return "thymeleaf/dependencySearch";
    }

}
