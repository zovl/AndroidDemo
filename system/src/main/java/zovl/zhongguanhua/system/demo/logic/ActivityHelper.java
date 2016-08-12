package zovl.zhongguanhua.system.demo.logic;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Debug;
import android.util.Log;

import java.util.List;

import zovl.zhongguanhua.framework.lib.utils.FormatUtil;

/**
 * 功能：应用工具
 */
public class ActivityHelper {

	public static final String TAG = ActivityHelper.class.getSimpleName();

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	public static String runningAppProcessInfo(ActivityManager.RunningAppProcessInfo runningAppProcessInfo) {
		Log.d(TAG, "----------------[runningAppProcessInfo]------------------");

		if (runningAppProcessInfo != null) {
			StringBuffer buffer = new StringBuffer();
			buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");

			buffer.append("processName=" + runningAppProcessInfo.processName + "\n");
			buffer.append("pid=" + runningAppProcessInfo.pid + "\n");
			buffer.append("uid=" + runningAppProcessInfo.uid + "\n");
			buffer.append("describeContents=" + runningAppProcessInfo.describeContents() + "\n");
			for (String pkg : runningAppProcessInfo.pkgList) {
                buffer.append("pkg=" + pkg + "\n");
            }
			buffer.append("lastTrimLevel=" + runningAppProcessInfo.lastTrimLevel + "\n");
			buffer.append("lru=" + runningAppProcessInfo.lru + "\n");
			// buffer.append("processState=" + runningAppProcessInfo.processState + "\n");
			buffer.append("importance=" + runningAppProcessInfo.importance + "\n");
			buffer.append("importanceReasonPid=" + runningAppProcessInfo.importanceReasonPid + "\n");
			buffer.append("importanceReasonComponent=" + runningAppProcessInfo.importanceReasonComponent + "\n");
			buffer.append("importanceReasonCode=" + runningAppProcessInfo.importanceReasonCode + "\n");
			// buffer.append("importanceReasonImportance=" + runningAppProcessInfo.importanceReasonImportance + "\n");

			Log.d(TAG, "runningAppProcessInfo: " + buffer.toString());
			return buffer.toString();
		}
		return "runningAppProcessInfo is null";
	}

	public static String runningServiceInfo(ActivityManager.RunningServiceInfo runningServiceInfo) {
		Log.d(TAG, "----------------[runningServiceInfo]------------------");

		if (runningServiceInfo != null) {
			StringBuffer buffer = new StringBuffer();
			buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");

			buffer.append("service=" + runningServiceInfo.service + "\n");
			buffer.append("process=" + runningServiceInfo.process + "\n");
			buffer.append("describeContents=" + runningServiceInfo.describeContents() + "\n");
			buffer.append("pid=" + runningServiceInfo.pid + "\n");
			buffer.append("uid=" + runningServiceInfo.uid + "\n");
			buffer.append("foreground=" + runningServiceInfo.foreground + "\n");
			buffer.append("started=" + runningServiceInfo.started + "\n");
			buffer.append("clientLabel=" + runningServiceInfo.clientLabel + "\n");
			buffer.append("clientCount=" + runningServiceInfo.clientCount + "\n");
			buffer.append("activeSince=" + runningServiceInfo.activeSince + "\n");
			buffer.append("crashCount=" + runningServiceInfo.crashCount + "\n");
			buffer.append("lastActivityTime=" + runningServiceInfo.lastActivityTime + "\n");
			buffer.append("restarting=" + runningServiceInfo.restarting + "\n");
			buffer.append("flags=" + runningServiceInfo.flags + "\n");

			Log.d(TAG, "runningServiceInfo: " + buffer.toString() + "\n");
			return buffer.toString();
		}
		return "runningServiceInfo is null";
	}

	public static String appTask(ActivityManager.AppTask appTask) {
		Log.d(TAG, "----------------[appTask]------------------");

		if (appTask != null) {
			if (Build.VERSION.SDK_INT >= 21) {
				String toString = appTask.toString();
				ActivityManager.RecentTaskInfo recentTaskInfo = appTask.getTaskInfo();

				// appTask.moveToFront( + "\n");
				// appTask.finishAndRemoveTask( + "\n");
				// appTask.startActivity(context, new Intent(), new Bundle() + "\n");
				// appTask.setExcludeFromRecents(true + "\n");

				StringBuffer buffer = new StringBuffer();
				buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");

				buffer.append("toString=" + toString + "\n");
				buffer.append("recentTaskInfo=" + recentTaskInfo + "\n");

				Log.d(TAG, "runningServiceInfo: " + buffer.toString() + "\n");
				return buffer.toString();
			}
			return "sdk is < 21";
		}
		return "appTask is null";
	}

