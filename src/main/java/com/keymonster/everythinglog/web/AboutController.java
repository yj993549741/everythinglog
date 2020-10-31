package com.keymonster.everythinglog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * created by yangjie sheting on 2020/10/28
 */
@Controller
public class AboutController {

    @GetMapping
    public String about(){
        return "about";
    }
}
