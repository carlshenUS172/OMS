package com.geekplus.java.oms.controller;

import com.geekplus.java.oms.entity.User;
import com.geekplus.java.oms.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> getRegisterPage() {
        return ResponseEntity.ok("Register page");
    }

    @GetMapping("/login")
    public ResponseEntity<String> getLoginPage(HttpSession session) {
        System.out.println(session.getAttribute("userId"));
        return ResponseEntity.ok("Login page");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(String username, String password) {  // /oms/register
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        Map<String, Object> map = userService.register(user);
        if (map == null || map.isEmpty()) {
            return ResponseEntity.ok("Register success!");
        } else {
            return ResponseEntity.badRequest().body(map.get("usernameMsg").toString() + " " + map.get("passwordMsg").toString());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(String username, String password, HttpSession session) {  // /oms/login
        Map<String, Object> map = userService.login(username, password);
        if (map.containsKey("userId")) {
            session.setAttribute("userId", map.get("userId"));
            System.out.println(session.getAttribute("userId"));
            return ResponseEntity.ok("Register success!");
        } else {
            return ResponseEntity.badRequest().body(map.get("usernameMsg").toString() + " " + map.get("passwordMsg").toString());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        Map<String, Object> model = new HashMap<>();
        return ResponseEntity.ok("logged out");
    }
}
