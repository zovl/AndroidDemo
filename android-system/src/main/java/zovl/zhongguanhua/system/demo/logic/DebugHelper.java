package zovl.zhongguanhua.system.demo.logic;

import android.os.Debug;
import android.util.Log;

import zovl.zhongguanhua.framework.lib.utils.FormatUtil;

/**
 * 功能：系统工具
 */
public class DebugHelper {

	public static final String TAG = DebugHelper.class.getSimpleName();

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	public static String nativeMemory() {
		Log.d(TAG, "----------------[nativeMemory]------------------");

		StringBuffer buffer = new StringBuffer();
		buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");

		long nativeHeapSize = Debug.getNativeHeapSize();
		long nativeHeapAllocatedSize = Debug.getNativeHeapAllocatedSize();
		long nativeHeapFreeSize = Debug.getNativeHeapFreeSize();

		buffer.append("nativeHeapSize=" + nativeHeapSize + "byte" + "--" + FormatUtil.format(nativeHeapSize) + "\n");
		buffer.append("nativeHeapAllocatedSize=" + nativeHeapAllocatedSize + "byte" + "--" + FormatUtil.format(nativeHeapAllocatedSize) + "\n");
		buffer.append("nativeHeapFreeSize=" + nativeHeapFreeSize + "byte" + "--" + FormatUtil.format(nativeHeapFreeSize) + "\n");

		Log.d(TAG, "context: " + buffer.toString() + "\n" + "\n");
		return buffer.toString();
	}
}
