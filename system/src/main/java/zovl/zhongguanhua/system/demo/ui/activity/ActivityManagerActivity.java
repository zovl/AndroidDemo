package zovl.zhongguanhua.system.demo.ui.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Debug;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.system.demo.R;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.framework.lib.utils.FormatUtil;

public class ActivityManagerActivity extends TBaseActivity {

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

    public static final String TAG = ActivityManagerActivity.class.getSimpleName();

    @Override
    public int getContentView() {
        return R.layout.activity_activitymanager;
    }

    @OnClick({R.id.runningAppProcesses,
            R.id.runningServices,

            R.id.appTasks,

            R.id.runningTasks,
            R.id.recentTasks,

            R.id.getMemoryInfo})
    public void onClick(View view) {

        setTitle(getTitle() + "#");

        switch (view.getId()) {

            case R.id.runningAppProcesses:
                runningAppProcesses(this);
                
                break;

            case R.id.runningServices:
                runningServices(this);
                break;

            case R.id.appTasks:
                appTasks(this);
                break;

            // ------------------------------------------------------

            case R.id.runningTasks:
                runningTasks(this);
                break;

            case R.id.recentTasks:
                recentTasks(this);
                break;

            case R.id.getMemoryInfo:
                getMemoryInfo(this);
                break;
        }
    }

    // ---------------------------------------------------------------------------------

    public void killBackgroundProcesses(Context context, String packageName) {
        log("killBackgroundProcesses: // --------------------------------------------------------");
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.killBackgroundProcesses(packageName);
    }

    public void clearApplicationUserData(Context context, String packageName) {
        log("clearApplicationUserData: // --------------------------------------------------------");
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.clearApplicationUserData();
    }

    public void clearWatchHeapLimit(Context context, String packageName) {
        log("clearWatchHeapLimit: // --------------------------------------------------------");
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.clearWatchHeapLimit();
    }

    public void restartPackage(Context context, String packageName) {
        log("restartPackage: // --------------------------------------------------------");
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.restartPackage(packageName);
    }

    // ---------------------------------------------------------------------------------

