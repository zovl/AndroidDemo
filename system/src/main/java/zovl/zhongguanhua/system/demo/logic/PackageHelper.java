package zovl.zhongguanhua.system.demo.logic;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 功能：包工具
 */
public class PackageHelper {

	public static final String TAG = PackageHelper.class.getSimpleName();

	// ---------------------------------------------------------------------------------

	public static String printPackageInfo(Context context, PackageInfo packageInfo) {
		Log.d(TAG, "----------------[packageInfo]------------------");

		StringBuffer buffer = new StringBuffer();
		buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");
		buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");
		buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");
		buffer.append("packageName=" + packageInfo.packageName + "\n");
		buffer.append("versionCode=" + packageInfo.versionCode + "\n");
		buffer.append("versionName=" + packageInfo.versionName + "\n");

		// ------------------------------------------------

		printApplicationInfo(context, packageInfo.applicationInfo);

		if (packageInfo.activities != null) {
			for (ActivityInfo activityInfo : packageInfo.activities) {
				printActivityInfo(activityInfo);
			}
		}

		if (packageInfo.receivers != null) {
			for (ActivityInfo activityInfo : packageInfo.receivers) {
				printActivityInfo(activityInfo);
			}
		}

		if (packageInfo.services != null) {
			for (ServiceInfo serviceInfo : packageInfo.services) {
				printServiceInfo(serviceInfo);
			}
		}

		// ------------------------------------------------

		if (Build.VERSION.SDK_INT >= 21) {
			if (packageInfo.splitNames != null) {
				for (String splitName : packageInfo.splitNames) {
					buffer.append("splitName=" + splitName + "\n");
				}
			}
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
			for (int splitRevisionCode : packageInfo.splitRevisionCodes) {
				buffer.append("splitRevisionCode=" + splitRevisionCode + "\n");
			}
		}
		if (packageInfo.gids != null) {
			for (int gid : packageInfo.gids) {
				buffer.append("gid=" + gid + "\n");
			}
		}
		// buffer.append("baseRevisionCode=" + info.baseRevisionCode + "\n");
		buffer.append("sharedUserId=" + packageInfo.sharedUserId + "\n");
		buffer.append("sharedUserLabel=" + packageInfo.sharedUserLabel + "\n");
		buffer.append("firstInstallTime=" + packageInfo.firstInstallTime + "\n");
		buffer.append("lastUpdateTime=" + packageInfo.lastUpdateTime + "\n");
		buffer.append("describeContents=" + packageInfo.describeContents() + "\n");
		buffer.append("installLocation=" + packageInfo.installLocation + "\n");
		buffer.append("instrumentation=" + packageInfo.instrumentation + "\n");

		Log.d(TAG, "printPackageInfo: " + buffer.toString() + "\n");

		return buffer.toString();
	}

	public static String printApplicationInfo(Context context, ApplicationInfo applicationInfo) {
		Log.d(TAG, "----------------[applicationInfo]------------------");

		PackageManager packageManager = context.getPackageManager();

		StringBuffer buffer = new StringBuffer();
		buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");
		buffer.append("packageName=" + applicationInfo.packageName + "\n");
		buffer.append("processName=" + applicationInfo.processName + "\n");
		buffer.append("className=" + applicationInfo.className + "\n");
		buffer.append("uid=" + applicationInfo.uid + "\n");
		buffer.append("name=" + applicationInfo.name + "\n");
		buffer.append("theme=" + applicationInfo.theme + "\n");
		buffer.append("logo=" + applicationInfo.logo + "\n");
		buffer.append("icon=" + applicationInfo.icon + "\n");
		buffer.append("labelRes=" + applicationInfo.labelRes + "\n");
		buffer.append("describeContents=" + applicationInfo.describeContents() + "\n");
		buffer.append("descriptionRes=" + applicationInfo.descriptionRes + "\n");
		buffer.append("nonLocalizedLabel=" + applicationInfo.nonLocalizedLabel + "\n");
		buffer.append("banner=" + applicationInfo.banner + "\n");
		buffer.append("metaData=" + applicationInfo.metaData + "\n");

		buffer.append("backupAgentName=" + applicationInfo.backupAgentName + "\n");

		buffer.append("sourceDir=" + applicationInfo.sourceDir + "\n");
		buffer.append("dataDir=" + applicationInfo.dataDir + "\n");
		buffer.append("nativeLibraryDir=" + applicationInfo.nativeLibraryDir + "\n");
		buffer.append("publicSourceDir=" + applicationInfo.publicSourceDir + "\n");
		if (Build.VERSION.SDK_INT >= 21) {
			buffer.append("splitPublicSourceDirs=" + applicationInfo.splitPublicSourceDirs + "\n");
			buffer.append("splitSourceDirs=" + applicationInfo.splitSourceDirs + "\n");
		}

		buffer.append("manageSpaceActivityName=" + applicationInfo.manageSpaceActivityName + "\n");
		buffer.append("permission=" + applicationInfo.permission + "\n");
		buffer.append("taskAffinity=" + applicationInfo.taskAffinity + "\n");
		buffer.append("enabled=" + applicationInfo.enabled + "\n");
		buffer.append("flags=" + applicationInfo.flags + "\n");
		buffer.append("compatibleWidthLimitDp=" + applicationInfo.compatibleWidthLimitDp + "\n");
		buffer.append("largestWidthLimitDp=" + applicationInfo.largestWidthLimitDp + "\n");
		buffer.append("uiOptions=" + applicationInfo.uiOptions + "\n");
		buffer.append("targetSdkVersion=" + applicationInfo.targetSdkVersion + "\n");

		buffer.append("loadLabel=" + applicationInfo.loadLabel(packageManager) + "\n");
		buffer.append("loadLogo=" + applicationInfo.loadLogo(packageManager) + "\n");
		buffer.append("loadDescription=" + applicationInfo.loadDescription(packageManager) + "\n");
		if (Build.VERSION.SDK_INT >= 20) {
			buffer.append("loadBanner=" + applicationInfo.loadBanner(packageManager) + "\n");
		}

		Log.d(TAG, "printApplicationInfo: " + buffer.toString() + "\n");

		return buffer.toString();
	}

