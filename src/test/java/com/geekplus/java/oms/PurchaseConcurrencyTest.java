package com.geekplus.java.oms;

import com.geekplus.java.oms.dao.ProductMapper;
import com.geekplus.java.oms.entity.Product;
import com.geekplus.java.oms.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
public class PurchaseConcurrencyTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    private final int THREAD_COUNT = 100; // 模拟100个用户并发下单
    private final String PRODUCT_ID = "20b1a7e5-695d-11f0-97e3-8c3b4ab2d146";
    private final String[] USERIDS = new String[] {"529c1433-688f-11f0-97e3-8c3b4ab2d146",
            "c2034a3d-695d-11f0-97e3-8c3b4ab2d146",
            "c203ce7f-695d-11f0-97e3-8c3b4ab2d146",
            "d4e5f6a7-b890-1234-d567-f890g123h456"};

    @Test
    public void testConcurrentPurchase() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch ready = new CountDownLatch(THREAD_COUNT);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(THREAD_COUNT);

        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();

        for (int i = 0; i < THREAD_COUNT; i++) {
            final String userId = USERIDS[i % USERIDS.length];
            executor.submit(() -> {
                try {
                    ready.countDown();     // 表示线程已就绪
                    start.await();         // 等待统一开始信号

                    boolean success = productService.purchase(userId, PRODUCT_ID);
                    if (success) {
                        successCount.incrementAndGet();
                    } else {
                        failCount.incrementAndGet();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    done.countDown();
                }
            });
        }

        ready.await();     // 等所有线程准备好
        start.countDown(); // 发令枪——所有线程同时开始
        done.await();      // 等所有线程执行完

        executor.shutdown();

        System.out.println("Success order: " + successCount.get());
        System.out.println("Fail order: " + failCount.get());

        System.out.println("Final quantity: " + productMapper.getQuantity(PRODUCT_ID));
    }
}