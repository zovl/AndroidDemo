package zovl.zhongguanhua.system.demo.ui.activity;

import android.app.ActivityManager;
import android.os.Debug;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.system.demo.R;
import zovl.zhongguanhua.system.demo.logic.ActivityHelper;

public class ActivityManagerActivity extends TBaseActivity {

    @Bind(R.id.pid)
    EditText pid;

    public int getPid() {
        try {
            return Integer.valueOf(pid.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return -1;
    }

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

            R.id.getMemoryInfo,
            R.id.getProcessMemoryInfo})
    public void onClick(View view) {

        setTitle(getTitle() + "#");

        switch (view.getId()) {

            case R.id.runningAppProcesses:
                List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos = ActivityHelper.runningAppProcesses(this);
                for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcessInfos) {
                    String s = ActivityHelper.runningAppProcessInfo(runningAppProcessInfo);
                    setText(s);
                }
                break;

            case R.id.runningServices:
                List<ActivityManager.RunningServiceInfo> runningServiceInfos = ActivityHelper.runningServices(this);
                for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServiceInfos) {
                    String s = ActivityHelper.runningServiceInfo(runningServiceInfo);
                    setText(s);
                }
                break;

            case R.id.appTasks:
                List<ActivityManager.AppTask> appTasks = ActivityHelper.appTasks(this);
                for (ActivityManager.AppTask appTask : appTasks) {
                    String s = ActivityHelper.appTask(appTask);
                    setText(s);
                }
                break;

            // ------------------------------------------------------

            case R.id.runningTasks:
                List<ActivityManager.RunningTaskInfo> runningTaskInfos = ActivityHelper.runningTasks(this);
                for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTaskInfos) {
                    String s = ActivityHelper.runningTaskInfo(runningTaskInfo);
                    setText(s);
                }
                break;

            case R.id.recentTasks:
                List<ActivityManager.RecentTaskInfo> recentTaskInfos = ActivityHelper.recentTasks(this);
                for (ActivityManager.RecentTaskInfo recentTaskInfo : recentTaskInfos) {
                    String s = ActivityHelper.recentTaskInfo(recentTaskInfo);
                    setText(s);
                }
                break;

            case R.id.getMemoryInfo:
                ActivityManager.MemoryInfo memoryInfo = ActivityHelper.getMemoryInfo(this);
                String s = ActivityHelper.memoryInfo(memoryInfo);
                setText(s);
                break;

            case R.id.getProcessMemoryInfo:
                Debug.MemoryInfo info = ActivityHelper.getProcessMemoryInfo(this, getPid());
                s = ActivityHelper.debugMemoryInfo(info);
                setText(s);
                break;
        }
    }

    // ---------------------------------------------------------------------------------
}
