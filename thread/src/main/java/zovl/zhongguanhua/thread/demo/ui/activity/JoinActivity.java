package zovl.zhongguanhua.thread.demo.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
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

    @OnClick({R.id.init})
    public void onClick(View view) {

        setTitle(getTitle() + "#");

        switch (view.getId()) {

            case R.id.init:
                if (threads.size() > 0) {
                    toastShort("threads are running...");
                    return;
                }
                for (int i = 0; i < 5; i++) {
                    log("loop: " + "--start...");
                    setText();

                    thread = new SleepThread();
                    thread.start();
                    try {
                        log("thread: " + thread.getName() + "--joining...");
                        setText();

                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    log("loop: " + "--end...");
                    setText();
                }
                break;
        }
    }

    // ---------------------------------------------------------------------------------

    private class SleepThread extends Thread {

        @Override
        public void run() {
            super.run();

            log("thread: " + getName() + "--start...");
            setText();

            try {
                log("thread: " + getName() + "--sleeping...");
                setText();

                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log("thread: " + getName() + "--end...");
            setText();

            threads.remove(this);
        }
    }
}