	public static String printActivityInfo(ActivityInfo activityInfo) {
		Log.d(TAG, "----------------[activityInfo]------------------");

		StringBuffer buffer = new StringBuffer();
		buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");
		buffer.append("name=" + activityInfo.name + "\n");
		buffer.append("processName=" + activityInfo.processName + "\n");
		buffer.append("launchMode=" + activityInfo.launchMode + "\n");
		buffer.append("taskAffinity=" + activityInfo.taskAffinity + "\n");
		buffer.append("enabled=" + activityInfo.enabled + "\n");
		buffer.append("documentLaunchMode=" + activityInfo.documentLaunchMode + "\n");
		buffer.append("persistableMode=" + activityInfo.persistableMode + "\n");
		buffer.append("maxRecents=" + activityInfo.maxRecents + "\n");
		buffer.append("permission=" + activityInfo.permission + "\n");
		buffer.append("flags=" + activityInfo.flags + "\n");
		buffer.append("theme=" + activityInfo.theme + "\n");
		buffer.append("icon=" + activityInfo.icon + "\n");
		buffer.append("logo=" + activityInfo.logo + "\n");
		buffer.append("parentActivityName=" + activityInfo.parentActivityName + "\n");
		buffer.append("targetActivity=" + activityInfo.targetActivity + "\n");
		buffer.append("configChanges=" + activityInfo.configChanges + "\n");
		buffer.append("describeContents=" + activityInfo.describeContents() + "\n");
		buffer.append("documentLaunchMode=" + activityInfo.documentLaunchMode + "\n");
		buffer.append("screenOrientation=" + activityInfo.screenOrientation + "\n");

		Log.d(TAG, "printActivityInfo: " + buffer.toString() + "\n");

		return buffer.toString();
	}

	public static String printServiceInfo(ServiceInfo serviceInfo) {
		Log.d(TAG, "----------------[serviceInfo]------------------");

		StringBuffer buffer = new StringBuffer();
		buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");
		buffer.append("name=" + serviceInfo.name + "\n");
		buffer.append("packageName=" + serviceInfo.packageName + "\n");
		buffer.append("processName=" + serviceInfo.processName + "\n");
		buffer.append("enabled=" + serviceInfo.enabled + "\n");
		buffer.append("permission=" + serviceInfo.permission + "\n");
		buffer.append("flags=" + serviceInfo.flags + "\n");
		buffer.append("icon=" + serviceInfo.icon + "\n");
		buffer.append("logo=" + serviceInfo.logo + "\n");

		Log.d(TAG, "printServiceInfo: " + buffer.toString() + "\n");

		return buffer.toString();
	}

