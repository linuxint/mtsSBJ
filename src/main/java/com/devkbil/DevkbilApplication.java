package com.devkbil;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.devkbil.*")
public class DevkbilApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevkbilApplication.class, args);
    }

}
