package zovl.zhongguanhua.thread.demo;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 同步锁
 */
public class LockThread {

    public static void main(String[] args) {

        LockObject obj = new LockObject();

        for (int i = 0; i < 100; i++) {
            LockThreada a = new LockThreada(obj, i, i % 2 == 0 ? true : false);
            LockThreadb b = new LockThreadb(obj);

            a.start();
            b.start();
        }
    }

    public static class LockThreada extends Thread {

        private LockObject lockObject;
        private int index;
        private boolean flag;

        public LockThreada(LockObject lockObject, int index, boolean flag) {
            this.lockObject = lockObject;
            this.index = index;
            this.flag = flag;
        }

        @Override
        public void run() {
            super.run();

            if (lockObject != null) {
                lockObject.writer(index, flag);
            }
        }
    }

    public static class LockThreadb extends Thread {

        private LockObject lockObject;

        public LockThreadb(LockObject lockObject) {
            this.lockObject = lockObject;
        }

        @Override
        public void run() {
            super.run();

            if (lockObject != null) {
                lockObject.reader();
            }
        }
    }

    public static class LockObject {

        private int index;
        private boolean flag;

        private ReentrantLock lock = new ReentrantLock();

        public void writer(int index, boolean flag) {

            lock.lock();
            try {
                System.out.println("writer: " + Thread.currentThread().getName() + "--start");
                this.index = index;
                this.flag = flag;
                System.out.println("writer: " + Thread.currentThread().getName() + "--end");
            } finally {
                lock.unlock();
            }
        }

        public void reader() {

            lock.lock();
            try {
                System.out.println("reader: " + Thread.currentThread().getName() + "--index=" + index + "--flag=" + flag);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("reader: " + Thread.currentThread().getName() + "--index=" + index + "--flag=" + flag);
            } finally {
                lock.unlock();
            }
        }
    }
}
