package com.codegen.service.test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author KyrieCao
 * @date 2020/6/7 18:32
 */
public class SemaphoreTest {
    private static int count = 21;

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(count);

        Semaphore semaphore = new Semaphore(5);

        Random random = new Random();

        for (int i = 1; i < count; i++) {
            final int no = i;
            service.execute(() -> {
                try {
                    // 获得许可
                    semaphore.acquire();
                    System.out.println(no + ": 号车可以通行");
                    // 模拟车辆通行耗时
                    Thread.sleep(random.nextInt(2000));
                    // 释放许可
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        service.shutdown();
    }
}
