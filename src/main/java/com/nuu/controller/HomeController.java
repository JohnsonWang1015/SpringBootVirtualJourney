package com.nuu.controller;

import com.nuu.components.CountryLanguage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {

    private String spot = "";

    @GetMapping(path = {"", "/", "/index"})
    public String index(){
        return "index";
    }

    @GetMapping(path = "/home")
    public String home(){
        return "welcome";
    }

    @RequestMapping(path = "/taipei", method = {RequestMethod.GET, RequestMethod.POST})
    public String taipeiArea(String spot, HttpServletRequest request, Model model){
        try{
//            this.spot = spot;
            String referer = request.getHeader("Referer");
            if(referer != null && referer.endsWith("/history")){
                model.addAttribute("source", "history");
            }
        }catch (NullPointerException ex){

        }
        return "taipeiarea";
    }

    @RequestMapping(path = "/story", method = {RequestMethod.GET, RequestMethod.POST})
    public String story(Model model){
        try{
//            model.addAttribute("spot", spot);
        }catch (NullPointerException ex){

        }
        return "story";
    }

    @GetMapping(path = "/testMap")
    public String testMap(){
        return "testmap";
    }

    @RequestMapping(path = "/history", method = {RequestMethod.GET, RequestMethod.POST})
    public String history(Model model){
        return "history";
    }

    @GetMapping(path = "/check/history")
    public String checkHistory(){
        return "checkhistory";
    }

}
