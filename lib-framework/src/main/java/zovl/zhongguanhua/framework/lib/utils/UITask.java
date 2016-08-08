package zovl.zhongguanhua.framework.lib.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * 功能：UI线程任务
 */
public class UITask {

    public interface Callback {
        void handleMessage(Message msg);
    }

    private static Handler handler = new Handler(Looper.getMainLooper());

    public static final void post(Runnable r) {
        handler.post(r);
    }

    public static final void postAtTime(Runnable r, long uptimeMillis) {
        handler.postAtTime(r, uptimeMillis);
    }

    public static final void postAtTime(Runnable r, Object token, long uptimeMillis) {
        handler.postAtTime(r, token, uptimeMillis);
    }

    public static final void postDelayed(Runnable r, long delayMillis) {
        handler.postDelayed(r, delayMillis);
    }

    public static final void postAtFrontOfQueue(Runnable r) {
        handler.postAtFrontOfQueue(r);
    }
/*
    public static final void sendEmptyMessage(int what) {
        handler.sendEmptyMessage(what);
    }

    public static final void sendEmptyMessageAtTime(int what, long uptimeMillis) {
        handler.sendEmptyMessageAtTime(what, uptimeMillis);
    }

    public static final void sendEmptyMessage(int what, long delayMillis) {
        handler.sendEmptyMessageDelayed(what, delayMillis);
    }

    public static final void sendMessage(Message msg) {
        handler.sendMessage(msg);
    }

    public static final void sendMessageAtFrontOfQueue(Message msg) {
        handler.sendMessageAtFrontOfQueue(msg);
    }

    public static final void sendMessageAtTime(Message msg, long uptimeMillis) {
        handler.sendMessageAtTime(msg, uptimeMillis);
    }

    public static final void sendMessageDelayed(Message msg, long delayMillis) {
        handler.sendMessageDelayed(msg, delayMillis);
    }*/
}
