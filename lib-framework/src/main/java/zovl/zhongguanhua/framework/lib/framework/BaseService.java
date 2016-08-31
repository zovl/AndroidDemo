package zovl.zhongguanhua.framework.lib.framework;

import android.app.Service;
import android.content.Intent;
import android.util.Log;

/**
 * 基本服务
 */
public abstract class BaseService extends Service {
	
	protected final String action = this.getClass().getName();
	protected final String tag = this.getClass().getSimpleName();
    
    @Override
    public void onCreate() {
        Log.d(tag, "onCreate: // ------------------------------------------------------------");
    	super.onCreate();
        AppManager.getInstance().addService(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(tag, "onCreate: // ------------------------------------------------------------");
        Log.d(tag, "onCreate: intent=" + intent);
        Log.d(tag, "onCreate: flags=" + flags);
        Log.d(tag, "onCreate: startId=" + startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(tag, "onDestroy: // ------------------------------------------------------------");
        AppManager.getInstance().removeService(this);
    	super.onDestroy();
    }
}
