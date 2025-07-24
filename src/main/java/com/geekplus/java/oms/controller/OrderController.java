package com.geekplus.java.oms.controller;

import com.geekplus.java.oms.entity.Order;
import com.geekplus.java.oms.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping(path = "/list")
    @ResponseBody
    public Map<String, Object> getOrderList(HttpSession session) {  // /oms/order/list
        Map<String, Object> model = new HashMap<>();
        Object userInfo = session.getAttribute("userId");
        if (userInfo == null) {
            model.put("Msg", "Not log in!");
            return model;
        }

        String userId = userInfo.toString();
        List<Order> orderList = orderService.getOrderList(userId);
        model.put("orderList", orderList);
        return model;
    }

    @GetMapping(path = "/detail/{orderId}")
    @ResponseBody
    public Map<String, Object> getOrderDetail(@PathVariable("orderId") String orderId, HttpSession session) {  // /oms/order/detail/{orderId}
        Map<String, Object> model = new HashMap<>();
        Object userInfo = session.getAttribute("userId");
        if (userInfo == null) {
            model.put("Msg", "Not log in!");
            return model;
        }

        Order order = orderService.getOrderById(orderId);
        model.put("orderList", order);
        return model;
    }
}
