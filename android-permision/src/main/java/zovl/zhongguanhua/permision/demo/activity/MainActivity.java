package zovl.zhongguanhua.permision.demo.activity;

import android.Manifest;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.framework.lib.utils.ToastHelper;
import zovl.zhongguanhua.permision.demo.R;

public class MainActivity extends TBaseActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.root)
    View root;

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.app_name);
    }

    @OnClick({R.id.judgePermission,
            R.id.openSettings,
            R.id.checkCallingPermission,
            R.id.enforceCallingPermission})
    public void onClick(View view) {

        setTitle(getTitle() + "#");

        switch (view.getId()) {

            case R.id.judgePermission:
                boolean flag = hasPermision(this);
                ToastHelper.l(String.valueOf(flag));
                break;

            case R.id.openSettings:
                startSettings(this);
                break;

            case R.id.checkCallingPermission:
                checkCallingPermission(this, Manifest.permission.RECORD_AUDIO);
                break;

            case R.id.enforceCallingPermission:
                enforceCallingPermission(this, Manifest.permission.RECORD_AUDIO);
                break;

            case R.id.checkPermission:
                checkPermission(this, Manifest.permission.RECORD_AUDIO);
                break;

            case R.id.enforcePermission:
                enforcePermission(this, Manifest.permission.RECORD_AUDIO);
                break;
        }
    }

    // ---------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------

    /**
     * 判断当前应用是否有查看应用使用情况的权限（5.0+）
     */
    public static  boolean hasPermision(Context context){
        if(android.os.Build.VERSION.SDK_INT >= 21){
            long ts = System.currentTimeMillis();
            UsageStatsManager manager = (UsageStatsManager) context.getSystemService(Service.USAGE_STATS_SERVICE);
            List<UsageStats> usageStatses = manager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, 0, ts);
            if (usageStatses == null || usageStatses.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 进入设置中心开启应用权限
     */
    public static void startSettings(Context context) {
        // 需要赋予权限才能进入设置中心
        // android.settings.USAGE_ACCESS_SETTINGS
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------

    /**
     * 检查应用权限
     */
    public static boolean checkCallingPermission(Context context, String permission) {
        // Quick check: if the calling permission is me, it's all okay.
        if (Binder.getCallingPid() == Process.myPid()) {
            Log.d(TAG, "checkCallingPermission: getCallingPid == myPid");
            return true;
        }
        if (context.checkCallingPermission(permission)
                == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "checkCallingPermission: Permission Granted");
            return true;
        }
        String msg = "Permission Denial: " + " from pid="
                + Binder.getCallingPid()
                + ", uid=" + Binder.getCallingUid()
                + " requires " + permission;
        Log.d(TAG, "checkCallingPermission: " + msg);
        return false;
    }

    /**
     * 请求应用权限
     */
    public static void enforceCallingPermission(Context context, String permission) {
        try {
            context.enforceCallingPermission(permission, null);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查应用权限
     */
    public static boolean checkPermission(Context context, String permission) {
        if (context.checkPermission(permission, Process.myPid(), Process.myUid())
                == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "checkPermission: Permission Granted");
            return true;
        }
        String msg = "Permission Denial: " + " from pid="
                + Binder.getCallingPid()
                + ", uid=" + Binder.getCallingUid()
                + " requires " + permission;
        Log.d(TAG, "checkPermission: " + msg);
        return false;
    }

    /**
     * 请求应用权限
     */
    public static void enforcePermission(Context context, String permission) {
        try {
            context.enforcePermission(permission, Process.myPid(), Process.myUid(), null);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}