	public static String printProviderInfo(Context context, ProviderInfo providerInfo) {
		Log.d(TAG, "----------------[providerInfo]------------------");

		PackageManager packageManager = context.getPackageManager();

		StringBuffer buffer = new StringBuffer();
		buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");
		buffer.append("name=" + providerInfo.name + "\n");
		buffer.append("name=" + providerInfo.processName + "\n");
		buffer.append("name=" + providerInfo.describeContents() + "\n");

		buffer.append("name=" + providerInfo.enabled + "\n");
		buffer.append("name=" + providerInfo.isEnabled() + "\n");
		buffer.append("name=" + providerInfo.logo + "\n");
		buffer.append("name=" + providerInfo.icon + "\n");
		buffer.append("name=" + providerInfo.banner + "\n");
		buffer.append("name=" + providerInfo.nonLocalizedLabel + "\n");
		buffer.append("name=" + providerInfo.authority + "\n");
		buffer.append("name=" + providerInfo.readPermission + "\n");
		buffer.append("name=" + providerInfo.writePermission + "\n");
		buffer.append("name=" + providerInfo.packageName + "\n");
		buffer.append("name=" + providerInfo.flags + "\n");
		buffer.append("name=" + providerInfo.grantUriPermissions + "\n");
		buffer.append("name=" + providerInfo.initOrder + "\n");
		buffer.append("name=" + providerInfo.multiprocess + "\n");
		buffer.append("name=" + providerInfo.pathPermissions + "\n");
		buffer.append("name=" + providerInfo.uriPermissionPatterns + "\n");
		buffer.append("name=" + providerInfo.applicationInfo + "\n");
		buffer.append("name=" + providerInfo.exported + "\n");

		buffer.append("name=" + providerInfo.labelRes + "\n");
		buffer.append("name=" + providerInfo.descriptionRes + "\n");
		if (Build.VERSION.SDK_INT >= 20) {
			buffer.append("name=" + providerInfo.getBannerResource() + "\n");
		}
		buffer.append("name=" + providerInfo.getIconResource() + "\n");

		buffer.append("name=" + providerInfo.loadLabel(packageManager) + "\n");
		buffer.append("name=" + providerInfo.loadLogo(packageManager) + "\n");
		buffer.append("name=" + providerInfo.loadIcon(packageManager) + "\n");
		if (Build.VERSION.SDK_INT >= 20) {
			buffer.append("name=" + providerInfo.loadBanner(packageManager) + "\n");
		}
		// buffer.append("name=" + providerInfo.loadXmlMetaData(packageManager, "") + "\n");
		if (Build.VERSION.SDK_INT >= 22) {
			buffer.append("name=" + providerInfo.loadUnbadgedIcon(packageManager) + "\n");
		}

		Log.d(TAG, "printProviderInfo: " + buffer.toString() + "\n");

		return buffer.toString();
	}

	// ---------------------------------------------------------------------------------

	/**
	 * 获取所有应用（系统）
	 */
	public static List<PackageInfo> getSystemPackages(Context context) {
		Log.d(TAG, "----------------[getSystemPackages]------------------");

		List<PackageInfo> list = new ArrayList<>();

		List<PackageInfo> packageInfos = getInstalledPackages(context);
		for (PackageInfo packageInfo : packageInfos) {
			// 如果 < = 0则为自己装的程序
			// 如果 > 0则为系统工程自带
			if ((packageInfo.applicationInfo.flags &
					packageInfo.applicationInfo.FLAG_SYSTEM) > 0) {
				list.add(packageInfo);
			}
		}
		return list;
	}

	/**
	 * 获取所有应用（用户）
	 */
	public static List<PackageInfo> getUserPackages(Context context) {
		Log.d(TAG, "----------------[getUserPackages]------------------");

		List<PackageInfo> list = new ArrayList<>();

		List<PackageInfo> packageInfos = getInstalledPackages(context);
		for (PackageInfo packageInfo : packageInfos) {
			if ((packageInfo.applicationInfo.flags &
					packageInfo.applicationInfo.FLAG_SYSTEM) <= 0) {
				list.add(packageInfo);
			}
		}
		return list;
	}

