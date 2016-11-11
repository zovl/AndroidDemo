package zovl.zhongguanhua.component.demo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import zovl.zhongguanhua.component.demo.activity.ServiceActivity;

public class Receiver extends BroadcastReceiver {
    
    public static final String TAG = Receiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        ServiceActivity.printProcess(TAG, context);
        Log.d(TAG, "onReceive: context=" + context);
        Log.d(TAG, "onReceive: intent=" + intent);
        Log.d(TAG, "onReceive: action=" + intent.getAction());
        Log.d(TAG, "onReceive: msg=" + intent.getStringExtra("msg"));
    }
}
