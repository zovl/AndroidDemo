package zovl.zhongguanhua.framework.lib.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Toast;

import zovl.zhongguanhua.framework.lib.framework.AppManager;

/**
 * 功能：吐司
 */
@SuppressLint("InflateParams")
public class ToastHelper {

    protected final String action = this.getClass().getName();
    protected final String tag = this.getClass().getSimpleName();

    private static ToastHelper instance;

    public static ToastHelper getInstance() {
        if (instance == null) {
            synchronized (ToastHelper.class) {
                if (instance == null) {
                    instance = new ToastHelper();
                }
            }
        }
        return instance;
    }

	private Context context;
	private LayoutInflater inflater;

    private ToastHelper() {
        context = AppManager.getInstance().getContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        context = AppManager.getInstance().getContext();
    }

	private static Handler h = new Handler(Looper.getMainLooper());

	private Toast toast;

	@SuppressWarnings("unused")
	private void a(final String text, final int duration, final int gravity, final int xOffset, final int yOffset) {
		if (StringUtil.isNull(text.toString())) {
			return;
		}
		h.post(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    if (toast != null) {
                        toast.setText(text);
                        toast.setDuration(duration);
                    } else {
                        toast = Toast.makeText(context.getApplicationContext(), text.toString().trim(), duration);
                    }
                    toast.show();
                }
            }
        });
	}

    private void a(final int res, final int duration, final int gravity, final int xOffset, final int yOffset) {
        if (res == 0)
            return;
        String text = null;
        try {
            text = context.getResources().getString(res);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        if (text == null)
            return;
        a(text, duration, gravity, xOffset, yOffset);
    }

    public static void s(final int res) {
        Context context = AppManager.getInstance().getContext();
        getInstance().a(res, Toast.LENGTH_SHORT, Gravity.BOTTOM, 0, ScreenUtil.dp2px(80));
    }

    public static void s(final String text) {
        Context context = AppManager.getInstance().getContext();
        getInstance().a(text, Toast.LENGTH_SHORT, Gravity.BOTTOM, 0, ScreenUtil.dp2px(80));
    }

    public static void l(final int res) {
        Context context = AppManager.getInstance().getContext();
        getInstance().a(res, Toast.LENGTH_LONG, Gravity.BOTTOM, 0, ScreenUtil.dp2px(80));
    }

    public static void l(final String text) {
        Context context = AppManager.getInstance().getContext();
        getInstance().a(text, Toast.LENGTH_LONG, Gravity.BOTTOM, 0, ScreenUtil.dp2px(80));
    }

    public static void x(final String text, final int duration, final int gravity, final int xOffset, final int yOffset) {
        getInstance().a(text, duration, gravity, xOffset, yOffset);
    }
}
