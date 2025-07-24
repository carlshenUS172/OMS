package com.geekplus.java.oms.service;

import com.geekplus.java.oms.dao.OrderMapper;
import com.geekplus.java.oms.dao.ProductMapper;
import com.geekplus.java.oms.entity.Order;
import com.geekplus.java.oms.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    public List<Product> getProductList() {
        return productMapper.getProductList();
    }

    public int purchase(String userId, String productId) {
        Order order = new Order();
        order.setUserId(userId);
        order.setProductId(productId);
        return orderMapper.addOrder(order);
    }
}
