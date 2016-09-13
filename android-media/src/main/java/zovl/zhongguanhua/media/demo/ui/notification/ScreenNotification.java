package zovl.zhongguanhua.media.demo.ui.notification;

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

import java.io.File;
import java.lang.reflect.Method;

import zovl.zhongguanhua.framework.lib.framework.AppManager;
import zovl.zhongguanhua.framework.lib.utils.ScreenUtil;
import zovl.zhongguanhua.framework.lib.utils.StorageUtil;
import zovl.zhongguanhua.framework.lib.utils.ToastHelper;
import zovl.zhongguanhua.media.demo.R;
import zovl.zhongguanhua.media.demo.logic.Configuration;
import zovl.zhongguanhua.media.demo.logic.ScreenCaptureActivity;
import zovl.zhongguanhua.media.demo.logic.ScreenRecordActivitya;
import zovl.zhongguanhua.media.demo.logic.ScreenRecordActivityb;

public class ScreenNotification {

    private static final String TAG = ScreenNotification.class.getSimpleName();
    private static final String ACTION = ScreenNotification.class.getName();

    private static ScreenNotification instance;

    public static ScreenNotification getInstance() {
        if (instance == null)
            synchronized (ScreenNotification.class) {
                if (instance == null)
                    instance = new ScreenNotification();
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

    public final static String ACTION_BUTTON = ACTION;
    public final static String EXTRA_BUTTON = "BUTTON";
    public final static int EXTRA_BUTTON_A = 1;

    public final static int EXTRA_BUTTON_B = 2;
    public final static int EXTRA_BUTTON_C = 3;
    public final static int EXTRA_BUTTON_D = 4;
    public final static int EXTRA_BUTTON_E = 5;

    public final static int EXTRA_BUTTON_F = 6;
    public final static int EXTRA_BUTTON_G = 7;
    public final static int EXTRA_BUTTON_H = 8;
    public final static int EXTRA_BUTTON_I = 9;

    public static final int NOTIFICATION_ID = 200;

    private Context context;
    private NotificationManager manager;
    private RemoteViews remoteViews;
    private Notification notification;
    private String packageName;
    private Receiver receiver;
    private Handler handler;
    private Service service;

    private ScreenNotification() {
        super();

        context = AppManager.getInstance().getContext();
        packageName = context.getPackageName();
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        handler = new Handler(Looper.myLooper());
        remoteViews = new RemoteViews(packageName, R.layout.notification_screen);
        if (Build.VERSION.SDK_INT >= 16) {
            int size = ScreenUtil.dp2px(5);
            remoteViews.setViewPadding(R.id.root, 0, size, 0, size);
        }

        Intent intent = new Intent();
        intent.setAction(ACTION_BUTTON);
        PendingIntent p;

        // a
        intent.putExtra(EXTRA_BUTTON, EXTRA_BUTTON_A);
        p = PendingIntent.getBroadcast(context, EXTRA_BUTTON_A, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.screen_a, p);

        // b
        intent.putExtra(EXTRA_BUTTON, EXTRA_BUTTON_B);
        p = PendingIntent.getBroadcast(context, EXTRA_BUTTON_B, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.screen_b, p);
        // c
        intent.putExtra(EXTRA_BUTTON, EXTRA_BUTTON_C);
        p = PendingIntent.getBroadcast(context, EXTRA_BUTTON_C, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.screen_c, p);
        // d
        intent.putExtra(EXTRA_BUTTON, EXTRA_BUTTON_D);
        p = PendingIntent.getBroadcast(context, EXTRA_BUTTON_D, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.screen_d, p);
        // e
        intent.putExtra(EXTRA_BUTTON, EXTRA_BUTTON_E);
        p = PendingIntent.getBroadcast(context, EXTRA_BUTTON_E, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.screen_e, p);

        // f
        intent.putExtra(EXTRA_BUTTON, EXTRA_BUTTON_F);
        p = PendingIntent.getBroadcast(context, EXTRA_BUTTON_F, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.screen_f, p);
        // g
        intent.putExtra(EXTRA_BUTTON, EXTRA_BUTTON_G);
        p = PendingIntent.getBroadcast(context, EXTRA_BUTTON_G, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.screen_g, p);
        // h
        intent.putExtra(EXTRA_BUTTON, EXTRA_BUTTON_H);
        p = PendingIntent.getBroadcast(context, EXTRA_BUTTON_H, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.screen_h, p);
        // i
        intent.putExtra(EXTRA_BUTTON, EXTRA_BUTTON_I);
        p = PendingIntent.getBroadcast(context, EXTRA_BUTTON_I, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.screen_i, p);

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

    private File videoFile, videoFileb, imageFile;

    /**
     * 监听按钮广播
     */
    private class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, Intent intent) {
            String action = intent.getAction();
            int id = intent.getIntExtra(EXTRA_BUTTON, 0);
            if (action.equals(ACTION_BUTTON)) {
                switch (id) {

                    case EXTRA_BUTTON_A:
                        Log.d(TAG, "onReceive: a");

                        videoFile = StorageUtil.getRootFile("_video.mp4");
                        Configuration configuration = Configuration.DEFAULT;
                        configuration.setAudio(true);
                        configuration.setLandscape(true);
                        ScreenRecordActivitya.startRecording(context, videoFile.getPath(), configuration);
                        ToastHelper.s(videoFile.getAbsolutePath());

                        break;

                    case EXTRA_BUTTON_B:
                        Log.d(TAG, "onReceive: b");

                        ScreenRecordActivitya.pauseRecording();

                        break;

                    case EXTRA_BUTTON_C:
                        Log.d(TAG, "onReceive: c");

                        ScreenRecordActivitya.restartRecording();

                        break;

                    case EXTRA_BUTTON_D:
                        Log.d(TAG, "onReceive: d");

                        ScreenRecordActivitya.stopRecording();
                        ToastHelper.s(videoFile.getAbsolutePath());

                        break;

                    case EXTRA_BUTTON_E:
                        Log.d(TAG, "onReceive: e");

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                imageFile = StorageUtil.getRootFile("_image.png");
                                ScreenCaptureActivity.startCapture(context, imageFile.getPath());
                                ToastHelper.s(imageFile.getAbsolutePath());
                            }
                        }, 1200);

                        break;

                    case EXTRA_BUTTON_F:
                        Log.d(TAG, "onReceive: f");

                        videoFileb = StorageUtil.getRootFile("_video.mp4");
                        configuration = Configuration.DEFAULT;
                        configuration.setAudio(true);
                        configuration.setLandscape(true);
                        ScreenRecordActivityb.startRecording(context, videoFileb.getPath(), configuration);
                        ToastHelper.s(videoFileb.getAbsolutePath());

                        break;

                    case EXTRA_BUTTON_G:
                        Log.d(TAG, "onReceive: g");

                        ScreenRecordActivityb.pauseRecording();

                        break;

                    case EXTRA_BUTTON_H:
                        Log.d(TAG, "onReceive: h");

                        ScreenRecordActivityb.restartRecording();

                        break;

                    case EXTRA_BUTTON_I:
                        Log.d(TAG, "onReceive: i");

                        ScreenRecordActivityb.stopRecording();
                        ToastHelper.s(videoFileb.getAbsolutePath());

                        break;
                }
                closeNotification();
            }
        }
    }
}
