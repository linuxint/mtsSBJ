package com.devkbil.mtssbj.common.util;

import com.devkbil.mtssbj.common.LocaleMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class FileDownload {
    
    @Autowired
    LocaleMessage localeMessage;
    
    /**
     * 파일(첨부파일, 이미지등) 다운로드.
     */
    @RequestMapping(value = "fileDownload")
    public void fileDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String path = System.getProperty("user.dir") + "/fileupload/";
        
        String filename = request.getParameter("filename");
        String downname = request.getParameter("downname");
        String realPath = "";
        
        if(filename == null || "".equals(filename)) {
            filename = downname;
        }
    
        filename = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString());
        try {
            realPath = path + downname.substring(0, 4) + "/" + downname;
        } catch (Exception e) {
        }
        
        File file1 = new File(realPath);
        if(!file1.exists()) {
            return;
        }
        
        // 파일명 지정
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        try {
            OutputStream os = response.getOutputStream();
            FileInputStream fis = new FileInputStream(realPath);
            
            int ncount = 0;
            byte[] bytes = new byte[512];
            
            while ((ncount = fis.read(bytes)) != -1) {
                os.write(bytes, 0, ncount);
            }
            fis.close();
            os.close();
        } catch (FileNotFoundException ex) {
            log.error("FileNotFoundException");
        } catch (IOException ex) {
            log.error("IOException");
        }
    }
    
}
