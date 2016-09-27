package zovl.zhongguanhua.system.demo.logic;

import android.os.Build;
import android.os.Debug;
import android.util.Log;

import java.util.Map;

import zovl.zhongguanhua.framework.lib.utils.FormatUtil;

/**
 * 功能：系统工具
 */
public class MemoryHelper {

	public static final String TAG = MemoryHelper.class.getSimpleName();

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	public static String runtimeMemory() {
		Log.d(TAG, "----------------[runtimeMemory]------------------");

		int availableProcessors = Runtime.getRuntime().availableProcessors();
		long freeMemory = Runtime.getRuntime().freeMemory();
		long totalMemory = Runtime.getRuntime().totalMemory();
		long maxMemory = Runtime.getRuntime().maxMemory();

		StringBuffer buffer = new StringBuffer();
		buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");

		buffer.append("availableProcessors=" + availableProcessors + "\n");
		buffer.append("totalMemory=" + totalMemory + "byte" +  "--" + FormatUtil.format(totalMemory) + "\n");
		buffer.append("freeMemory=" + freeMemory + "byte" + "--" + FormatUtil.format(freeMemory) + "\n");
		buffer.append("maxMemory=" + maxMemory + "byte" + "--" + FormatUtil.format(maxMemory) + "\n");

		Log.d(TAG, "runtimeMemory: " + buffer.toString() + "\n");
		return buffer.toString();
	}

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	public static String debugNativeMemory() {
		Log.d(TAG, "----------------[debugNativeMemory]------------------");

		long nativeHeapSize = Debug.getNativeHeapSize();
		long nativeHeapAllocatedSize = Debug.getNativeHeapAllocatedSize();
		long nativeHeapFreeSize = Debug.getNativeHeapFreeSize();

		StringBuffer buffer = new StringBuffer();
		buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");

		buffer.append("nativeHeapSize=" + nativeHeapSize + "byte" +  "--" + FormatUtil.format(nativeHeapSize) + "\n");
		buffer.append("nativeHeapAllocatedSize=" + nativeHeapAllocatedSize + "byte" + "--" + FormatUtil.format(nativeHeapAllocatedSize) + "\n");
		buffer.append("nativeHeapFreeSize=" + nativeHeapFreeSize + "byte" + "--" + FormatUtil.format(nativeHeapFreeSize) + "\n");

		Log.d(TAG, "debugNativeMemory: " + buffer.toString() + "\n");
		return buffer.toString();
	}

