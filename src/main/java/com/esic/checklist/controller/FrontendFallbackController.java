package com.esic.checklist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontendFallbackController {

    @RequestMapping(value = {
        "/", 
        "/login"
    })
    public String forwardToIndex() {
        return "forward:/index.html";
    }
}
