package zovl.zhongguanhua.system.demo.logic;

import android.util.Log;

import zovl.zhongguanhua.framework.lib.utils.FormatUtil;

/**
 * 功能：系统工具
 */
public class RuntimeHelper {

	public static final String TAG = RuntimeHelper.class.getSimpleName();

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	public static String memory() {
		Log.d(TAG, "----------------[memory]------------------");

		int availableProcessors = Runtime.getRuntime().availableProcessors();
		long freeMemory = Runtime.getRuntime().freeMemory();
		long totalMemory = Runtime.getRuntime().totalMemory();
		long maxMemory = Runtime.getRuntime().maxMemory();

		StringBuffer buffer = new StringBuffer();
		buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");

		buffer.append("availableProcessors=" + availableProcessors);
		buffer.append("totalMemory=" + totalMemory + "byte" +  "--" + FormatUtil.format(totalMemory));
		buffer.append("freeMemory=" + freeMemory + "byte" + "--" + FormatUtil.format(freeMemory));
		buffer.append("maxMemory=" + maxMemory + "byte" + "--" + FormatUtil.format(maxMemory));

		Log.d(TAG, "memory: " + buffer.toString() + "\n");
		return buffer.toString();
	}
}
