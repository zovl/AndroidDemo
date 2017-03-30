package zovl.zhongguanhua.compenent.demo.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import zovl.zhongguanhua.compenent.demo.activity.ServiceActivity;
import zovl.zhongguanhua.framework.lib.framework.BaseService;

public class ReceiverService extends BaseService {

    public static final String TAG = ReceiverService.class.getSimpleName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerReceiver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
    }

    public static void startService(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ReceiverService.class);
        context.startService(intent);
    }

    public static void stopService(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ReceiverService.class);
        context.stopService(intent);
    }

    // ---------------------------------------------------------------------------------

    private Receiver receiver;

    private void registerReceiver() {
        receiver = new Receiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.Receiver");
        registerReceiver(receiver, filter);
        Log.d(tag, "registerReceiver: ");
    }

    private void unregisterReceiver() {
        if (receiver != null) {
            unregisterReceiver(receiver);
            Log.d(tag, "registerReceiver: ");
        }
    }

    public class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ServiceActivity.printProcess(tag, context);
            Log.d(tag, "onReceive: context=" + context);
            Log.d(tag, "onReceive: intent=" + intent);
            Log.d(tag, "onReceive: action=" + intent.getAction());
            Log.d(tag, "onReceive: msg=" + intent.getStringExtra("msg"));
        }
    }
}
