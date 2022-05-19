package com.devkbil.health;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.ui.Model;

@RestController
@AllArgsConstructor
@Api(value = "HomeControllerTest", description = "HomeController 테스트입니다.") // Controller에 대한 Swagger 설명
public class CheckController {

    @ApiOperation(  // API에 대한 Swagger 설명
            value="서비스",
            notes = "서비스입니다.",
            httpMethod = "POST",
            consumes = "application/json",
            produces = "application/json",
            protocols = "http",
            responseHeaders = {
                    //headers
            })
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "No params")
    })

    
    @ApiImplicitParams({
            @ApiImplicitParam(name = "area", value = "지역", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "param1", value = "파라미터1", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "param2", value = "파마미터2", required = false, dataType = "int", paramType = "query")
    })
    @GetMapping(value = "/home/{area}")
    public String home(@PathVariable String area, @RequestParam String param1, @RequestParam int param2) {
        return "home";
    }
    
    @GetMapping(value = "/")
    public String home(Model model) {
        model.addAttribute("hi", "Hello~~");
        return "home";
    }
    
    
    
}
