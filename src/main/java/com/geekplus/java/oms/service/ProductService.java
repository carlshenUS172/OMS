package com.geekplus.java.oms.service;

import com.geekplus.java.oms.dao.OrderMapper;
import com.geekplus.java.oms.dao.ProductMapper;
import com.geekplus.java.oms.entity.Order;
import com.geekplus.java.oms.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
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

    @Autowired
    private StringRedisTemplate operations;

    private final String REDIS_KEY_PREFIX = "productStock:";

    private final ReentrantLock lock = new ReentrantLock();
    @Autowired
    private OrderService orderService;

    public List<Product> getProductList() {
        return productMapper.getProductList();
    }

    @Transactional
    public boolean purchase(String userId, String productId, int quantityToDeduct) {
        //  双重检查锁
        String productKey = REDIS_KEY_PREFIX + productId;

        // 确保库存有值
        Object value = operations.opsForValue().get(productKey);
        int stock = value == null ? 0 : Integer.parseInt((String) value);

        // 没库存直接退，不拿锁，提升性能
        if (stock < quantityToDeduct) {
            return false;
        }

        lock.lock();
        try {
            // 加锁后再次判断库存
            value = operations.opsForValue().get(productKey);
            stock = value == null ? 0 : Integer.parseInt((String) value);
            if (stock < quantityToDeduct) {
                return false;
            }

            // 执行业务操作
            Long remain = operations.opsForValue().decrement(productKey, quantityToDeduct);
            // 发现库存是负数了，发生超卖
            if (remain == null || remain < 0) {
                operations.opsForValue().increment(productKey, quantityToDeduct);  // 回滚补偿
                return false;
            }

            // 插入订单
            int res = orderService.addOrder(userId, productId);
            // 插入失败
            if (res != 1) {
                operations.opsForValue().increment(productKey, quantityToDeduct);  // 回滚补偿
                return false;
            }

            return true;
        } finally {
            lock.unlock();
        }
    }

    @Transactional
    // 事务传播为REQUIRED，合并事务
    public boolean purchase(String userId, String productId) {
        return purchase(userId, productId, 1);
    }
}
