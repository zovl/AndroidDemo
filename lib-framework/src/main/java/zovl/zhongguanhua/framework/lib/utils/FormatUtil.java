package zovl.zhongguanhua.framework.lib.utils;

import android.content.Context;
import android.text.format.Formatter;

import zovl.zhongguanhua.framework.lib.framework.AppManager;

public class FormatUtil {

    public static String format(long size) {
        Context context = AppManager.getInstance().getContext();
        return Formatter.formatFileSize(context, size);
    }
}
