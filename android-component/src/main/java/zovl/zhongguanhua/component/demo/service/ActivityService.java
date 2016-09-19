package zovl.zhongguanhua.component.demo.service;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import zovl.zhongguanhua.framework.lib.framework.BaseService;
import zovl.zhongguanhua.framework.lib.utils.ToastHelper;

public class ActivityService extends BaseService {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

        if (intent != null) {
            String action = intent.getStringExtra("action");
            if (action != null) {
                ToastHelper.l(action);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
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