	public static String runningTaskInfo(ActivityManager.RunningTaskInfo runningTaskInfo) {
		Log.d(TAG, "----------------[runningTaskInfo]------------------");

		if (runningTaskInfo != null) {
			StringBuffer buffer = new StringBuffer();
			buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");

			buffer.append("id=" + runningTaskInfo.id + "\n");
			buffer.append("baseActivity=" + runningTaskInfo.baseActivity + "\n");
			buffer.append("topActivity=" + runningTaskInfo.topActivity + "\n");
			buffer.append("thumbnail=" + runningTaskInfo.thumbnail + "\n");
			buffer.append("description=" + runningTaskInfo.description + "\n");
			buffer.append("numActivities=" + runningTaskInfo.numActivities + "\n");
			buffer.append("numRunning=" + runningTaskInfo.numRunning + "\n");
			// buffer.append("lastActiveTime=" + runningTaskInfo.lastActiveTime + "\n");
			buffer.append("describeContents=" + runningTaskInfo.describeContents() + "\n");

			Log.d(TAG, "runningTaskInfo: " + buffer.toString() + "\n");
			return buffer.toString();
		}
		return "runningTaskInfo is null";
	}

	public static String recentTaskInfo(ActivityManager.RecentTaskInfo recentTaskInfo) {
		Log.d(TAG, "----------------[recentTaskInfo]------------------");

		if (recentTaskInfo != null) {
			StringBuffer buffer = new StringBuffer();
			buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");

			buffer.append("id=" + recentTaskInfo.id + "\n");
			buffer.append("affiliatedTaskId=" + recentTaskInfo.affiliatedTaskId + "\n");
			buffer.append("description=" + recentTaskInfo.description + "\n");
			if (Build.VERSION.SDK_INT >= 21) {
                buffer.append("taskDescription=" + recentTaskInfo.taskDescription + "\n");
            }
			buffer.append("baseIntent=" + recentTaskInfo.baseIntent + "\n");
			// buffer.append("numActivities=" + recentTaskInfo.numActivities + "\n");
			buffer.append("origActivity=" + recentTaskInfo.origActivity + "\n");
			if (Build.VERSION.SDK_INT >= 23) {
                buffer.append("baseActivity=" + recentTaskInfo.baseActivity + "\n");
                buffer.append("topActivity=" + recentTaskInfo.topActivity + "\n");
            }
			buffer.append("persistentId=" + recentTaskInfo.persistentId + "\n");
			// buffer.append("stackId=" + recentTaskInfo.stackId + "\n");
			// buffer.append("userId=" + recentTaskInfo.userId + "\n");
			// buffer.append("firstActiveTime=" + recentTaskInfo.firstActiveTime + "\n");
			// buffer.append("lastActiveTime=" + recentTaskInfo.lastActiveTime + "\n");
			// buffer.append("affiliatedTaskColor=" + recentTaskInfo.affiliatedTaskColor + "\n");

			Log.d(TAG, "recentTaskInfo: " + buffer.toString() + "\n");
			return buffer.toString();
		}
		return "recentTaskInfo is null";
	}

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	public static void killBackgroundProcesses(Context context, String packageName) {
		Log.d(TAG, "----------------[killBackgroundProcesses]------------------");
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		activityManager.killBackgroundProcesses(packageName + "\n");
	}

	public static void clearApplicationUserData(Context context, String packageName) {
		Log.d(TAG, "----------------[clearApplicationUserData]------------------");
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		activityManager.clearApplicationUserData();
	}

	public static void clearWatchHeapLimit(Context context, String packageName) {
		Log.d(TAG, "----------------[clearWatchHeapLimit]------------------");
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		if (Build.VERSION.SDK_INT >= 23) {
			activityManager.clearWatchHeapLimit();
		}
	}

