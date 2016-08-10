package zovl.zhongguanhua.thread.demo.ui.activity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.thread.demo.R;

public class NewActivity extends TBaseActivity {

    @Bind(R.id.index)
    EditText index;

    public int getIndex() {
        try {
            return Integer.valueOf(index.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return -1;
    }

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

    public static final String TAG = NewActivity.class.getSimpleName();

    @Override
    public int getContentView() {
        return R.layout.activity_new;
    }

    // ---------------------------------------------------------------------------------

    private List<LoopThread> threads = new ArrayList<>();
    private LoopThread lastThread;

    @OnClick({R.id.init,
            R.id.start,
            R.id.interrupt,
            R.id.isAlive,

            R.id.startAll,
            R.id.stopAll,
            R.id.isAliveAll,
            R.id.interruptAll,

            R.id.suspendAll,
            R.id.resumeAll})
    public void onClick(View view) {

        setTitle(getTitle() + "#");
        
        Thread thread = null;

        switch (view.getId()) {

            case R.id.init:
                lastThread = new LoopThread();
                threads.add(lastThread);
                printMyThread();
                break;

            case R.id.start:
                int i = getIndex();
                if (i >=0 &&
                        i < threads.size()) {
                    try {
                        thread = threads.get(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (thread != null &&
                            thread.getState().equals(Thread.State.NEW)) {
                        thread.start();
                    }
                    printMyThreadState(thread);
                }
                break;

            case R.id.interrupt:
                i = getIndex();
                if (i >=0 &&
                        i < threads.size()) {
                    try {
                        thread = threads.get(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (thread != null &&
                            !thread.getState().equals(Thread.State.TERMINATED)) {
                        thread.interrupt();
                    }
                    printMyThreadState(thread);
                }
                break;

            case R.id.isAlive:
                i = getIndex();
                if (i >=0 &&
                        i < threads.size()) {
                    try {
                        thread = threads.get(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    printMyThreadState(thread);
                }
                break;

            case R.id.startAll:
                for (Thread th : threads) {
                    if (th.getState().equals(Thread.State.NEW)) {
                        th.start();
                    }
                }
                break;

            case R.id.stopAll:
                for (Thread th : threads) {
                    try {
                        th.stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.isAliveAll:
                printMyThreadState();
                break;

            case R.id.interruptAll:
                for (Thread th : threads) {
                    th.interrupt();
                }
                break;

            case R.id.suspendAll:
                for (Thread th : threads) {
                    th.suspend();
                }
                break;

            case R.id.resumeAll:
                for (Thread th : threads) {
                    th.resume();
                }
                break;
        }
    }

    // ---------------------------------------------------------------------------------

    private void printMyThread() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\n");
        buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        buffer.append("\n");
        for (Thread thread : threads) {
            buffer.append("thread: " + thread + "\n");
        }
        log(buffer.toString());
        setText();
    }

    private void printMyThreadState() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\n");
        buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        buffer.append("\n");
        for (Thread thread : threads) {
            buffer.append("thread: " + thread.getName() +
                    "--isAlive=" + thread.isAlive() +
                    "--state=" + thread.getState() + "\n");
        }
        log(buffer.toString());
        setText();
    }

    private void printMyThreadState(Thread thread) {
        if (thread != null) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("\n");
            buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            buffer.append("\n");
            buffer.append("thread: " + thread.getName() +
                    "--isAlive=" + thread.isAlive() +
                    "--state=" + thread.getState() + "\n");
            log(buffer.toString());
            setText();
        }
    }

    private class LoopThread extends Thread {

        private int index;

        @Override
        public void run() {
            super.run();

            log("thread: " + getName() + "--running...");
            setText();

            while (true) {
                if (isInterrupted()) {
                    log("thread: " + getName() + "--interrupt...");
                    return;
                }
                ++index;
                Log.d(TAG, "thread: " + getName() + "--index=" + index);
                setText();
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    log("thread: " + getName() + "--interrupted...exception...");
                    setText();
                    return;
                }
            }
        }
    }
}