	public static String debugMemory() {
		Log.d(TAG, "----------------[debugMemory]------------------");

		long pss = Debug.getPss();

		long loadedClassCount = Debug.getLoadedClassCount();

		long globalAllocSize = Debug.getGlobalAllocSize();
		long globalFreedSize = Debug.getGlobalFreedSize();
		long globalAllocCount = Debug.getGlobalAllocCount();
		long globalFreedCount = Debug.getGlobalFreedCount();

		long globalExternalAllocSize = Debug.getGlobalExternalAllocSize();
		long globalExternalFreedSize = Debug.getGlobalExternalFreedSize();
		long globalExternalAllocCount = Debug.getGlobalExternalAllocCount();
		long globalExternalFreedCount = Debug.getGlobalExternalFreedCount();

		long globalClassInitCount = Debug.getGlobalClassInitCount();
		long globalClassInitTime = Debug.getGlobalClassInitTime();

		long globalGcInvocationCount = Debug.getGlobalGcInvocationCount();

		long threadAllocSize = Debug.getThreadAllocSize();
		long threadExternalAllocSize = Debug.getThreadExternalAllocSize();

		long threadAllocCount = Debug.getThreadAllocCount();
		long threadGcInvocationCount = Debug.getThreadGcInvocationCount();
		long threadExternalAllocCount = Debug.getThreadExternalAllocCount();

		StringBuffer buffer = new StringBuffer();
		buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");

		buffer.append("pss=" + pss + "\n");
		buffer.append("loadedClassCount=" + loadedClassCount + "\n");

		buffer.append("globalAllocSize=" + globalAllocSize + "byte" +  "--" + FormatUtil.format(globalAllocSize) + "\n");
		buffer.append("globalFreedSize=" + globalFreedSize + "byte" +  "--" + FormatUtil.format(globalFreedSize) + "\n");
		buffer.append("globalAllocCount=" + globalAllocCount + "\n");
		buffer.append("globalFreedCount=" + globalFreedCount + "\n");

		buffer.append("globalExternalAllocSize=" + globalExternalAllocSize + "byte" +  "--" + FormatUtil.format(globalExternalAllocSize) + "\n");
		buffer.append("globalExternalFreedSize=" + globalExternalFreedSize + "byte" +  "--" + FormatUtil.format(globalExternalFreedSize) + "\n");
		buffer.append("globalExternalAllocCount=" + globalExternalAllocCount + "\n");
		buffer.append("globalExternalFreedCount=" + globalExternalFreedCount + "\n");

		buffer.append("globalClassInitCount=" + globalClassInitCount + "\n");
		buffer.append("globalClassInitTime=" + globalClassInitTime + "\n");

		buffer.append("globalGcInvocationCount=" + globalGcInvocationCount + "\n");

		buffer.append("threadAllocSize=" + threadAllocSize + "byte" +  "--" + FormatUtil.format(threadAllocSize) + "\n");
		buffer.append("threadExternalAllocSize=" + threadExternalAllocSize + "byte" +  "--" + FormatUtil.format(threadExternalAllocSize) + "\n");
		buffer.append("threadAllocCount=" + threadAllocCount + "\n");
		buffer.append("threadGcInvocationCount=" + threadGcInvocationCount + "\n");
		buffer.append("threadExternalAllocCount=" + threadExternalAllocCount + "\n");

		Log.d(TAG, "debugMemory: " + buffer.toString() + "\n");
		return buffer.toString();
	}

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	public static String debugStats() {
		Log.d(TAG, "----------------[debugStats]------------------");

		if (Build.VERSION.SDK_INT >= 23) {
			Map<String, String> stats = Debug.getRuntimeStats();

			if (stats !=  null && stats.size() > 0) {
				StringBuffer buffer = new StringBuffer();
				buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");
				buffer.append("stats" + "\n");
				for (String key : stats.keySet()) {
					buffer.append("\t" + key + "=" + stats.get(key) + "\n");
				}
				Log.d(TAG, "debugStats: " + buffer.toString() + "\n");
				return buffer.toString();
			}
			return "stats is null or size = 0";
		}
		return "sdk version < 23";
	}

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	/**
	 * 获取内存信息
	 */
	public static Debug.MemoryInfo debugMemoryInfo() {
		Log.d(TAG, "----------------[debugMemoryInfo]------------------");
		Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
		Debug.getMemoryInfo(memoryInfo);
		printDebugMemoryInfo(memoryInfo);
		return memoryInfo;
	}

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	public static String printDebugMemoryInfo(Debug.MemoryInfo memoryInfo) {
		Log.d(TAG, "----------------[printDebugMemoryInfo]------------------");

		if (memoryInfo != null) {
			StringBuffer buffer = new StringBuffer();
			buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");
			buffer.append("\n");

			// 18  = （4 - 1） + 6 + 3 + 3 /（2 - 1）
			int describeContents = memoryInfo.describeContents();

			int totalPss = memoryInfo.getTotalPss();
			int totalSwappablePss = memoryInfo.getTotalSwappablePss();
			int totalPrivateClean = memoryInfo.getTotalPrivateClean();
			int totalPrivateDirty = memoryInfo.getTotalPrivateDirty();
			int totalSharedClean = memoryInfo.getTotalSharedClean();
			int totalSharedDirty = memoryInfo.getTotalSharedDirty();

			int nativePss = memoryInfo.nativePss;
			int nativePrivateDirty = memoryInfo.nativePrivateDirty;
			int nativeSharedDirty = memoryInfo.nativeSharedDirty;

			int otherPss = memoryInfo.otherPss;
			int otherPrivateDirty = memoryInfo.otherPrivateDirty;
			int otherSharedDirty = memoryInfo.otherSharedDirty;

			buffer.append("describeContents=" + describeContents + "\n");

			buffer.append("totalPss=" + totalPss + "byte" +  "--" + FormatUtil.format(totalPss) + "\n");
			buffer.append("totalSwappablePss=" + totalSwappablePss + "byte" +  "--" + FormatUtil.format(totalSwappablePss) + "\n");
			buffer.append("totalPrivateClean=" + totalPrivateClean + "byte" +  "--" + FormatUtil.format(totalPrivateClean) + "\n");
			buffer.append("totalPrivateDirty=" + totalPrivateDirty + "byte" +  "--" + FormatUtil.format(totalPrivateDirty) + "\n");
			buffer.append("totalSharedClean=" + totalSharedClean + "byte" +  "--" + FormatUtil.format(totalSharedClean) + "\n");
			buffer.append("totalSharedDirty=" + totalSharedDirty + "byte" +  "--" + FormatUtil.format(totalSharedDirty) + "\n");

			buffer.append("nativePss=" + nativePss + "byte" +  "--" + FormatUtil.format(nativePss) + "\n");
			buffer.append("nativePrivateDirty=" + nativePrivateDirty + "byte" +  "--" + FormatUtil.format(nativePrivateDirty) + "\n");
			buffer.append("nativeSharedDirty=" + nativeSharedDirty + "byte" +  "--" + FormatUtil.format(nativeSharedDirty) + "\n");

			buffer.append("otherPss=" + otherPss + "byte" +  "--" + FormatUtil.format(otherPss) + "\n");
			buffer.append("otherPrivateDirty=" + otherPrivateDirty + "byte" +  "--" + FormatUtil.format(otherPrivateDirty) + "\n");
			buffer.append("otherSharedDirty=" + otherSharedDirty + "byte" +  "--" + FormatUtil.format(otherSharedDirty) + "\n");

			if (Build.VERSION.SDK_INT >= 23) {
				Map<String, String> stats = memoryInfo.getMemoryStats();
				if (stats !=  null && stats.size() > 0) {
					buffer.append("stats" + "\n");
					for (String key : stats.keySet()) {
						buffer.append("\t" + key + "=" + stats.get(key) + "\n");
					}
				}
			}

			buffer.append("\n");
			Log.d(TAG, "printDebugMemoryInfo: " + "\n" + buffer.toString());
			return buffer.toString();
		}
		return "memoryInfo is null";
	}
}
