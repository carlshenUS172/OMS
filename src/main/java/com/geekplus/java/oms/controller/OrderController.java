package com.geekplus.java.oms.controller;

import com.geekplus.java.oms.entity.Order;
import com.geekplus.java.oms.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> getOrderList(HttpSession session) {  // /oms/order/list
        Object userInfo = session.getAttribute("userId");
        if (userInfo == null) {
            return ResponseEntity.badRequest().body("not logged in!");
        }

        String userId = userInfo.toString();
        List<Order> orderList = orderService.getOrderList(userId);
        return ResponseEntity.ok(orderList.stream().map(Order::toString).toString());
    }

    @GetMapping(path = "/detail/{orderId}")
    public ResponseEntity<String> getOrderDetail(@PathVariable("orderId") String orderId, HttpSession session) {  // /oms/order/detail/{orderId}
        Object userInfo = session.getAttribute("userId");
        if (userInfo == null) {
            return ResponseEntity.badRequest().body("not logged in!");
        }

        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order.toString());
    }
}
