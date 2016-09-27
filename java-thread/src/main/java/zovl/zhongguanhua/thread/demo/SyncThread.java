package zovl.zhongguanhua.thread.demo;

/**
 * 同步关键字
 */
public class SyncThread {

    public static void main(String[] args) {

        // 新建100个A线程，100个B线程，线程A读，线程B写，读和写用同一个对象锁

        SyncObject obj = new SyncObject();

        for (int i = 0; i < 100; i++) {
            SyncThreada a = new SyncThreada(obj, i, i % 2 == 0 ? true : false);
            SyncThreadb b = new SyncThreadb(obj);

            a.start();
            b.start();
        }
    }

    public static class SyncThreada extends Thread {

        private SyncObject syncObject;
        private int index;
        private boolean flag;

        public SyncThreada(SyncObject syncObject, int index, boolean flag) {
            this.syncObject = syncObject;
            this.index = index;
            this.flag = flag;
        }

        @Override
        public void run() {
            super.run();

            if (syncObject != null) {
                syncObject.writer(index, flag);
            }
        }
    }

    public static class SyncThreadb extends Thread {

        private SyncObject syncObject;

        public SyncThreadb(SyncObject syncObject) {
            this.syncObject = syncObject;
        }

        @Override
        public void run() {
            super.run();

            if (syncObject != null) {
                syncObject.reader();
            }
        }
    }

    public static class SyncObject {

        private int index;
        private boolean flag;

        public synchronized void writer(int index, boolean flag) {

            System.out.println("writer: " + Thread.currentThread().getName() + "--start");
            this.index = index;
            this.flag = flag;
            System.out.println("writer: " + Thread.currentThread().getName() + "--end");
        }

        public synchronized void reader() {

            System.out.println("reader: " + Thread.currentThread().getName() + "--index=" + index + "--flag=" + flag);
        }
    }
}
