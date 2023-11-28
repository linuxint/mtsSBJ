package com.devkbil.mtssbj;

import java.io.FileNotFoundException;
import java.net.URL;

import org.springframework.util.ResourceUtils;

public class ResourceTest {

    public ResourceTest() throws FileNotFoundException {
        URL url = ResourceUtils.getURL("classpath:logback");
    }
}
