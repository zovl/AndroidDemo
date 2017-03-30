package zovl.zhongguanhua.compenent.demo.service;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import zovl.zhongguanhua.compenent.demo.activity.ServiceActivity;
import zovl.zhongguanhua.framework.lib.framework.BaseService;
import zovl.zhongguanhua.framework.lib.utils.ToastHelper;

public class ActivityService extends BaseService {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(tag, "onStartCommand: action=" + intent.getStringExtra("action"));
        if (intent.getStringExtra("action") != null &&
                intent.getStringExtra("action").equals("stopSelf")) {
            stopSelf();
        }
        ServiceActivity.printProcess(tag, this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(tag, "onBind: intent=" + intent);
        Log.d(tag, "onBind: action=" + intent.getStringExtra("action"));
        ServiceActivity.printProcess(tag, this);
        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(tag, "onUnbind: intent=" + intent);
        Log.d(tag, "onUnbind: action=" + intent.getStringExtra("action"));
        ServiceActivity.printProcess(tag, this);
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(tag, "onRebind: intent=" + intent);
        Log.d(tag, "onRebind: action=" + intent.getStringExtra("action"));
        ServiceActivity.printProcess(tag, this);
        super.onRebind(intent);
    }

    public class MyBinder extends Binder {

        public ActivityService getService(){
            return ActivityService.this;
        }

        public String getValue() {
            return "Binder get value";
        }

        public void setValue(String value) {
            ToastHelper.l(value);
        }
    }

    private MyBinder myBinder = new MyBinder();

    public String getValue() {
        return "Service get value";
    }

    public void setValue(String value) {
        ToastHelper.l(value);
    }
}
