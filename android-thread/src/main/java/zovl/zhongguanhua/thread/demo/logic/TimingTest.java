package zovl.zhongguanhua.thread.demo.logic;

import android.os.*;
import android.os.Process;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 功能：Android 计时，倒计时（定时循环任务）
 */
public class TimingTest {

    public static void executefirst() {

        Timerfir timer = new Timerfir();

        timer.initLooperThread();
        timer.startTiming();

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        timer.stopTiming();
        timer.destroyLooperThread();
    }

    public static void executesecond() {

        Timersec timer = new Timersec();

        timer.startTiming();

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        timer.stopTiming();
    }

    public static void executethird() {

        Timerthi timer = new Timerthi();

        timer.startTiming();

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        timer.stopTiming();
    }

    // --------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------

    /**
     * 第1种方法：HandlerThread + Handler计时
     */
    public static class Timerfir {

        private Handler h = new Handler(Looper.getMainLooper());
        // 计时线程
        private final String threadName = "HandlerThread-Timer";
        private HandlerThread thread;
        private Looper looper;
        private Handler handler;

        /**
         * 初始化计时线程
         */
        private void initLooperThread() {
            thread = new HandlerThread(threadName, Process.THREAD_PRIORITY_BACKGROUND);
            thread.start();
            looper = thread.getLooper();
            handler = new Handler(looper);
        }

        /**
         * 销毁计时线程
         */
        private void destroyLooperThread() {
            if (thread != null)
                if (thread.isAlive())
                    try {
                        thread.quit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
        }

        /**
         * 开始计时
         */
        private void startTiming() {
            if (handler != null) {
                handler.post(runnable);
            }
        }

        /**
         * 停止计时
         */
        private void stopTiming() {
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
            }
        }

        private Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.d("Timer", "--running...");
                // TODO: 2016/9/11 执行任务
                // 或者消息到主线程再执行
                // h.sendEmptyMessage();

                handler.postDelayed(this, 1000);
            }
        };
    }

    // --------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------

    /**
     * 第2种方法：Thread 计时
     */
    public static class Timersec {

        private final String threadName = "newThread-Timer";
        private TimerThread thread;

        /**
         * 开始计时
         */
        private void startTiming() {
            if (thread == null) {
                thread = new TimerThread();
                thread.start();
            }
        }

        /**
         * 停止计时
         */
        private void stopTiming() {
            if (thread != null) {
                thread.stopTimer();
                thread = null;
            }
        }

        public class TimerThread extends Thread {

            private AtomicBoolean flag = new AtomicBoolean(false);

            public void stopTimer() {
                this.flag.set(false);
            }

            public TimerThread() {
                super(threadName);
            }

            @Override
            public void run() {
                super.run();

                while (flag.get() == true) {
                    Log.d("Timer", "--running...");
                    // TODO: 2016/9/11 执行任务
                    // 或者消息到主线程再执行
                    // h.sendEmptyMessage();
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // --------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------

    /**
     * 第3种方法：Timer + TimerTask 计时（可修正周期）
     */
    public static class Timerthi {

        private Handler h = new Handler(Looper.getMainLooper());
        private final String timerName = "Timing-";
        private Timer timer;
        private TimerTask timerTask;

        /**
         * 开始计时
         */
        private void startTiming() {
            stopTiming();

            timer = new Timer(timerName);
            timerTask = new TimerTask() {

                @Override
                public void run() {
                    Log.d("Timer", "--running...");
                    // TODO: 2016/9/11 执行任务
                    // 或者消息到主线程再执行
                    // h.sendEmptyMessage();
                }
            };
            // timer.schedule(timerTask, 0, 1000);
            // 修正周期
            timer.scheduleAtFixedRate(timerTask, 0, 1000);
        }

        /**
         * 停止计时
         */
        private void stopTiming() {
            if (timer != null)
                try {
                    timer.cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    // --------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------

    /**
     * 第4种方法：ScheduledExecutorService 定时循环任务（可修正周期）
     */
    public static class Timerfou {

        private ScheduledExecutorService scheduler;
        private ScheduledFuture future;

        /**
         * 开始计时
         */
        private void startTiming() {

            scheduler = Executors.newSingleThreadScheduledExecutor();
            final Runnable runnable = new Runnable() {

                @Override
                public void run() {
                    Log.d("Timer", "--running...");
                    // TODO: 2016/9/11 执行任务
                    // 或者消息到主线程再执行
                    // h.sendEmptyMessage();
                }
            };

            // 30秒后执行一次
            // future = scheduler.schedule(runnable, 30, TimeUnit.SECONDS);
            // 30秒后执行，每5秒执行一次，修正周期
            future = scheduler.scheduleAtFixedRate(runnable, 30, 5, TimeUnit.SECONDS);
        }

        /**
         * 停止计时
         */
        private void stopTiming() {
            if (future != null) {
                if (!future.isCancelled()) {
                    future.cancel(true);
                }
            }
            if (scheduler != null)
                if (!scheduler.isShutdown())
                    try {
                        scheduler.shutdown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
        }
    }

    // --------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------

    /**
     * 第5种方法：CountdownTimer 倒计时
     */
    public static class Timerfif {

        private CountDownTimer timer;

        /**
         * 开始倒计时
         */
        private void startTiming() {

            // 30秒倒计时，每秒执行一次
            timer = new CountDownTimer(30 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    Log.d("Timer", "--running...");
                    // TODO: 2016/9/11 执行任务
                }

                @Override
                public void onFinish() {
                    Log.d("Timer", "--finished...");
                    // TODO: 2016/9/11 执行任务
                }
            };
            timer.start();
        }

        /**
         * 停止倒计时
         */
        private void stopTiming() {
            if (timer != null) {
                try {
                    timer.cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // --------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------

    /**
     * 倒计时
     *
     * @param secs 倒计时时间，秒
     */
    public static void countDown(long secs) {
        if (secs > 0) {
            long hour = 0;
            long minute = 0;
            long seconds = 0;
            while (secs >= 0) {
                hour = secs / 3600;
                minute = (secs - hour * 3600) / 60;
                seconds = secs - hour * 3600 - minute * 60;
                System.out.println(hour + "时" + minute + "分" + seconds + "秒");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                secs--;
            }
        }
    }

    /**
     * 倒计时
     *
     * @param secs 倒计时时间，秒
     */
    private static void countDown_(long secs) {
        long l = System.currentTimeMillis();
        long ll = l;
        while (true) {
            l = System.currentTimeMillis();
            long d = l - ll;
            if (d % 1000 == 0) {
                System.out.println(new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date(l)));
            }
            if (d > secs) {
                System.out.println("倒计时结束！");
                break;
            }
        }
    }
}
