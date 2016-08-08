package zovl.zhongguanhua.framework.lib.framework;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public abstract class BaseReceiver extends BroadcastReceiver {

    protected final String tag = this.getClass().getSimpleName();

    public BaseReceiver() {
        super();
    }

    @Override
    public IBinder peekService(Context context, Intent intent) {
        Log.d(tag, "peekService: // ----------------------------------------------");
        Log.d(tag, "peekService: context=" + context);
        Log.d(tag, "peekService: intent=" + intent);
        return super.peekService(context, intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(tag, "onReceive: // ----------------------------------------------");
        Log.d(tag, "onReceive: context=" + context);
        Log.d(tag, "onReceive: intent=" + intent);
    }
}
