package com.codegen.service.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * @author KyrieCao
 * @date 2020/6/7 18:49
 */
public class CountDownTest {
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);
        Worker worker1 = new Worker("张三", 4000, latch);
        Worker worker2 = new Worker("李四", 2000, latch);
        Worker worker3 = new Worker("王五", 3000, latch);
        worker1.start();
        worker2.start();
        worker3.start();

        long startTime = System.currentTimeMillis();
        latch.await();
        System.out.println("bug全部解决，领导可以给客户交差了，任务总耗时：" + (System.currentTimeMillis() - startTime));
    }

    static class Worker extends Thread {
        private String name;
        private int workTime;
        private CountDownLatch latch;

        public Worker(String name, int workTime, CountDownLatch latch) {
            this.name = name;
            this.workTime = workTime;
            this.latch = latch;
        }

        @Override
        public void run() {
            System.out.println(name + "开始修复bug，当前时间：" + sdf.format(new Date()));
            doWork();
            System.out.println(name + "结束修复bug，当前时间：" + sdf.format(new Date()));
            latch.countDown();
        }

        private void doWork() {
            try {
                Thread.sleep(workTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
