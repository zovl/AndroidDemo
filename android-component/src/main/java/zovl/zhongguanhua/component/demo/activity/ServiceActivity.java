package zovl.zhongguanhua.component.demo.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;
import android.view.View;
import butterknife.OnClick;
import zovl.zhongguanhua.component.demo.R;
import zovl.zhongguanhua.component.demo.service.ActivityService;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.framework.lib.utils.ToastHelper;
import zovl.zhongguanhua.framework.lib.utils.ViewUtil;

public class ServiceActivity extends TBaseActivity {

    @Override
    public int getContentView() {
        return R.layout.activity_service;
    }

    @OnClick({R.id.startService,
            R.id.stopService,
            R.id.stopSelf,
            R.id.bindService,
            R.id.unbindService})
    public void onClick(View view) {
        printProcess(tag, this);
        switch (view.getId()) {

            case R.id.startService:
                startService(this);
                break;

            case R.id.stopService:
                stopService(this);
                break;

            case R.id.stopSelf:
                stopSelf(this);
                break;

            case R.id.bindService:
                ViewUtil.enabled(view, 5000);

                bindService(this);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (binder != null) {
                    ToastHelper.l(binder.getValue());
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (binder != null) {
                    binder.setValue("Binder set value");
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (service != null) {
                    ToastHelper.l(service.getValue());
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (service != null) {
                    service.setValue("Service set value");
                }

                break;

            case R.id.unbindService:
                unbindService(this);
                break;
        }
    }

    // ---------------------------------------------------------------------------------

    private void startService(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ActivityService.class);
        intent.putExtra("action", "start Service");
        context.startService(intent);
    }

    private void stopService(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ActivityService.class);
        intent.putExtra("action", "stop Service");
        context.stopService(intent);
    }

    private void stopSelf(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ActivityService.class);
        intent.putExtra("action", "stopSelf");
        context.startActivity(intent);
    }

    private void bindService(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ActivityService.class);
        intent.putExtra("action", "bind Service");
        context.bindService(intent, connection, BIND_AUTO_CREATE);
    }

    private void unbindService(Context context) {
        if (connection != null) {
            context.unbindService(connection);
        }
    }

    private ActivityService service;
    private ActivityService.MyBinder binder;

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            Log.d(tag, "onServiceConnected: name=" + name);

            try {
                binder = (ActivityService.MyBinder) iBinder;
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (binder != null) {
                service = binder.getService();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(tag, "onServiceConnected: name=" + name);
            binder = null;
            service = null;
        }
    };

    // ---------------------------------------------------------------------------------

    public static void printProcess(String tag, Context context) {
        Log.d(tag, "printProcess: pid=" + Process.myPid()+
                "--" + "uid=" + Process.myUid() +
                "--" + "tid=" + Process.myTid() +
                "--" + "elapsedCpuTime=" + Process.getElapsedCpuTime());
    }
}
