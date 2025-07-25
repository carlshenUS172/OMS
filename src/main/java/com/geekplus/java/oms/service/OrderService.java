package com.geekplus.java.oms.service;

import com.geekplus.java.oms.dao.OrderMapper;
import com.geekplus.java.oms.entity.Order;
import com.geekplus.java.oms.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    public List<Order> getOrderList(String userId) {
        return orderMapper.getOrderList(userId);
    }

    public Order getOrderById(String id) {
        return orderMapper.getOrderById(id);
    }

    public int addOrder(String userId, String productId) {
        Order order = new Order();
        order.setUserId(userId);
        order.setProductId(productId);
        return orderMapper.addOrder(order);
    }
}
