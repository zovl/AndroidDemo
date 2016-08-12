package zovl.zhongguanhua.system.demo.logic;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.util.Log;

/**
 * 功能：电源工具
 */
public class BatteryHelper {

	public static final String TAG = BatteryHelper.class.getSimpleName();

	// ---------------------------------------------------------------------------------

	@TargetApi(21)
	public static String property(Context context) {
		Log.d(TAG, "----------------[property]------------------");

		if (context != null) {
			BatteryManager manager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);

			String toString = manager.toString();
			int _1 = manager.getIntProperty(1);
			int _2 = manager.getIntProperty(2);

			StringBuffer buffer = new StringBuffer();
			buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");
			buffer.append("toString=" + toString + "\n");
			buffer.append("1=" + _1 + "\n");
			buffer.append("2=" + _2 + "\n");

			Log.d(TAG, "property: " + buffer.toString() + "\n");

			return buffer.toString();
		}
		return "context is null";
	}
	
	/**
	 * 获取电量
	 */
	public static Intent intent(Context context) {
		IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		Intent intent = context.registerReceiver(null, intentFilter);
		return intent;
	}

	public static String printIntent(Intent intent) {
		Log.d(TAG, "----------------[printIntent]------------------");

		if (intent != null) {
			String toString = intent.toString();

			int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
			int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);

			int iconSmall = intent.getIntExtra(BatteryManager.EXTRA_ICON_SMALL, -1);
			int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
			int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
			boolean present = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);
			int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
			int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
			String technology = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
			int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);

			StringBuffer buffer = new StringBuffer();
			buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");
			buffer.append("toString=" + toString + "\n");
			buffer.append("health=" + health + "\n");
			buffer.append("voltage=" + voltage + "\n");
			buffer.append("iconSmall=" + iconSmall + "\n");
			buffer.append("level=" + level + "\n");
			buffer.append("plugged=" + plugged + "\n");
			buffer.append("present=" + present + "\n");
			buffer.append("scale=" + scale + "\n");
			buffer.append("status=" + status + "\n");
			buffer.append("technology=" + technology + "\n");
			buffer.append("temperature=" + temperature + "\n");

			Log.d(TAG, "printIntent: " + buffer.toString() + "\n");

			return buffer.toString();
		}
		return "intent is null";
	}
}
