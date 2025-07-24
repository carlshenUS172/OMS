package com.geekplus.java.oms.service;

import com.geekplus.java.oms.dao.UserMapper;
import com.geekplus.java.oms.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public Map<String, Object> register(User user) {
        Map<String, Object> map = new HashMap<>();

        if (user == null) {
            throw new IllegalArgumentException("No parameter!");
        }
        if (isEmptyString(user.getUsername())) {
            map.put("usernameMsg", "No username!");
            return map;
        }
        if (isEmptyString(user.getPassword())) {
            map.put("passwordMsg", "No password!");
            return map;
        }

        User u = userMapper.selectByName(user.getUsername());
        if (u != null) {
            map.put("usernameMsg", "Username already exists!");
            return map;
        }

        userMapper.insertUser(user);

        return map;
    }

    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashMap<>();

        if (isEmptyString(username)) {
            map.put("usernameMsg", "No username!");
            return map;
        }
        if (isEmptyString(password)) {
            map.put("passwordMsg", "No password!");
            return map;
        }

        User user = userMapper.selectByName(username);
        if (user == null) {
            map.put("usernameMsg", "No such username!");
            return map;
        }

        if (!user.getPassword().equals(password)) {
            map.put("passwordMsg", "Password does not match!");
            return map;
        }

        map.put("userId", user.getId());
        return map;
    }

    private Boolean isEmptyString(String s) {
        return s == null || s.isEmpty();
    }
}
