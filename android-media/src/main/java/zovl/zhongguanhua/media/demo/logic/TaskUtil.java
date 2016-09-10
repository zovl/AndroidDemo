package zovl.zhongguanhua.media.demo.logic;

import android.app.Activity;
import android.os.Build;
import android.util.Log;

public class TaskUtil {

    public static final String TAG = TaskUtil.class.getSimpleName();

    public static void clearTaskAndAffinity(Activity activity) {
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                activity.finishAndRemoveTask();
                Log.d(TAG, "clearTaskAndAffinity: finishAndRemoveTask");
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    activity.finishAffinity();
                    Log.d(TAG, "clearTaskAndAffinity: finishAffinity");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    try {
                        activity.finish();
                        Log.d(TAG, "clearTaskAndAffinity: finish");
                    } catch (Exception ee) {
                        ee.printStackTrace();
                    }
                }
            }
        } else {
            try {
                activity.finishAffinity();
                Log.d(TAG, "clearTaskAndAffinity: finishAffinity");
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    activity.finish();
                    Log.d(TAG, "clearTaskAndAffinity: finish");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
