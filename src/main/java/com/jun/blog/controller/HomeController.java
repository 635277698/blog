package com.jun.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        System.out.println("首页。。。");
        return "index";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        System.out.println("登出页面。。。");
        session.removeAttribute("user");
        return "redirect:/";
    }



    @GetMapping("/settings")
    public String settings() {
        System.out.println("设置。。。");
        return "settings";
    }

    @GetMapping("/login")
    public String login() {
        System.out.println("login。。。");
        return "login";
    }
}