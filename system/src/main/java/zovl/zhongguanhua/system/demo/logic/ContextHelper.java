package zovl.zhongguanhua.system.demo.logic;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

/**
 * 功能：系统工具
 */
public class ContextHelper {

	public static final String TAG = ContextHelper.class.getSimpleName();

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	public static String context(Context context) {
		Log.d(TAG, "----------------[context]------------------");

		if (context != null) {
			StringBuffer buffer = new StringBuffer();
			buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");

			buffer.append("context=" + context + "\n");
			buffer.append("applicationContext=" + context.getApplicationContext() + "\n");
			buffer.append("packageCodePath=" + context.getPackageCodePath() + "\n");
			buffer.append("packageResourcePath=" + context.getPackageResourcePath() + "\n");
			buffer.append("packageName=" + context.getPackageName() + "\n");
			buffer.append("theme=" + context.getTheme() + "\n");
			buffer.append("wallpaper=" + context.getWallpaper() + "\n");
			String[] databases = context.databaseList();
			for (String database : databases) {
                buffer.append("database=" + database + "\n");
            }
			String[] files = context.fileList();
			for (String file : files) {
                buffer.append("file=" + file + "\n");
            }
			ApplicationInfo applicationInfo = context.getApplicationInfo();
			buffer.append("applicationInfo=" + applicationInfo + "\n");

			Log.d(TAG, "context: " + buffer.toString() + "\n");
			return buffer.toString();
		}
		return "context is null";
	}
}
