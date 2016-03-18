package com.ecsteam;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LandingController {
    @RequestMapping(value = "/")
    public String index(Model model) {
        return "index";
    }

}
