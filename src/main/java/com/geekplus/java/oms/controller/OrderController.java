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

import java.util.List;

@Controller
@RequestMapping("order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping(path = "/list")
    public String getOrderList(Model model, HttpSession session) {
        Object userInfo = session.getAttribute("userId");
        if (userInfo == null) {
            return "login/login";
        }

        String userId = userInfo.toString();
        List<Order> orderList = orderService.getOrderList(userId);
        model.addAttribute("orderList", orderList);
        return "order/list";
    }

    @GetMapping(path = "/detail/{orderId}")
    public String getOrderDetail(@PathVariable("orderId") String orderId, Model model, HttpSession session) {
        Object userInfo = session.getAttribute("userId");
        if (userInfo == null) {
            return "login/login";
        }

        Order order = orderService.getOrderById(orderId);
        model.addAttribute("orderList", order);
        return "order/detail";
    }
}
