package zovl.zhongguanhua.framework.lib.framework;


import android.annotation.TargetApi;
import android.app.Application;
import android.os.Build;
import android.util.Log;

/**
 * 基本应用程序
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public abstract class BaseApplication extends Application {

    protected final String tag = this.getClass().getSimpleName();

	@Override
	public void onCreate() {
		Log.d(tag, "onCreate: // ----------------------------------------------");
		super.onCreate();
		AppManager.getInstance().setApplication(this);
	}

	@Override
	public void onTrimMemory(int level) {
		Log.d(tag, "onTrimMemory: // ----------------------------------------------");
		Log.d(tag, "onTrimMemory: level=" + level);
		super.onTrimMemory(level);
	}

	@Override
	public void onLowMemory() {
		Log.d(tag, "onLowMemory: // ----------------------------------------------");
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		Log.d(tag, "onTerminate: // ----------------------------------------------");
		super.onTerminate();
	}
}
