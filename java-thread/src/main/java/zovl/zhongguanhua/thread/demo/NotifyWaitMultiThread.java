package zovl.zhongguanhua.thread.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * wait/notify对多线程的控制
 */
public class NotifyWaitMultiThread {

    public static void main(String[] args) {

        /**
         * 新建5个工作线程并执行，5秒后所有工作线程等待，5秒后唤醒所有工作线程
         */

        // 线程是否【等待】
        AtomicBoolean isWait = new AtomicBoolean(false);

        // 同步对象
        Object syncObj = new Object();

        List<Thread> threads = new ArrayList<>();

        // 线程【开始】
        for (int i = 0; i < 5; i++) {
            Thread thread = new NotifyWaitThread.WorkerThread(isWait, syncObj);
            threads.add(thread);
            thread.start();
        }

        // 5秒后工作线程【等待】
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isWait.set(true);

        // 5秒后【唤醒】一个工作线程
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (syncObj) {
            isWait.set(false);
            syncObj.notify();
            System.out.println("object: " + Thread.currentThread().getName() + "--notify...");
        }

        // 5秒后【唤醒】全部工作线程
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (syncObj) {
            isWait.set(false);
            syncObj.notifyAll();
            System.out.println("object: " + Thread.currentThread().getName() + "--notify...");
        }
    }
}
