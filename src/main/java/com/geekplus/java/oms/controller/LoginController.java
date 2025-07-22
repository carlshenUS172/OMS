package com.geekplus.java.oms.controller;

import com.geekplus.java.oms.entity.User;
import com.geekplus.java.oms.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    UserService userService;

    @GetMapping("/register")
    public String getRegisterPage() {
        return "login/register";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login/login";
    }

    @PostMapping("/register")
    public String register(Model model, User user) {
        Map<String, Object> map = userService.register(user);
        if (map == null || map.isEmpty()) {
            model.addAttribute("msg", "Register success!");
            return "redirect:login/login";
        } else {
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            return "login/register";
        }
    }

    @PostMapping("/login")
    public String login(String username, String password, HttpSession session, Model model) {
        Map<String, Object> map = userService.login(username, password);
        if (map.containsKey("userId")) {
            session.setAttribute("userId", map.get("userId"));
            return "redirect:/index";
        } else {
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            return "site/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
