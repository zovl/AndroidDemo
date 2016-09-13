package zovl.zhongguanhua.thread.demo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程执行体内的空循环
 */
public class EmptyLoopThread {

    public static void main(String[] args) {

        JavaThread thread = new JavaThread();
        thread.start();
    }

    public static class JavaThread extends Thread {

        private AtomicInteger index = new AtomicInteger(0);

        public JavaThread() {
            super("newThread-JavaThread");
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
