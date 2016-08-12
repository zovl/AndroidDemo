package zovl.zhongguanhua.system.demo.logic;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import zovl.zhongguanhua.framework.lib.framework.AppManager;

/**
 * 功能：屏幕工具
 */
public class ScreenUtil {

	public static final String TAG = ScreenUtil.class.getSimpleName();

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	/**
	 * 功能：屏幕单位转换
	 */
	public static int px2dp(float pxValue) {
		Context context = AppManager.getInstance().getContext();
		return px2dp(context, pxValue);
	}

	/**
	 * 功能：屏幕单位转换
	 */
	public static int dp2px(float dpValue) {
		Context context = AppManager.getInstance().getContext();
		return dp2px(context, dpValue);
	}

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	/**
	 * 功能：屏幕单位转换
	 */
	public static int px2dp(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 功能：屏幕单位转换
	 */
	public static int dp2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	
	/**
	 * 功能：获得屏幕宽度（px）
	 */
	public static int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}
	
	/**
	 * 功能：获得屏幕高度（px）
	 */
	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	/**
	 * 获得屏幕宽度（px）
	 */
	public static int getScreenWidth(WindowManager manager) {
		DisplayMetrics metrics = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(metrics);
		return metrics.widthPixels;
	}

	/**
	 * 获得屏幕高度（px）
	 */
	public static int getScreenHeight(WindowManager manager) {
		DisplayMetrics metrics = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(metrics);
		return metrics.heightPixels;
	}

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	/**
	 * 获得屏幕比例
	 */
	public static float getAreaOneRatio(Activity activity){
		Log.d(TAG, "----------------[restartPackage]------------------");
		int[] size = getAreaOne(activity);
		int a = size[0];
		int b = size[1];
		float ratio = 0f;
		try {
			if (a > b) {
                ratio = (float) a / (float) b;
            } else {
                ratio = (float) b / (float) a;
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d(TAG, "getAreaOneRatio: ratio=" + ratio);
		return ratio;
	}

	/**
	 * 获得屏幕尺寸（宽 x 高）（px）
	 */
	public static int[] getAreaOne(Activity activity) {
		Log.d(TAG, "----------------[getAreaOne]------------------");
		Display display = activity.getWindowManager().getDefaultDisplay();
		Point point = new Point();
		display.getSize(point);
		Log.d(TAG, "getAreaOne: width=" + point.x);
		Log.d(TAG, "getAreaOne: height=" + point.y);
		return new int[]{ point.x, point.y };
	}

	/**
	 * 获得应用区域尺寸（宽 x 高）（px）
	 */
	public static int[] getAreaTwo(Activity activity) {
		Log.d(TAG, "----------------[getAreaTwo]------------------");
		Rect rect = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
		Log.d(TAG, "getAreaOne: width=" + rect.width());
		Log.d(TAG, "getAreaOne: height=" + rect.height());
		return new int[]{ rect.width(), rect.height() };
	}

	/**
	 * 获得用户绘图区域尺寸（宽 x 高）（px）
	 */
	public static int[] getAreaThree(Activity activity) {
		Log.d(TAG, "----------------[getAreaThree]------------------");
		Rect rect = new Rect();
		activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(rect);
		Log.d(TAG, "getAreaOne: width=" + rect.width());
		Log.d(TAG, "getAreaOne: height=" + rect.height());
		return new int[]{ rect.width(), rect.height() };
	}
}
