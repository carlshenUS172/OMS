package com.geekplus.java.oms;

import com.geekplus.java.oms.service.OrderService;
import com.geekplus.java.oms.service.ProductService;
import com.geekplus.java.oms.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = {OmsApplication.class})
public class ServiceTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Test
    public void testGetOrderList() {
        String userId = "d4e5f6a7-b890-1234-d567-f890g123h456";
        System.out.println(orderService.getOrderList(userId));
    }

    @Test
    public void testGetOrder() {
        String orderId = "11112222-3333-4444-5555-666677778888";
        System.out.println(orderService.getOrderById(orderId));
    }

    @Test
    public void testGerProductList() {
        System.out.println(productService.getProductList());
    }

    @Test
    public void testPurchase() {
        String userId = "d4e5f6a7-b890-1234-d567-f890g123h456";
        String productId = "b2c3d4e5-f6a7-8901-b234-d567e890f123";
        System.out.println(productService.purchase(userId, productId));
        System.out.println(orderService.getOrderList(userId));
    }
}
