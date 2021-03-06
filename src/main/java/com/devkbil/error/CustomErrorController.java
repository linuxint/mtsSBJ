package com.devkbil.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
public class CustomErrorController implements ErrorController {

    static final Logger LOGGER = LoggerFactory.getLogger(CustomErrorController.class);

    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request, HttpServletResponse response, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString()));
        LOGGER.info("httpStatus : "+httpStatus.toString());

        ModelAndView modelAndView = new ModelAndView();
        System.out.println(response.getStatus());
        modelAndView.addObject("code", status.toString());
        modelAndView.addObject("msg", httpStatus.getReasonPhrase());
        modelAndView.addObject("timestamp", new Date());
        modelAndView.setViewName("/error/error");
        /*
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                modelAndView.setViewName("/error/404");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                modelAndView.setViewName("/error/500");
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                modelAndView.setViewName("/error/403");
            } else modelAndView.setViewName("/error/common");
        }
         */
        return modelAndView;
    }

}