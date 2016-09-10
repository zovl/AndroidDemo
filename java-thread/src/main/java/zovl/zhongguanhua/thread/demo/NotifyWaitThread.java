package zovl.zhongguanhua.thread.demo;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 单线程的控制
 */
public class NotifyWaitThread {

    public static void main(String[] args) {

        /**
         * 新建一个工作线程并执行，5秒后工作线程等待，5秒后唤醒工作线程
         */

        // 线程是否【等待】
        AtomicBoolean isWaiting = new AtomicBoolean(false);

        // 同步对象
        Object syncObj = new Object();

        // 线程【开始】
        Thread thread = new Thread() {

            private AtomicInteger index = new AtomicInteger(0);

            @Override
            public void run() {
                super.run();

                setName("newThread-WorkerThread");

                // 循环执行
                while (true) {
                    System.out.println("thread: " + getName() + "--running..." +
                            "--index=" + index.incrementAndGet());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (isWaiting.get() == true) {
                        // 当前线程【等待】
                        synchronized (syncObj) {
                            try {
                                System.out.println("object: " + Thread.currentThread().getName() + "--wait...");
                                syncObj.wait();
                                System.out.println("object: " + Thread.currentThread().getName() + "--notify...");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        };
        thread.start();

        // 5秒后工作线程【等待】
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isWaiting.set(true);

        // 5秒后【唤醒】工作线程
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (syncObj) {
            isWaiting.set(false);
            syncObj.notify();
            System.out.println("object: " + Thread.currentThread().getName() + "--notify...");
        }
    }
}
