package zovl.zhongguanhua.system.demo.ui.activity;

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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.framework.lib.utils.ViewUtil;
import zovl.zhongguanhua.system.demo.R;

public class PackageManagerActivity extends TBaseActivity {

    @Bind(R.id.getPackageArchiveInfo_edit)
    EditText paEdit;

    @Override
    public TextView getText() {
        return text;
    }

    @Override
    public ImageView getImage() {
        return image;
    }

    @Bind(R.id.image)
    ImageView image;

    @Bind(R.id.text)
    TextView text;

    public static final String TAG = PackageManagerActivity.class.getSimpleName();

    @Override
    public int getContentView() {
        return R.layout.activity_packagemanager;
    }

    @OnClick({R.id.getInstalledPackages,
            R.id.getPackageInfo,
            R.id.getPackageArchiveInfo,

            R.id.getInstalledApplications,
            R.id.getApplicationInfo,
            R.id.getActivityInfo,
            R.id.getReceiverInfo,
            R.id.getServiceInfo,
            R.id.getProviderInfo,

            R.id.getLaunchIntentForPackage,
            R.id.getLeanbackLaunchIntentForPackage})
    public void onClick(View view) {

        setTitle(getTitle() + "#");

        switch (view.getId()) {

            case R.id.getInstalledPackages:
                ViewUtil.enabled(view, 1200);
                getInstalledPackages(this);
                setText();
                break;

            case R.id.getPackageInfo:
                getPackageInfo(this, getPackageName());
                setText();
                break;

            case R.id.getPackageArchiveInfo:
                getPackageArchiveInfo(this, paEdit.getText().toString());
                setText();
                break;

            // --------------------------------

            case R.id.getInstalledApplications:
                getInstalledApplications(this);
                setText();
                break;

            case R.id.getApplicationInfo:
                getApplicationInfo(this, "com.bbk.appstore");
                setText();
                break;

            case R.id.getActivityInfo:
                getActivityInfo(this, PackageManagerActivity.class);
                setText();
                break;

            case R.id.getReceiverInfo:
                getReceiverInfo(this, PackageManagerActivity.class);
                setText();
                break;

            case R.id.getServiceInfo:
                getServiceInfo(this, PackageManagerActivity.class);
                setText();
                break;

            case R.id.getProviderInfo:
                getProviderInfo(this, PackageManagerActivity.class);
                setText();
                break;

            // --------------------------------

            case R.id.getLaunchIntentForPackage:
                startActivity(getLaunchIntentForPackage(this, "com.bbk.appstore"));
                setText();
                break;

            case R.id.getLeanbackLaunchIntentForPackage:
                startActivity(getLeanbackLaunchIntentForPackage(this, "com.bbk.appstore"));
                setText();
                break;
        }
    }

    // ---------------------------------------------------------------------------------

    /**
     * 获取所有应用
     */
    private List<PackageInfo> getInstalledPackages(Context context) {
        log("#########################################");
        PackageManager packageManager = context.getPackageManager();
        // 如果<=0则为自己装的程序，否则为系统工程自带
        // (pkg.applicationInfo.flags & pkg.applicationInfo.FLAG_SYSTEM) <= 0
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        Iterator<PackageInfo> iterator = packageInfos.iterator();
        while (iterator.hasNext()) {
            PackageInfo packageInfo = iterator.next();
            packageInfo(context, packageInfo);
        }
        return packageInfos;
    }

