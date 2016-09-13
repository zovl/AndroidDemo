package zovl.zhongguanhua.media.demo.compenent.service;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import zovl.zhongguanhua.framework.lib.framework.BaseService;
import zovl.zhongguanhua.media.demo.ui.notification.ScreenNotification;

public class ScreenService extends BaseService {

    public static void startService(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ScreenService.class);
        context.startService(intent);
    }

    public static void stopService(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ScreenService.class);
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

        ScreenNotification.getInstance().startForeground(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ScreenNotification.getInstance().destruction();
    }
}
