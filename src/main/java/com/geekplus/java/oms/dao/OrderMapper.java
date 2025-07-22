package com.geekplus.java.oms.dao;

import com.geekplus.java.oms.entity.Order;
import com.geekplus.java.oms.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface OrderMapper {
    public List<Order> getOrderList(String userId);

    public Order getOrderById(@Param("id") String id);

    public void addOrder(String userId, String orderId);
}