    /**
     * 获取当前应用
     */
    private PackageInfo getPackageInfo(Context context, String packageName) {
        log("#########################################");
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(packageName, 0);
            packageInfo(context, packageInfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }

    /**
     * 获取文件应用
     */
    private PackageInfo getPackageArchiveInfo(Context context, String apkPath) {
        log("#########################################");
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        packageInfo(context, packageInfo);
        return packageInfo;
    }

    private void packageInfo(Context context, PackageInfo packageInfo) {
        log("--------------[packageInfo]--------------");
        log("packageName=" + packageInfo.packageName);
        log("versionCode=" + packageInfo.versionCode);
        log("versionName=" + packageInfo.versionName);

        // ------------------------------------------------

        applicationInfo(context, packageInfo.applicationInfo);

        if (packageInfo.activities != null) {
            for (ActivityInfo activityInfo : packageInfo.activities) {
                activityInfo(activityInfo);
            }
        }

        if (packageInfo.receivers != null) {
            for (ActivityInfo activityInfo : packageInfo.receivers) {
                activityInfo(activityInfo);
            }
        }

        if (packageInfo.services != null) {
            for (ServiceInfo serviceInfo : packageInfo.services) {
                serviceInfo(serviceInfo);
            }
        }

        // ------------------------------------------------

        if (Build.VERSION.SDK_INT >= 21) {
            if (packageInfo.splitNames != null) {
                for (String splitName : packageInfo.splitNames) {
                    log("splitName=" + splitName);
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            for (int splitRevisionCode : packageInfo.splitRevisionCodes) {
                log("splitRevisionCode=" + splitRevisionCode);
            }
        }
        if (packageInfo.gids != null) {
            for (int gid : packageInfo.gids) {
                log("gid=" + gid);
            }
        }
        // log("baseRevisionCode=" + info.baseRevisionCode);
        log("sharedUserId=" + packageInfo.sharedUserId);
        log("sharedUserLabel=" + packageInfo.sharedUserLabel);
        log("firstInstallTime=" + packageInfo.firstInstallTime);
        log("lastUpdateTime=" + packageInfo.lastUpdateTime);
        log("describeContents=" + packageInfo.describeContents());
        log("installLocation=" + packageInfo.installLocation);
        log("instrumentation=" + packageInfo.instrumentation);
    }

    private void applicationInfo(Context context, ApplicationInfo applicationInfo) {
        log("--------------[applicationInfo]--------------");
        PackageManager packageManager = context.getPackageManager();
        log("packageName=" + applicationInfo.packageName);
        log("processName=" + applicationInfo.processName);
        log("className=" + applicationInfo.className);
        log("uid=" + applicationInfo.uid);
        log("name=" + applicationInfo.name);
        log("theme=" + applicationInfo.theme);
        log("logo=" + applicationInfo.logo);
        log("icon=" + applicationInfo.icon);
        log("labelRes=" + applicationInfo.labelRes);
        log("describeContents=" + applicationInfo.describeContents());
        log("descriptionRes=" + applicationInfo.descriptionRes);
        log("nonLocalizedLabel=" + applicationInfo.nonLocalizedLabel);
        log("banner=" + applicationInfo.banner);
        log("metaData=" + applicationInfo.metaData);

        log("backupAgentName=" + applicationInfo.backupAgentName);

        log("sourceDir=" + applicationInfo.sourceDir);
        log("dataDir=" + applicationInfo.dataDir);
        log("nativeLibraryDir=" + applicationInfo.nativeLibraryDir);
        log("publicSourceDir=" + applicationInfo.publicSourceDir);
        if (Build.VERSION.SDK_INT >= 21) {
            log("splitPublicSourceDirs=" + applicationInfo.splitPublicSourceDirs);
            log("splitSourceDirs=" + applicationInfo.splitSourceDirs);
        }

        log("manageSpaceActivityName=" + applicationInfo.manageSpaceActivityName);
        log("permission=" + applicationInfo.permission);
        log("taskAffinity=" + applicationInfo.taskAffinity);
        log("enabled=" + applicationInfo.enabled);
        log("flags=" + applicationInfo.flags);
        log("compatibleWidthLimitDp=" + applicationInfo.compatibleWidthLimitDp);
        log("largestWidthLimitDp=" + applicationInfo.largestWidthLimitDp);
        log("uiOptions=" + applicationInfo.uiOptions);
        log("targetSdkVersion=" + applicationInfo.targetSdkVersion);

        log("loadLabel=" + applicationInfo.loadLabel(packageManager));
        log("loadLogo=" + applicationInfo.loadLogo(packageManager));
        log("loadDescription=" + applicationInfo.loadDescription(packageManager));
        if (Build.VERSION.SDK_INT >= 20) {
            log("loadBanner=" + applicationInfo.loadBanner(packageManager));
        }
    }

    private void activityInfo(ActivityInfo activityInfo) {
        log("--------------[activityInfo]--------------");
        log("name=" + activityInfo.name);
        log("processName=" + activityInfo.processName);
        log("launchMode=" + activityInfo.launchMode);
        log("taskAffinity=" + activityInfo.taskAffinity);
        log("enabled=" + activityInfo.enabled);
        log("documentLaunchMode=" + activityInfo.documentLaunchMode);
        log("persistableMode=" + activityInfo.persistableMode);
        log("maxRecents=" + activityInfo.maxRecents);
        log("permission=" + activityInfo.permission);
        log("flags=" + activityInfo.flags);
        log("theme=" + activityInfo.theme);
        log("icon=" + activityInfo.icon);
        log("logo=" + activityInfo.logo);
        log("parentActivityName=" + activityInfo.parentActivityName);
        log("targetActivity=" + activityInfo.targetActivity);
        log("configChanges=" + activityInfo.configChanges);
        log("describeContents=" + activityInfo.describeContents());
        log("documentLaunchMode=" + activityInfo.documentLaunchMode);
        log("screenOrientation=" + activityInfo.screenOrientation);
    }

    private void serviceInfo(ServiceInfo serviceInfo) {
        log("--------------[serviceInfo]--------------");
        log("name=" + serviceInfo.name);
        log("packageName=" + serviceInfo.packageName);
        log("processName=" + serviceInfo.processName);
        log("enabled=" + serviceInfo.enabled);
        log("permission=" + serviceInfo.permission);
        log("flags=" + serviceInfo.flags);
        log("icon=" + serviceInfo.icon);
        log("logo=" + serviceInfo.logo);
    }

    private void providerInfo(Context context, ProviderInfo providerInfo) {
        log("--------------[providerInfo]--------------");
        PackageManager packageManager = context.getPackageManager();
        log("name=" + providerInfo.name);
        log("name=" + providerInfo.processName);
        log("name=" + providerInfo.describeContents());

        log("name=" + providerInfo.enabled);
        log("name=" + providerInfo.isEnabled());
        log("name=" + providerInfo.logo);
        log("name=" + providerInfo.icon);
        log("name=" + providerInfo.banner);
        log("name=" + providerInfo.nonLocalizedLabel);
        log("name=" + providerInfo.authority);
        log("name=" + providerInfo.readPermission);
        log("name=" + providerInfo.writePermission);
        log("name=" + providerInfo.packageName);
        log("name=" + providerInfo.flags);
        log("name=" + providerInfo.grantUriPermissions);
        log("name=" + providerInfo.initOrder);
        log("name=" + providerInfo.multiprocess);
        log("name=" + providerInfo.pathPermissions);
        log("name=" + providerInfo.uriPermissionPatterns);
        log("name=" + providerInfo.applicationInfo);
        log("name=" + providerInfo.exported);

        log("name=" + providerInfo.labelRes);
        log("name=" + providerInfo.descriptionRes);
        if (Build.VERSION.SDK_INT >= 20) {
            log("name=" + providerInfo.getBannerResource());
        }
        log("name=" + providerInfo.getIconResource());

        log("name=" + providerInfo.loadLabel(packageManager));
        log("name=" + providerInfo.loadLogo(packageManager));
        log("name=" + providerInfo.loadIcon(packageManager));
        if (Build.VERSION.SDK_INT >= 20) {
            log("name=" + providerInfo.loadBanner(packageManager));
        }
        // log("name=" + providerInfo.loadXmlMetaData(packageManager, ""));
        if (Build.VERSION.SDK_INT >= 22) {
            log("name=" + providerInfo.loadUnbadgedIcon(packageManager));
        }
    }

    // ---------------------------------------------------------------------------------

    /**
     * 获取所有应用
     */
    private List<ApplicationInfo> getInstalledApplications(Context context) {
        log("#########################################");
        PackageManager packageManager = context.getPackageManager();
        List<ApplicationInfo> applicationInfos = packageManager.getInstalledApplications(0);
        Iterator<ApplicationInfo> iterator = applicationInfos.iterator();
        while (iterator.hasNext()) {
            ApplicationInfo applicationInfo = iterator.next();
            applicationInfo(context, applicationInfo);
        }
        return applicationInfos;
    }

    /**
     * 获取当前应用（应用信息）
     */
    private ApplicationInfo getApplicationInfo(Context context, String packageName) {
        log("#########################################");
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            applicationInfo(context, applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return applicationInfo;
    }

    /**
     * 获取当前应用（活动信息）
     */
    private ActivityInfo getActivityInfo(Context context, Class<?> cls) {
        log("#########################################");
        PackageManager packageManager = context.getPackageManager();
        ComponentName component = new ComponentName(context, cls);
        ActivityInfo activityInfo = null;
        try {
            activityInfo = packageManager.getActivityInfo(component, 0);
            activityInfo(activityInfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return activityInfo;
    }

    /**
     * 获取当前应用（）
     */
    private ActivityInfo getReceiverInfo(Context context, Class<?> cls) {
        log("#########################################");
        PackageManager packageManager = context.getPackageManager();
        ComponentName component = new ComponentName(context, cls);
        ActivityInfo activityInfo = null;
        try {
            activityInfo = packageManager.getReceiverInfo(component, 0);
            activityInfo(activityInfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return activityInfo;
    }

    /**
     * 获取当前应用（服务信息）
     */
    private ServiceInfo getServiceInfo(Context context, Class<?> cls) {
        log("#########################################");
        PackageManager packageManager = context.getPackageManager();
        ComponentName component = new ComponentName(context, cls);
        ServiceInfo serviceInfo = null;
        try {
            serviceInfo = packageManager.getServiceInfo(component, 0);
            serviceInfo(serviceInfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return serviceInfo;
    }

    /**
     * 获取当前应用（提供者信息）
     */
    private ProviderInfo getProviderInfo(Context context, Class<?> cls) {
        log("#########################################");
        PackageManager packageManager = context.getPackageManager();
        ComponentName component = new ComponentName(context, cls);
        ProviderInfo providerInfo = null;
        try {
            providerInfo = packageManager.getProviderInfo(component, 0);
            providerInfo(context, providerInfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return providerInfo;
    }

    // ---------------------------------------------------------------------------------

    /**
     * 获取当前应用
     */
    private Intent getLaunchIntentForPackage(Context context, String packageName) {
        log("#########################################");
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        log("intent=" + intent);
        return intent;
    }

    /**
     * 获取当前应用
     */
    private Intent getLeanbackLaunchIntentForPackage(Context context, String packageName) {
        log("#########################################");
        PackageManager packageManager = context.getPackageManager();
        Intent intent = null;
        if (Build.VERSION.SDK_INT >= 21) {
            intent = packageManager.getLeanbackLaunchIntentForPackage(packageName);
        }
        log("intent=" + intent);
        return intent;
    }

    // ---------------------------------------------------------------------------------

    /**
     * 是否安装应用
     */
    private boolean isInstalled(Context context, String packageName) {
        log("#########################################");
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
    private void lauch(Context context, String packageName) {
        log("#########################################");
        try {
            context.startActivity(getLaunchIntentForPackage(context, packageName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
