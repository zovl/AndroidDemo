package zovl.zhongguanhua.framework.lib.utils;

import android.util.Log;
import android.view.View;

public class ViewUtil {

    public static final String TAG = ViewUtil.class.getSimpleName();

    // -------------------------------------------------------------------------------

    public static void enabled(final View view, long delayMillis) {
        Log.d(TAG, "enabled: // ----------------------------------------------------------");
        Log.d(TAG, "enabled: delayMillis=" + delayMillis);
        if (delayMillis < 0) {
            delayMillis = 0;
        }
        if (view != null) {
            view.setEnabled(false);
            view.setClickable(false);
            UITask.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setEnabled(true);
                    view.setClickable(true);
                }
            }, delayMillis);
        }
    }
}
