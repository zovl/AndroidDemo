package zovl.zhongguanhua.system.demo.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.utils.ViewUtil;
import zovl.zhongguanhua.system.demo.R;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.framework.lib.utils.FormatUtil;

public class RuntimeActivity extends TBaseActivity {

    @Bind(R.id.exitText)
    TextView exitText;

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
                gc();
                break;

            case R.id.runFinalization:
                runFinalization();
                break;

            // -------------------------------

            case R.id.exit:
                exit(Integer.valueOf(exitText.getText().toString()));
                break;

            // -------------------------------

            case R.id.memory:
                memory();
                setText();
                break;

            // -------------------------------

            case R.id.addShutdownHook:
                addShutdownHook();
                break;
        }
    }

    // ---------------------------------------------------------------------------------

    private void memory() {

        log("memory: // --------------------------------------------------------");
        
        int availableProcessors = Runtime.getRuntime().availableProcessors();

        long freeMemory = Runtime.getRuntime().freeMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();

        log("memory: availableProcessors=" + availableProcessors);
        log("");
        log("memory: totalMemory=" + FormatUtil.format(totalMemory));
        log("memory: freeMemory=" + FormatUtil.format(freeMemory));
        log("memory: maxMemory=" + FormatUtil.format(maxMemory));
        log("");
        log("memory: totalMemory=" + totalMemory + "byte");
        log("memory: freeMemory=" + freeMemory + "byte");
        log("memory: maxMemory=" + maxMemory + "byte");
    }

    // ---------------------------------------------------------------------------------

    private void gc() {

        log("gc: // --------------------------------------------------------");

        Runtime.getRuntime().gc();
    }

    private void runFinalization() {

        log("runFinalization: // --------------------------------------------------------");

        Runtime.getRuntime().runFinalization();
    }

    private void runFinalizersOnExit(boolean run) {

        log("runFinalizersOnExit: // --------------------------------------------------------");

        Runtime.runFinalizersOnExit(run);
    }

    private void exit(int code) {

        log("exit: // --------------------------------------------------------");

        Runtime.getRuntime().exit(code);
    }

    // ---------------------------------------------------------------------------------

    private void addShutdownHook() {
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

    private class HookThread extends Thread {

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

    private void a() {

        try {
            Runtime.getRuntime().exec("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Runtime.getRuntime().halt(0);
        Runtime.getRuntime().load("");
        Runtime.getRuntime().loadLibrary("");
        Runtime.getRuntime().traceInstructions(true);
        Runtime.getRuntime().traceMethodCalls(true);

        Runtime.getRuntime().getLocalizedInputStream(null);
        Runtime.getRuntime().getLocalizedOutputStream(null);
    }

}
