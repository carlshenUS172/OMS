package com.geekplus.java.oms.controller;

import com.geekplus.java.oms.entity.Order;
import com.geekplus.java.oms.entity.Product;
import com.geekplus.java.oms.service.OrderService;
import com.geekplus.java.oms.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/list")
    @ResponseBody
    public Map<String, Object> getProducts() {  // /oms/product/list
        Map<String, Object> model = new HashMap<>();
        List<Product> productList = productService.getProductList();
        model.put("productList", productList);
        return model;
    }

    @PostMapping("/purchase/{productId}")
    @ResponseBody
    public Map<String, Object> purchaseProduct(HttpSession session, @PathVariable("productId") String productId) {  // /oms/product/purchase/{productId}
        Map<String, Object> model = new HashMap<>();
        Object userInfo = session.getAttribute("userId");
        if (userInfo == null) {
            model.put("Msg", "Not log in!");
            return model;
        }

        String userId = userInfo.toString();
        int res = productService.purchase(userId, productId);
        model.put("Msg", res + " order created");
        return model;
    }
}