    /**
     * 获得系统运行的进程
     */
    public List<ActivityManager.RunningAppProcessInfo> runningAppProcesses(Context context) {
        log("#########################################");
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcessInfos) {
            runningAppProcessInfo(runningAppProcessInfo);
        }
        return runningAppProcessInfos;
    }

    /**
     * 获得当前正在运行的服务
     */
    public List<ActivityManager.RunningServiceInfo> runningServices(Context context) {
        log("#########################################");
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = activityManager.getRunningServices(100);
        for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServiceInfos) {
            runningServiceInfo(runningServiceInfo);
        }
        return runningServiceInfos;
    }

    /**
     * 获得系统全部任务栈
     */
    public List<ActivityManager.AppTask> appTasks(Context context) {
        log("#########################################");
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.AppTask> appTasks = null;
        if (Build.VERSION.SDK_INT >= 21) {
            appTasks = activityManager.getAppTasks();
            for (ActivityManager.AppTask appTask : appTasks) {
                appTask(appTask);
            }
        }
        return appTasks;
    }

    /**
     * 获得当前正在运行的任务栈
     */
    public List<ActivityManager.RunningTaskInfo> runningTasks(Context context) {
        log("#########################################");
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1000);
        for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTaskInfos) {
            runningTaskInfo(runningTaskInfo);
        }
        return runningTaskInfos;
    }

    /**
     * 获得最近运行的任务栈
     */
    public List<ActivityManager.RecentTaskInfo> recentTasks(Context context) {
        log("#########################################");
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RecentTaskInfo> recentTaskInfos = activityManager.getRecentTasks(100, 1);
        for (ActivityManager.RecentTaskInfo recentTaskInfo : recentTaskInfos) {
            recentTaskInfo(recentTaskInfo);
        }
        return recentTaskInfos;
    }

    private void runningAppProcessInfo(ActivityManager.RunningAppProcessInfo runningAppProcessInfo) {
        log("--------------[runningAppProcessInfo]--------------");
        log("processName=" + runningAppProcessInfo.processName);
        log("pid=" + runningAppProcessInfo.pid);
        log("uid=" + runningAppProcessInfo.uid);
        log("describeContents=" + runningAppProcessInfo.describeContents());
        for (String pkg : runningAppProcessInfo.pkgList) {
            log("pkg=" + pkg);
        }

        log("lastTrimLevel=" + runningAppProcessInfo.lastTrimLevel);
        log("lru=" + runningAppProcessInfo.lru);
        // log("processState=" + runningAppProcessInfo.processState);

        log("importance=" + runningAppProcessInfo.importance);
        log("importanceReasonPid=" + runningAppProcessInfo.importanceReasonPid);
        log("importanceReasonComponent=" + runningAppProcessInfo.importanceReasonComponent);
        log("importanceReasonCode=" + runningAppProcessInfo.importanceReasonCode);
        // log("importanceReasonImportance=" + runningAppProcessInfo.importanceReasonImportance);
    }

    private void runningServiceInfo(ActivityManager.RunningServiceInfo runningServiceInfo) {
        log("--------------[runningServiceInfo]--------------");
        log("service=" + runningServiceInfo.service);
        log("process=" + runningServiceInfo.process);
        log("describeContents=" + runningServiceInfo.describeContents());

        log("pid=" + runningServiceInfo.pid);
        log("uid=" + runningServiceInfo.uid);

        log("foreground=" + runningServiceInfo.foreground);
        log("started=" + runningServiceInfo.started);

        log("clientLabel=" + runningServiceInfo.clientLabel);
        log("clientCount=" + runningServiceInfo.clientCount);
        log("activeSince=" + runningServiceInfo.activeSince);
        log("crashCount=" + runningServiceInfo.crashCount);
        log("lastActivityTime=" + runningServiceInfo.lastActivityTime);
        log("restarting=" + runningServiceInfo.restarting);
        log("flags=" + runningServiceInfo.flags);
    }

    private void appTask(ActivityManager.AppTask appTask) {
        log("--------------[appTask]--------------");
        ActivityManager.RecentTaskInfo recentTaskInfo = appTask.getTaskInfo();
        recentTaskInfo(recentTaskInfo);
        // appTask.moveToFront();
        // appTask.finishAndRemoveTask();
        // appTask.startActivity(context, new Intent(), new Bundle());
        // appTask.setExcludeFromRecents(true);
    }

    private void runningTaskInfo(ActivityManager.RunningTaskInfo runningTaskInfo) {
        log("--------------[runningTaskInfo]--------------");
        log("id=" + runningTaskInfo.id);
        log("baseActivity=" + runningTaskInfo.baseActivity);
        log("topActivity=" + runningTaskInfo.topActivity);
        log("thumbnail=" + runningTaskInfo.thumbnail);
        log("description=" + runningTaskInfo.description);
        log("numActivities=" + runningTaskInfo.numActivities);
        log("numRunning=" + runningTaskInfo.numRunning);
        // log("lastActiveTime=" + runningTaskInfo.lastActiveTime);
        log("describeContents=" + runningTaskInfo.describeContents());
    }

    private void recentTaskInfo(ActivityManager.RecentTaskInfo recentTaskInfo) {
        log("--------------[recentTaskInfo]--------------");
        log("id=" + recentTaskInfo.id);
        log("affiliatedTaskId=" + recentTaskInfo.affiliatedTaskId);

        log("description=" + recentTaskInfo.description);
        if (Build.VERSION.SDK_INT >= 21) {
            log("taskDescription=" + recentTaskInfo.taskDescription);
        }

        log("baseIntent=" + recentTaskInfo.baseIntent);

        // log("numActivities=" + recentTaskInfo.numActivities);
        log("origActivity=" + recentTaskInfo.origActivity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            log("baseActivity=" + recentTaskInfo.baseActivity);
            log("topActivity=" + recentTaskInfo.topActivity);
        }

        log("persistentId=" + recentTaskInfo.persistentId);
        // log("stackId=" + recentTaskInfo.stackId);
        // log("userId=" + recentTaskInfo.userId);

        // log("firstActiveTime=" + recentTaskInfo.firstActiveTime);
        // log("lastActiveTime=" + recentTaskInfo.lastActiveTime);
        // log("affiliatedTaskColor=" + recentTaskInfo.affiliatedTaskColor);
    }

    // ---------------------------------------------------------------------------------

    /**
     * 获取系统可用内存信息
     */
    public ActivityManager.MemoryInfo getMemoryInfo(Context context) {
        log("#########################################");
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        amMemoryInfo(memoryInfo);
        return memoryInfo;
    }

    /**
     * 获取系统可用内存信息
     */
    public Debug.MemoryInfo[] getProcessMemoryInfo(Context context, int[] pids) {
        log("#########################################");
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        Debug.MemoryInfo[] memoryInfos = activityManager.getProcessMemoryInfo(pids);
        for (Debug.MemoryInfo memoryInfo : memoryInfos) {
            debugMemoryInfo(memoryInfo);
        }
        return memoryInfos;
    }

    private void amMemoryInfo(ActivityManager.MemoryInfo memoryInfo) {
        log("--------------[memoryInfo]--------------");
        log("availMem=" + memoryInfo.availMem);
        log("totalMem=" + memoryInfo.totalMem);
        log("threshold=" + memoryInfo.threshold);
        log("lowMemory=" + memoryInfo.lowMemory);
        log("");
        log("availMem=" + FormatUtil.format(memoryInfo.availMem));
        log("totalMem=" + FormatUtil.format(memoryInfo.totalMem));
        log("threshold=" + FormatUtil.format(memoryInfo.threshold));

        // log("hiddenAppThreshold=" + memoryInfo.hiddenAppThreshold);
        // log("secondaryServerThreshold=" + memoryInfo.secondaryServerThreshold);
        // log("visibleAppThreshold=" + memoryInfo.visibleAppThreshold);
        // log("foregroundAppThreshold=" + memoryInfo.foregroundAppThreshold);
    }

    private void debugMemoryInfo(Debug.MemoryInfo memoryInfo) {
        log("--------------[memoryInfo]--------------");
        log("dalvikPss=" + memoryInfo.dalvikPss);
//            log("dalvikSwappablePss=" + memoryInfo.dalvikSwappablePss);
        log("dalvikPrivateDirty=" + memoryInfo.dalvikPrivateDirty);
        log("dalvikSharedDirty=" + memoryInfo.dalvikSharedDirty);
//            log("dalvikPrivateClean=" + memoryInfo.dalvikPrivateClean);
//            log("dalvikSharedClean=" + memoryInfo.dalvikSharedClean);
//            log("dalvikSwappedOut=" + memoryInfo.dalvikSwappedOut);
        log("nativePss=" + memoryInfo.nativePss);
//            log("nativeSwappablePss=" + memoryInfo.nativeSwappablePss);
        log("nativePrivateDirty=" + memoryInfo.nativePrivateDirty);
        log("nativeSharedDirty=" + memoryInfo.nativeSharedDirty);
//            log("nativePrivateClean=" + memoryInfo.nativePrivateClean);
//            log("nativeSharedClean=" + memoryInfo.nativeSharedClean);
//            log("nativeSwappedOut=" + memoryInfo.nativeSwappedOut);
        log("otherPss=" + memoryInfo.otherPss);
//            log("otherSwappablePss=" + memoryInfo.otherSwappablePss);
        log("otherPrivateDirty=" + memoryInfo.otherPrivateDirty);
        log("otherSharedDirty=" + memoryInfo.otherSharedDirty);
//            log("otherPrivateClean=" + memoryInfo.otherPrivateClean);
//            log("otherSharedClean=" + memoryInfo.otherSharedClean);
//            log("otherSwappedOut=" + memoryInfo.otherSwappedOut);
//            log("otherStats=" + memoryInfo.otherStats);
        log("getTotalPss=" + memoryInfo.getTotalPss());
//            log("getTotalUss=" + memoryInfo.getTotalUss());
        log("getTotalSwappablePss=" + memoryInfo.getTotalSwappablePss());
        log("getTotalPrivateDirty=" + memoryInfo.getTotalPrivateDirty());
        log("getTotalSharedDirty=" + memoryInfo.getTotalSharedDirty());
        log("getTotalPrivateClean=" + memoryInfo.getTotalPrivateClean());
        log("getTotalSharedClean=" + memoryInfo.getTotalSharedClean());
//            log("getTotalSwappedOut=" + memoryInfo.getTotalSwappedOut());
//            log("getOtherPss=" + memoryInfo.getOtherPss());
//            log("getOtherSwappablePss=" + memoryInfo.getOtherSwappablePss());
//            log("getOtherPrivateDirty=" + memoryInfo.getOtherPrivateDirty());
//            log("getOtherSharedDirty=" + memoryInfo.getOtherSharedDirty());
//            log("getOtherPrivateClean=" + memoryInfo.getOtherPrivateClean());
//            log("getOtherPrivate=" + memoryInfo.getOtherPrivate());
//            log("getOtherSharedClean=" + memoryInfo.getOtherSharedClean());
//            log("getOtherSwappedOut=" + memoryInfo.getOtherSwappedOut());
    }
}
