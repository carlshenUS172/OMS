package com.geekplus.java.oms.initializer;

import com.geekplus.java.oms.dao.ProductMapper;
import com.geekplus.java.oms.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
// 缓存预热
public class ProductStockInitializer implements ApplicationRunner {
    // 让数据库中product的quantity作为初始库存放入缓存。为的是不让在业务中发现缓存未命中而写，容易发生脏读

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Product> productList = productMapper.getProductList();

        productList.forEach(product -> stringRedisTemplate.opsForValue().set("productStock:" + product.getId(), String.valueOf(product.getQuantity())));

        System.out.println("Cache Preheated!");
    }
}