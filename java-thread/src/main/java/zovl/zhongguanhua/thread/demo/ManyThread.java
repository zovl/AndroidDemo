package zovl.zhongguanhua.thread.demo;

/**
 * 多个线程
 */
public class ManyThread {

    public static void main(String[] args) {

        // 新建多个线程启动并空循环
        for (int i = 0; i < 50; i++) {

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
            while (true) {
                System.out.println("thread: " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
