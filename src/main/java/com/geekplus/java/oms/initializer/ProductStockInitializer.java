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
public class ProductStockInitializer implements ApplicationRunner {

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