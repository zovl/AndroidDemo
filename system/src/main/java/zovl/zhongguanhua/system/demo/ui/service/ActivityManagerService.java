package zovl.zhongguanhua.system.demo.ui.service;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import zovl.zhongguanhua.framework.lib.framework.BaseService;
import zovl.zhongguanhua.system.demo.ui.notification.ActivityManagerNotification;

public class ActivityManagerService extends BaseService {

    public static void startService(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ActivityManagerService.class);
        context.startService(intent);
    }

    public static void stopService(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ActivityManagerService.class);
        context.stopService(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ActivityManagerNotification.getInstance().startForeground(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ActivityManagerNotification.getInstance().destruction();
    }
}
