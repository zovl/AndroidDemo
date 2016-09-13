package zovl.zhongguanhua.thread.demo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程执行体内的空循环
 */
public class EmptyThread {

    public static void main(String[] args) {

        WorkerThread thread = new WorkerThread();
        thread.start();
    }

    public static class WorkerThread extends Thread {

        private AtomicInteger index = new AtomicInteger(0);

        public WorkerThread() {
            super("newThread-WorkerThread");
        }

        @Override
        public void run() {
            super.run();

            // 循环
            while (true) {

            }
        }
    }
}
