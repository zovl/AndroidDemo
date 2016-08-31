package zovl.zhongguanhua.system.demo.ui.notification;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import java.lang.reflect.Method;
import java.util.List;

import zovl.zhongguanhua.framework.lib.framework.AppManager;
import zovl.zhongguanhua.system.demo.R;
import zovl.zhongguanhua.system.demo.logic.ActivityHelper;
import zovl.zhongguanhua.system.demo.logic.ScreenUtil;

public class ActivityManagerNotification {

    private static final String TAG = ActivityManagerNotification.class.getSimpleName();

    private static ActivityManagerNotification instance;

    public static ActivityManagerNotification getInstance() {
        if (instance == null)
            synchronized (ActivityManagerNotification.class) {
                if (instance == null)
                    instance = new ActivityManagerNotification();
            }
        return instance;
    }

    /**
     * 销毁通知栏
     */
    public static void destruction() {
        if (instance != null)
            instance.destroy();
        instance = null;
    }

    // -------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------

    public final static String ACTION_BUTTON = "zovl.zhongguanhua.system.demo.ui.notification.ActivityManagerNotification";
    public final static String EXTRA_BUTTON = "BUTTON";
    public final static int EXTRA_BUTTON_A = 1;
    public final static int EXTRA_BUTTON_B = 2;
    public final static int EXTRA_BUTTON_C = 3;
    public final static int EXTRA_BUTTON_D = 4;

    public static final int NOTIFICATION_ID = 200;

    private Context context;
    private NotificationManager manager;
    private RemoteViews remoteViews;
    private Notification notification;
    private String packageName;
    private Receiver receiver;
    private Handler handler;
    private Service service;

    private ActivityManagerNotification() {
        super();

        context = AppManager.getInstance().getContext();
        packageName = context.getPackageName();
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        handler = new Handler(Looper.myLooper());
        remoteViews = new RemoteViews(packageName, R.layout.notification_activitymanager);
        if (Build.VERSION.SDK_INT >= 16) {
            int size = ScreenUtil.dp2px(5);
            remoteViews.setViewPadding(R.id.root, 0, size, 0, size);
        }

        Intent intent = new Intent();
        intent.setAction(ACTION_BUTTON);
        PendingIntent p;
        // a
        intent.putExtra(EXTRA_BUTTON, EXTRA_BUTTON_A);
        p = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.activitymanager_a, p);
        // b
        intent.putExtra(EXTRA_BUTTON, EXTRA_BUTTON_B);
        p = PendingIntent.getBroadcast(context, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.activitymanager_b, p);
        // c
        intent.putExtra(EXTRA_BUTTON, EXTRA_BUTTON_C);
        p = PendingIntent.getBroadcast(context, 3, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.activitymanager_c, p);
        // d
        intent.putExtra(EXTRA_BUTTON, EXTRA_BUTTON_D);
        p = PendingIntent.getBroadcast(context, 4, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.activitymanager_d, p);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, new Intent(), Notification.FLAG_ONGOING_EVENT);

        notification = new NotificationCompat.Builder(context)
                .setContent(remoteViews)
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        manager.notify(NOTIFICATION_ID, notification);

        registerReceiver();
    }

    /**
     * 启动前台服务
     */
    public void startForeground(Service service) {
        if (service != null)
            service.startForeground(NOTIFICATION_ID, notification);
    }

    /**
     * 销毁前台服务
     */
    public void stopForeground() {
        if (service != null)
            service.stopForeground(true);
    }

    /**
     * 销毁通知栏
     */
    private void destroy() {
        cancel();
        stopForeground();
        unregisterReceiver();
        closeNotification();
        if (handler != null)
            handler.removeCallbacksAndMessages(null);
    }

    /**
     * 清除通知栏
     */
    private void cancel() {
        if (manager != null) {
            manager.cancel(NOTIFICATION_ID);
        }
    }

    /**
     * 收起通知栏
     */
    private void closeNotification() {
        try {
            Object statusBarManager = context.getSystemService("statusbar");
            Method collapse;
            if (Build.VERSION.SDK_INT <= 16) {
                collapse = statusBarManager.getClass().getMethod("collapse");
            } else {
                collapse = statusBarManager.getClass().getMethod("collapsePanels");
            }
            collapse.invoke(statusBarManager);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    /**
     * 注册广播
     */
    private void registerReceiver() {
        receiver = new Receiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_BUTTON);
        context.registerReceiver(receiver, filter);
    }

    /**
     * 反注册广播
     */
    private void unregisterReceiver() {
        if (receiver != null)
            try {
                context.unregisterReceiver(receiver);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
    }

    /**
     * 监听按钮广播
     */
    private class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int id = intent.getIntExtra(EXTRA_BUTTON, 0);
            if (action.equals(ACTION_BUTTON)) {
                switch (id) {

                    case EXTRA_BUTTON_A:
                        Log.d(TAG, "onReceive: a");

                        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos = ActivityHelper.runningAppProcesses(context);
                        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcessInfos) {
                            ActivityHelper.runningAppProcessInfo(runningAppProcessInfo);
                        }
                        break;

                    case EXTRA_BUTTON_B:
                        Log.d(TAG, "onReceive: b");
                        List<ActivityManager.RunningTaskInfo> runningTaskInfos = ActivityHelper.runningTasks(context);
                        for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTaskInfos) {
                            ActivityHelper.runningTaskInfo(runningTaskInfo);
                        }
                        break;

                    case EXTRA_BUTTON_C:
                        Log.d(TAG, "onReceive: c");
                        List<ActivityManager.AppTask> appTasks = ActivityHelper.appTasks(context);
                        for (ActivityManager.AppTask appTask : appTasks) {
                            ActivityHelper.appTask(appTask);
                        }
                        break;

                    case EXTRA_BUTTON_D:
                        Log.d(TAG, "onReceive: d");
                        List<ActivityManager.RunningServiceInfo> runningServiceInfos = ActivityHelper.runningServices(context);
                        for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServiceInfos) {
                            ActivityHelper.runningServiceInfo(runningServiceInfo);
                        }
                        break;
                }
                closeNotification();
            }
        }
    }
}
