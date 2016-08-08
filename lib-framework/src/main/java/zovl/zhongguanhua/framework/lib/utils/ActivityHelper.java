package zovl.zhongguanhua.framework.lib.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ActivityHelper {

    private static final String TAG = ActivityHelper.class.getSimpleName();

    // ---------------------------------------------------------------------------------

    public static void startActivity(Context context, Class<?> cls) {
        Log.d(TAG, "startActivity: " + context);
        Log.d(TAG, "startActivity: " + cls);
        Intent intent = new Intent();
        intent.setClass(context, cls);
        context.startActivity(intent);
    }
}