	/**
	 * 获取所有应用
	 */
	public static List<PackageInfo> getInstalledPackages(Context context) {
		Log.d(TAG, "----------------[getInstalledPackages]------------------");
		PackageManager packageManager = context.getPackageManager();
		List<PackageInfo> packageInfos = null;
		try {
			packageInfos = packageManager.getInstalledPackages(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return packageInfos;
	}

	/**
	 * 获取当前应用
	 */
	public static PackageInfo getPackageInfo(Context context, String packageName) {
		Log.d(TAG, "----------------[getPackageInfo]------------------");
		PackageManager packageManager = context.getPackageManager();
		PackageInfo packageInfo = null;
		try {
			packageInfo = packageManager.getPackageInfo(packageName, 0);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return packageInfo;
	}

	/**
	 * 获取文件应用
	 */
	public static PackageInfo getPackageArchiveInfo(Context context, String apkPath) {
		Log.d(TAG, "----------------[getPackageArchiveInfo]------------------");
		PackageManager packageManager = context.getPackageManager();
		PackageInfo packageInfo = null;
		try {
			packageInfo = packageManager.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return packageInfo;
	}

	// ---------------------------------------------------------------------------------

	/**
	 * 获取所有应用
	 */
	public static List<ApplicationInfo> getInstalledApplications(Context context) {
		Log.d(TAG, "----------------[getInstalledApplications]------------------");
		PackageManager packageManager = context.getPackageManager();
		List<ApplicationInfo> applicationInfos = null;
		try {
			applicationInfos = packageManager.getInstalledApplications(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return applicationInfos;
	}

	/**
	 * 获取当前应用（应用信息）
	 */
	public static ApplicationInfo getApplicationInfo(Context context, String packageName) {
		Log.d(TAG, "----------------[getApplicationInfo]------------------");
		PackageManager packageManager = context.getPackageManager();
		ApplicationInfo applicationInfo = null;
		try {
			applicationInfo = packageManager.getApplicationInfo(packageName, 0);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return applicationInfo;
	}

	/**
	 * 获取当前应用（活动信息）
	 */
	public static ActivityInfo getActivityInfo(Context context, Class<?> cls) {
		Log.d(TAG, "----------------[getActivityInfo]------------------");
		PackageManager packageManager = context.getPackageManager();
		ComponentName component = new ComponentName(context, cls);
		ActivityInfo activityInfo = null;
		try {
			activityInfo = packageManager.getActivityInfo(component, 0);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return activityInfo;
	}

	/**
	 * 获取当前应用（）
	 */
	public static ActivityInfo getReceiverInfo(Context context, Class<?> cls) {
		Log.d(TAG, "----------------[getReceiverInfo]------------------");
		PackageManager packageManager = context.getPackageManager();
		ComponentName component = new ComponentName(context, cls);
		ActivityInfo activityInfo = null;
		try {
			activityInfo = packageManager.getReceiverInfo(component, 0);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return activityInfo;
	}

	/**
	 * 获取当前应用（服务信息）
	 */
	public static ServiceInfo getServiceInfo(Context context, Class<?> cls) {
		Log.d(TAG, "----------------[getServiceInfo]------------------");
		PackageManager packageManager = context.getPackageManager();
		ComponentName component = new ComponentName(context, cls);
		ServiceInfo serviceInfo = null;
		try {
			serviceInfo = packageManager.getServiceInfo(component, 0);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return serviceInfo;
	}

	/**
	 * 获取当前应用（提供者信息）
	 */
	public static ProviderInfo getProviderInfo(Context context, Class<?> cls) {
		Log.d(TAG, "----------------[getProviderInfo]------------------");
		PackageManager packageManager = context.getPackageManager();
		ComponentName component = new ComponentName(context, cls);
		ProviderInfo providerInfo = null;
		try {
			providerInfo = packageManager.getProviderInfo(component, 0);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return providerInfo;
	}

	// ---------------------------------------------------------------------------------

	/**
	 * 获取当前应用
	 */
	public static Intent getLaunchIntentForPackage(Context context, String packageName) {
		Log.d(TAG, "----------------[getLaunchIntentForPackage]------------------");
		PackageManager packageManager = context.getPackageManager();
		Intent intent = packageManager.getLaunchIntentForPackage(packageName);
		Log.d(TAG, "intent=" + intent);
		return intent;
	}

	/**
	 * 获取当前应用
	 */
	public static Intent getLeanbackLaunchIntentForPackage(Context context, String packageName) {
		Log.d(TAG, "----------------[getLeanbackLaunchIntentForPackage]------------------");
		PackageManager packageManager = context.getPackageManager();
		Intent intent = null;
		if (Build.VERSION.SDK_INT >= 21) {
			intent = packageManager.getLeanbackLaunchIntentForPackage(packageName);
		}
		Log.d(TAG, "intent=" + intent);
		return intent;
	}

	// ---------------------------------------------------------------------------------

	/**
	 * 是否安装应用
	 */
	public static boolean isInstalled(Context context, String packageName) {
		Log.d(TAG, "----------------[isInstalled]------------------");
		Iterator<PackageInfo> iterator = getInstalledPackages(context).iterator();
		while (iterator.hasNext()) {
			PackageInfo packageInfo = iterator.next();
			if (packageName != null && packageName.equals(packageInfo.packageName))
				return true;
		}
		return false;
	}

	/**
	 * 启动应用
	 */
	public static void lauch(Context context, String packageName) {
		Log.d(TAG, "----------------[lauch]------------------");
		try {
			context.startActivity(getLaunchIntentForPackage(context, packageName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
