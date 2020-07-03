import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 模拟10个线程同时扣减库存
 *
 * @author KyrieCao
 * @date 2020/7/3 9:41
 */
public class ZKTest implements Runnable {
    static int inventory = 1;
    private static final int NUM = 10;
    private static CountDownLatch cdl = new CountDownLatch(NUM);
    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        for (int i = 0; i < NUM; i++) {
            new Thread(new ZKTest()).start();
            cdl.countDown();
        }
    }

    @Override
    public void run() {
        lock.lock();
        try {
            cdl.await();
            if (inventory > 0) {
                Thread.sleep(5);
                inventory --;
            }
            System.out.println(inventory);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
