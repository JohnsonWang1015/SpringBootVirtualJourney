package com.nuu.controller;

import com.nuu.components.CountryLanguage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;

@Controller
public class HomeController {

    @GetMapping(path = {"", "/", "/index"})
    public String index(){
        return "index";
    }

    @GetMapping(path = "/home")
    public String home(){
        return "home";
    }

    @GetMapping(path = "/taipei")
    public String taipeiArea(){
        return "taipeiarea";
    }

}
