package com.geekplus.java.oms.service;

import com.geekplus.java.oms.dao.OrderMapper;
import com.geekplus.java.oms.dao.ProductMapper;
import com.geekplus.java.oms.entity.Order;
import com.geekplus.java.oms.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserService userService;

    private final ReentrantLock lock = new ReentrantLock();
    @Autowired
    private OrderService orderService;

    public List<Product> getProductList() {
        return productMapper.getProductList();
    }

    public int getProductQuantity(String productId) {
        return productMapper.getQuantity(productId);
    }

    @Transactional
    public boolean purchase(String userId, String productId) {
        //  双重检查锁
        int quantityToDeduct = 1;
        Product product = productMapper.getProductById(productId);
        if (product.getQuantity() < quantityToDeduct) {
            return false;
        }

        lock.lock();
        try {
            // 加锁后再次判断库存
            product = productMapper.getProductById(productId);
            if (product.getQuantity() < quantityToDeduct) {
                return false;  // 已被其他线程扣完
            }

            // 执行业务操作
            if (productMapper.deductQuantity(productId, quantityToDeduct) == 0) return false;
            int res = orderService.addOrder(userId, productId);
            return true;
        } finally {
            lock.unlock();
        }
    }
}
