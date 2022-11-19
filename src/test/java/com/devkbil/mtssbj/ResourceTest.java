package com.devkbil.mtssbj;

import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.net.URL;

public class ResourceTest {
    
    public ResourceTest() throws FileNotFoundException {
        URL url = ResourceUtils.getURL("classpath:logback");
    }
}