	public static void restartPackage(Context context, String packageName) {
		Log.d(TAG, "----------------[restartPackage]------------------");
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		activityManager.restartPackage(packageName + "\n");
	}

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	/**
	 * 获得系统运行的进程
	 */
	public static List<ActivityManager.RunningAppProcessInfo> runningAppProcesses(Context context) {
		Log.d(TAG, "----------------[runningAppProcesses]------------------");
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos = activityManager.getRunningAppProcesses();
		return runningAppProcessInfos;
	}

	/**
	 * 获得当前正在运行的服务
	 */
	public static List<ActivityManager.RunningServiceInfo> runningServices(Context context) {
		Log.d(TAG, "----------------[runningServices]------------------");
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> runningServiceInfos = activityManager.getRunningServices(100);
		return runningServiceInfos;
	}

	/**
	 * 获得系统全部任务栈
	 */
	public static List<ActivityManager.AppTask> appTasks(Context context) {
		Log.d(TAG, "----------------[appTasks]------------------");
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		if (Build.VERSION.SDK_INT >= 21) {
			List<ActivityManager.AppTask> appTasks = activityManager.getAppTasks();
			return appTasks;
		}
		return null;
	}

	/**
	 * 获得当前正在运行的任务栈
	 */
	public static List<ActivityManager.RunningTaskInfo> runningTasks(Context context) {
		Log.d(TAG, "----------------[runningTasks]------------------");
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1000);
		return runningTaskInfos;
	}

	/**
	 * 获得最近运行的任务栈
	 */
	public static List<ActivityManager.RecentTaskInfo> recentTasks(Context context) {
		Log.d(TAG, "----------------[recentTasks]------------------");
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RecentTaskInfo> recentTaskInfos = activityManager.getRecentTasks(100, 1);
		return recentTaskInfos;
	}

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	/**
	 * 获取系统可用内存信息
	 */
	public static ActivityManager.MemoryInfo getMemoryInfo(Context context) {
		Log.d(TAG, "----------------[getMemoryInfo]------------------");
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
		activityManager.getMemoryInfo(memoryInfo);
		return memoryInfo;
	}

	/**
	 * 获取系统可用内存信息
	 */
	public static Debug.MemoryInfo getProcessMemoryInfo(Context context, int pid) {
		Log.d(TAG, "----------------[getProcessMemoryInfo]------------------");
		Debug.MemoryInfo[] memoryInfos = getProcessMemoryInfos(context, new int[]{ pid });
		if (memoryInfos != null && memoryInfos.length > 0) {
			return memoryInfos[0];
		}
		return null;
	}

	/**
	 * 获取系统可用内存信息
	 */
	public static Debug.MemoryInfo[] getProcessMemoryInfos(Context context, int[] pids) {
		Log.d(TAG, "----------------[getProcessMemoryInfos]------------------");
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		Debug.MemoryInfo[] memoryInfos = activityManager.getProcessMemoryInfo(pids);
		return memoryInfos;
	}

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	public static String memoryInfo(ActivityManager.MemoryInfo memoryInfo) {
		Log.d(TAG, "----------------[recentTaskInfo]------------------");

		if (memoryInfo != null) {
			StringBuffer buffer = new StringBuffer();
			buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");
			
			buffer.append("availMem=" + memoryInfo.availMem + "\n");
			buffer.append("totalMem=" + memoryInfo.totalMem + "\n");
			buffer.append("threshold=" + memoryInfo.threshold + "\n");
			buffer.append("lowMemory=" + memoryInfo.lowMemory + "\n");
			buffer.append("" + "\n");
			buffer.append("availMem=" + FormatUtil.format(memoryInfo.availMem) + "\n");
			buffer.append("totalMem=" + FormatUtil.format(memoryInfo.totalMem) + "\n");
			buffer.append("threshold=" + FormatUtil.format(memoryInfo.threshold) + "\n");
			// buffer.append("hiddenAppThreshold=" + memoryInfo.hiddenAppThreshold + "\n");
			// buffer.append("secondaryServerThreshold=" + memoryInfo.secondaryServerThreshold + "\n");
			// buffer.append("visibleAppThreshold=" + memoryInfo.visibleAppThreshold + "\n");
			// buffer.append("foregroundAppThreshold=" + memoryInfo.foregroundAppThreshold + "\n");

			Log.d(TAG, "memoryInfo: " + buffer.toString());
			return buffer.toString();
		}
		return "memoryInfo is null";
	}

	public static String debugMemoryInfo(Debug.MemoryInfo memoryInfo) {
		Log.d(TAG, "----------------[debugMemoryInfo]------------------");

		if (memoryInfo != null) {
			StringBuffer buffer = new StringBuffer();
			buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");

			buffer.append("dalvikPss=" + memoryInfo.dalvikPss + "\n");
            // buffer.append("dalvikSwappablePss=" + memoryInfo.dalvikSwappablePss + "\n");
			buffer.append("dalvikPrivateDirty=" + memoryInfo.dalvikPrivateDirty + "\n");
			buffer.append("dalvikSharedDirty=" + memoryInfo.dalvikSharedDirty + "\n");
            // buffer.append("dalvikPrivateClean=" + memoryInfo.dalvikPrivateClean + "\n");
            // buffer.append("dalvikSharedClean=" + memoryInfo.dalvikSharedClean + "\n");
            // buffer.append("dalvikSwappedOut=" + memoryInfo.dalvikSwappedOut + "\n");
			buffer.append("nativePss=" + memoryInfo.nativePss + "\n");
            // buffer.append("nativeSwappablePss=" + memoryInfo.nativeSwappablePss + "\n");
			buffer.append("nativePrivateDirty=" + memoryInfo.nativePrivateDirty + "\n");
			buffer.append("nativeSharedDirty=" + memoryInfo.nativeSharedDirty + "\n");
            // buffer.append("nativePrivateClean=" + memoryInfo.nativePrivateClean + "\n");
            // buffer.append("nativeSharedClean=" + memoryInfo.nativeSharedClean + "\n");
            // buffer.append("nativeSwappedOut=" + memoryInfo.nativeSwappedOut + "\n");
			buffer.append("otherPss=" + memoryInfo.otherPss + "\n");
            // buffer.append("otherSwappablePss=" + memoryInfo.otherSwappablePss + "\n");
			buffer.append("otherPrivateDirty=" + memoryInfo.otherPrivateDirty + "\n");
			buffer.append("otherSharedDirty=" + memoryInfo.otherSharedDirty + "\n");
            // buffer.append("otherPrivateClean=" + memoryInfo.otherPrivateClean + "\n");
            // buffer.append("otherSharedClean=" + memoryInfo.otherSharedClean + "\n");
            // buffer.append("otherSwappedOut=" + memoryInfo.otherSwappedOut + "\n");
            // buffer.append("otherStats=" + memoryInfo.otherStats + "\n");
			buffer.append("getTotalPss=" + memoryInfo.getTotalPss() + "\n");
            // buffer.append("getTotalUss=" + memoryInfo.getTotalUss() + "\n");
			buffer.append("getTotalSwappablePss=" + memoryInfo.getTotalSwappablePss() + "\n");
			buffer.append("getTotalPrivateDirty=" + memoryInfo.getTotalPrivateDirty() + "\n");
			buffer.append("getTotalSharedDirty=" + memoryInfo.getTotalSharedDirty() + "\n");
			buffer.append("getTotalPrivateClean=" + memoryInfo.getTotalPrivateClean() + "\n");
			buffer.append("getTotalSharedClean=" + memoryInfo.getTotalSharedClean() + "\n");
            // buffer.append("getTotalSwappedOut=" + memoryInfo.getTotalSwappedOut() + "\n");
            // buffer.append("getOtherPss=" + memoryInfo.getOtherPss() + "\n");
            // buffer.append("getOtherSwappablePss=" + memoryInfo.getOtherSwappablePss() + "\n");
            // buffer.append("getOtherPrivateDirty=" + memoryInfo.getOtherPrivateDirty() + "\n");
            // buffer.append("getOtherSharedDirty=" + memoryInfo.getOtherSharedDirty() + "\n");
            // buffer.append("getOtherPrivateClean=" + memoryInfo.getOtherPrivateClean() + "\n");
            // buffer.append("getOtherPrivate=" + memoryInfo.getOtherPrivate() + "\n");
            // buffer.append("getOtherSharedClean=" + memoryInfo.getOtherSharedClean() + "\n");
            // buffer.append("getOtherSwappedOut=" + memoryInfo.getOtherSwappedOut() + "\n");

			Log.d(TAG, "memoryInfo: " + buffer.toString());
			return buffer.toString();
		}
		return "memoryInfo is null";
	}
}
