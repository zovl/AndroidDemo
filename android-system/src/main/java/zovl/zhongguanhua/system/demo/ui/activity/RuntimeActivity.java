package zovl.zhongguanhua.system.demo.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.system.demo.R;
import zovl.zhongguanhua.system.demo.logic.RuntimeHelper;

public class RuntimeActivity extends TBaseActivity {

    @Bind(R.id.exitCode)
    TextView exitCode;

    public Integer getExitCode() {
        try {
            return Integer.valueOf(exitCode.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
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

    public static final String TAG = RuntimeActivity.class.getSimpleName();

    @Override
    public int getContentView() {
        return R.layout.activity_runtime;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.memory,

            R.id.addShutdownHook,

            R.id.gc,
            R.id.runFinalization,

            R.id.exit})
    public void onClick(View view) {

        setTitle(getTitle() + "#");

        switch (view.getId()) {

            case R.id.gc:
                Runtime.getRuntime().gc();
                break;

            case R.id.runFinalization:
                Runtime.getRuntime().runFinalization();
                break;

            // -------------------------------

            case R.id.exit:
                Runtime.getRuntime().exit(getExitCode());
                break;

            // -------------------------------

            case R.id.addShutdownHook:
                addShutdownHook();
                break;
        }
    }

    // ---------------------------------------------------------------------------------

    public void addShutdownHook() {
        HookThread a = new HookThread("Thread-A");
        HookThread b = new HookThread("Thread-B");
        HookThread hook = new HookThread("Thread-Hook");

        Runtime.getRuntime().addShutdownHook(hook);
        // Runtime.getRuntime().removeShutdownHook(hook);

        a.start();
        b.start();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                // 不能执行Hook线程
                // Process.killProcess(Process.myPid());

                // 可以执行Hook线
                System.exit(0);
            }
        }, 800);
    }

    public class HookThread extends Thread {

        public HookThread(String threadName) {
            super(threadName);
        }

        @Override
        public void run() {
            super.run();

            Log.d(TAG, "HookThread: threadName=" + Thread.currentThread().getName());
        }
    }

    // ---------------------------------------------------------------------------------

//    Runtime.getRuntime().exec("");
//    Runtime.getRuntime().halt(0);
//    Runtime.getRuntime().load("");
//    Runtime.getRuntime().loadLibrary("");
//    Runtime.getRuntime().traceInstructions(true);
//    Runtime.getRuntime().traceMethodCalls(true);
//    Runtime.getRuntime().getLocalizedInputStream(null);
//    Runtime.getRuntime().getLocalizedOutputStream(null);
}
