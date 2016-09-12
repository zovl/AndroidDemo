package zovl.zhongguanhua.thread.demo;

import java.nio.ByteBuffer;

import static javafx.scene.input.KeyCode.T;

/**
 * 多个线程
 */
public class ManyThread {

    public static void main(String[] args) {

        for (int i = 0; i < 10000; i++) {

            WorkerThread thread = new WorkerThread(i);
            thread.start();
        }
    }

    public static class WorkerThread extends Thread {

        private int index;

        public WorkerThread(int index) {
            super("newThread-" + index);
            this.index = index;
        }

        @Override
        public void run() {
           super.run();
            System.out.println("thread: " + Thread.currentThread().getName());
            /*
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

            ByteBuffer buffer = ByteBuffer.allocate(10000);
        }
    }
}
