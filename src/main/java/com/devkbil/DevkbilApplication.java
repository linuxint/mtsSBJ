package com.devkbil;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@CrossOrigin(origins = "http://localhost:18080")    //'Access-Control-Allow-Origin' header 추가
//@RestController
public class DevkbilApplication {
    
    public static void main(String[] args) {
        //SpringApplication.run(DevkbilApplication.class, args);
        SpringApplication application = new SpringApplication(DevkbilApplication.class);
        //application.setBannerMode(Banner.Mode.OFF); -- banner mode off
        
        // custom banner of java
        /*
        application.setBanner(new Banner() {
            @Override
            public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {

                out.println("888b     d888 88888888888 .d8888b.        d888        .d8888b.)");
                out.println("8888b   d8888     888    d88P  Y88b      d8888       d88P  Y88b)");
                out.println("88888b.d88888     888    Y88b.             888       888    888)");
                out.println("888Y88888P888     888     \"Y888b.          888       888    888)");
                out.println("888 Y888P 888     888        \"Y88b.        888       888    888)");
                out.println("888  Y8P  888     888          \"888        888       888    888)");
                out.println("888   \"   888     888    Y88b  d88P        888   d8b Y88b  d88P)");
                out.println("888       888     888     \"Y8888P\"       8888888 Y8P  \"Y8888P\")");

            }
        });
        */
        application.run(args);
    }
    
}
