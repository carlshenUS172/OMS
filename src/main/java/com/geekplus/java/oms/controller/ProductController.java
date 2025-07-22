package com.geekplus.java.oms.controller;

import com.geekplus.java.oms.service.OrderService;
import com.geekplus.java.oms.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("list")
    public String getProducts(Model model) {
        model.addAttribute("productList", productService.getProductList());
        return "product/list";
    }

    @PostMapping("purchase/{productId}")
    public String purchaseProduct(Model model, HttpSession session, @PathVariable("productId") String productId) {
        Object userInfo = session.getAttribute("userId");
        if (userInfo == null) {
            return "login/login";
        }

        String userId = userInfo.toString();
        productService.purchase(userId, productId);
        return "order/list";
    }
}
