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
	public static void property(Context context) {
		Log.d(TAG, "property: // ----------------------------------------------");
		BatteryManager manager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
		Log.d(TAG, "property: manager=" + manager);

		int health = manager.getIntProperty(1);
		int voltage = manager.getIntProperty(2);

		Log.d(TAG, "onReceive: 1=" + health);
		Log.d(TAG, "onReceive: 2=" + voltage);
	}
	
	/**
	 * 获取电量
	 */
	public static Intent intent(Context context) {
		//通过粘性广播读取电量
		// 注意，粘性广播不需要广播接收器
		IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		Intent intent = context.registerReceiver(null, intentFilter);
		printIntent(intent);
		return intent;
	}

	public static void printIntent(Intent intent) {
		Log.d(TAG, "printIntent: // ----------------------------------------------");
		Log.d(TAG, "printIntent: intent=" + intent);

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

		Log.d(TAG, "onReceive: health=" + health);
		Log.d(TAG, "onReceive: voltage=" + voltage);
		Log.d(TAG, "onReceive: iconSmall=" + iconSmall);
		Log.d(TAG, "onReceive: level=" + level);
		Log.d(TAG, "onReceive: plugged=" + plugged);
		Log.d(TAG, "onReceive: present=" + present);
		Log.d(TAG, "onReceive: scale=" + scale);
		Log.d(TAG, "onReceive: status=" + status);
		Log.d(TAG, "onReceive: technology=" + technology);
		Log.d(TAG, "onReceive: temperature=" + temperature);
	}
}
