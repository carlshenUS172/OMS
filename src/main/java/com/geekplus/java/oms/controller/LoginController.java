package com.geekplus.java.oms.controller;

import com.geekplus.java.oms.entity.User;
import com.geekplus.java.oms.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
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
    public String getLoginPage(HttpSession session) {
        System.out.println(session.getAttribute("userId"));
        return "login/login";
    }

    @PostMapping("/register")
    @ResponseBody
    public Map<String, Object> register(String username, String password) {  // /oms/register
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        Map<String, Object> map = userService.register(user);
        Map<String, Object> model = new HashMap<>();
        if (map == null || map.isEmpty()) {
            model.put("msg", "Register success!");
        } else {
            model.put("usernameMsg", map.get("usernameMsg"));
            model.put("passwordMsg", map.get("passwordMsg"));
        }
        return model;
    }

    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> login(String username, String password, HttpSession session) {  // /oms/login
        Map<String, Object> map = userService.login(username, password);
        Map<String, Object> model = new HashMap<>();
        if (map.containsKey("userId")) {
            session.setAttribute("userId", map.get("userId"));
            System.out.println(session.getAttribute("userId"));
            model.put("msg", map.get("userId") + " Login success!");
        } else {
            model.put("usernameMsg", map.get("usernameMsg"));
            model.put("passwordMsg", map.get("passwordMsg"));
        }
        return model;
    }

    @PostMapping("/logout")
    @ResponseBody
    public Map<String, Object> logout(HttpSession session) {
        session.invalidate();
        Map<String, Object> model = new HashMap<>();
        model.put("msg", "Logged out");
        return model;
    }
}
