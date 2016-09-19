package zovl.zhongguanhua.thread.demo.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.framework.lib.utils.ToastHelper;
import zovl.zhongguanhua.framework.lib.utils.ViewUtil;
import zovl.zhongguanhua.thread.demo.R;

public class JoinActivity extends TBaseActivity {

    @Override
    public TextView getText() {
        return text;
    }

    @Override
    public ImageView getImage() {
        return image;
    }

    @Bind(R.id.image)
    ImageView image;

    @Bind(R.id.text)
    TextView text;

    public static final String TAG = JoinActivity.class.getSimpleName();

    @Override
    public int getContentView() {
        return R.layout.activity_join;
    }

    // ---------------------------------------------------------------------------------

    private Thread thread;
    private List<Thread> threads = Collections.synchronizedList(new ArrayList<Thread>());

    @OnClick({R.id.join,

            R.id.start,
            R.id.interrupt})
    public void onClick(View view) {

        setTitle(getTitle() + "#");

        switch (view.getId()) {

            case R.id.join:
                if (threads.size() > 0) {
                    ToastHelper.s("threads are running...");
                    return;
                }
                ViewUtil.enabled(view, 3000);
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        int number = 5;
                        for (int i = 0; i < number; i++) {
                            logText("loop: " + "--start...");

                            thread = new SleepThread();
                            thread.start();
                            try {
                                logText("thread: " + thread.getName() + "--joining...");

                                thread.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            if (i < number - 1) {
                                logText("loop: " + "--end...");
                            } else {
                                logText("loop: " + "--stop...");
                            }
                        }
                    }
                }).start();
                break;

            case R.id.start:
                thread = new LoopThread();
                thread.start();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        int number = 5;
                        for (int i = 0; i < number; i++) {
                            Thread t = new WaitThread(i, thread);
                            t.start();
                        }
                    }
                }, 1200);
                break;

            case R.id.interrupt:
                if (thread != null &&
                        !thread.getState().equals(Thread.State.TERMINATED)) {
                    thread.interrupt();
                }
                break;
        }
    }

    // ---------------------------------------------------------------------------------

    private class SleepThread extends Thread {

        @Override
        public void run() {
            super.run();

            logText("thread: " + getName() + "--start...");

            try {
                logText("thread: " + getName() + "--sleeping...");

                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            logText("thread: " + getName() + "--end...");

            threads.remove(this);
            logText("thread: size=" + threads.size());
        }
    }

    // ---------------------------------------------------------------------------------

    private class LoopThread extends Thread {

        private long index;

        public LoopThread() {
            super("LoopThread");
        }

        @Override
        public void run() {
            super.run();

            logText("thread: " + getName() + "--start...");

            while (true) {
                if (index % 1000 == 0) {
                    log("thread: " + getName() + "--index=" + index);
                }
                ++ index;

                if (isInterrupted()) {
                    break;
                }
            }

            logText("thread: " + getName() + "--end...");
        }
    }

    private class WaitThread extends Thread {

        private Thread thread;

        public WaitThread(int index, Thread thread) {
            super("WaitThread-" + index);
            this.thread = thread;
        }

        @Override
        public void run() {
            super.run();

            logText("thread: " + getName() + "--start...");

            if (thread != null) {
                try {
                    logText("thread: " + getName() + "--waiting...");
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    logText("thread: " + getName() + "--interrupted...");
                }
            }

            logText("thread: " + getName() + "--end...");
        }
    }
}
