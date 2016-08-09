package zovl.zhongguanhua.thread.demo.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.thread.demo.R;

public class BaseActivity extends TBaseActivity {

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

    public static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    public int getContentView() {
        return R.layout.activity_base;
    }

    private List<MyThread> myThreads = new ArrayList<>();
    private MyThread lastThread;

    @OnClick({R.id.initialization,
            R.id.start,
            R.id.isAlive,
            R.id.startAll,
            R.id.stopAll,
            R.id.isAliveAll,
            R.id.interrupt})
    public void onClick(View view) {

        setTitle(getTitle() + "#");

        switch (view.getId()) {

            case R.id.initialization:
                lastThread = new MyThread();
                myThreads.add(lastThread);
                printMyThread();
                break;

            case R.id.start:
                int i = getIndex();
                if (i >=0 &&
                        i < myThreads.size()) {
                    MyThread myThread = null;
                    try {
                        myThread = myThreads.get(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (myThread != null &&
                            myThread.getState().equals(Thread.State.NEW)) {
                        myThread.start();
                    }
                    log("MyThread: " + Thread.currentThread().getName() +
                            "--isAlive=" + myThread.isAlive() +
                            "--state=" + myThread.getState());
                    setText();
                }
                break;

            case R.id.isAlive:
                i = getIndex();
                if (i >=0 &&
                        i < myThreads.size()) {
                    MyThread myThread = null;
                    try {
                        myThread = myThreads.get(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (myThread != null) {
                        log("MyThread: " + Thread.currentThread().getName() +
                                "--isAlive=" + myThread.isAlive() +
                                "--state=" + myThread.getState());
                        setText();
                    }
                }
                break;

            case R.id.startAll:
                for (MyThread myThread: myThreads) {
                    if (myThread.getState().equals(Thread.State.NEW)) {
                        myThread.start();
                    }
                }
                break;

            case R.id.stopAll:
                for (MyThread myThread: myThreads) {
                    try {
                        myThread.stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.isAliveAll:
                printMyThreadState();
                break;

            case R.id.interrupt:
                for (MyThread myThread: myThreads) {
                    myThread.interrupt();
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
        for (MyThread myThread : myThreads) {
            buffer.append("MyThread: " + myThread + "\n");
        }
        log(buffer.toString());
        setText();
    }

    private void printMyThreadState() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\n");
        buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        buffer.append("\n");
        for (MyThread myThread : myThreads) {
            buffer.append("MyThread: " + Thread.currentThread().getName() +
                    "--isAlive=" + myThread.isAlive() +
                    "--state=" + myThread.getState() + "\n");
        }
        log(buffer.toString());
        setText();
    }

    private class MyThread extends Thread {

        private int index;

        @Override
        public void run() {
            super.run();

            log("MyThread: " + Thread.currentThread().getName() + "--running...");
            setText();

            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    log("MyThread: " + Thread.currentThread().getName() + "--interrupt...");
                    break;
                }
                ++index;
                Log.d(TAG, "MyThread: " + Thread.currentThread().getName() + "--index=" + index);
                setText();
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    log("MyThread: " + Thread.currentThread().getName() + "--interrupted...");
                    setText();
                    break;
                }
            }
        }
    }
}
