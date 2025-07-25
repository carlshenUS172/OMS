package com.geekplus.java.oms.controller;

import com.geekplus.java.oms.entity.Order;
import com.geekplus.java.oms.entity.Product;
import com.geekplus.java.oms.service.OrderService;
import com.geekplus.java.oms.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/list")
    // url: /oms/product/list
    public ResponseEntity<String> getProducts() {
        List<Product> productList = productService.getProductList();
        return ResponseEntity.ok(productList.toString());
    }

    @PostMapping("/purchase/{productId}")
    // url: /oms/product/purchase/{productId}
    public ResponseEntity<String> purchaseProduct(HttpSession session, @PathVariable("productId") String productId) {
        Object userInfo = session.getAttribute("userId");
        if (userInfo == null) {
            return ResponseEntity.badRequest().body("not logged in!");
        }

        String userId = userInfo.toString();
        boolean res = productService.purchase(userId, productId);

        return res ? ResponseEntity.ok("order created") : ResponseEntity.badRequest().body("order failed!");
    }
}
