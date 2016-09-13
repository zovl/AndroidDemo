package zovl.zhongguanhua.thread.demo;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * wait/notify对单线程的控制
 */
public class NotifyWaitThread {

    public static void main(String[] args) {

        /**
         * 新建1个工作线程并执行，5秒后工作线程等待，5秒后唤醒工作线程
         */

        // 线程是否【等待】
        AtomicBoolean flag = new AtomicBoolean(false);

        // 同步对象
        Object syncObj = new Object();

        // 线程【开始】
        Thread thread = new WorkerThread(flag, syncObj);
        thread.start();

        // 5秒后工作线程【等待】
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag.set(true);

        // 5秒后【唤醒】工作线程
        try {
            Thread.sleep(25000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (syncObj) {
            flag.set(false);
            syncObj.notify();
            System.out.println("object: " + Thread.currentThread().getName() + "--notify...");
        }
    }

    public static class WorkerThread extends Thread {

        private AtomicInteger index = new AtomicInteger(0);
        // 线程是否【等待】
        private AtomicBoolean flag;
        // 同步对象
        private Object syncObj;

        public WorkerThread(AtomicBoolean flag, Object syncObj) {
            super("newThread-WorkerThread");
            this.flag = flag;
            this.syncObj = syncObj;
        }

        @Override
        public void run() {
            super.run();

            // 循环执行
            while (true) {
                System.out.println("thread: " + getName() + "--running..." +
                        "--index=" + index.incrementAndGet());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (flag.get()) {
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
    }
}
