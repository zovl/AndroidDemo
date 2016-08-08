package zovl.zhongguanhua.framework.lib.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import zovl.zhongguanhua.framework.lib.framework.AppManager;

/**
 * 功能：屏幕工具
 */
public class ScreenUtil {

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
	
	/**
	 * 功能：获得屏幕宽度（px）
	 */
	public static int getScreenWidth() {
		Context context = AppManager.getInstance().getContext();
		return context.getResources().getDisplayMetrics().widthPixels;
	}
	
	/**
	 * 功能：获得屏幕高度（px）
	 */
	public static int getScreenHeight() {
		Context context = AppManager.getInstance().getContext();
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
}
