package zovl.zhongguanhua.framework.lib.framework;

import android.app.Service;
import android.content.Context;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

/**
 * 功能：全局管理Application/Activiy/Service的实例
 */
public class AppManager {

	protected final String tag = this.getClass().getSimpleName();

	private AppManager() {
		super();
	}

	private static AppManager instance;

	/**
	 * 单一实例
	 */
	public static AppManager getInstance() {
		if (null == instance) {
			instance = new AppManager();
		}
		return instance;
	}

	/*****************************************  Application  *****************************************/

	private BaseApplication application;

	private Context context;

	public void setApplication(BaseApplication application) {
		this.application = application;
		setContext(application.getApplicationContext());
	}

	public BaseApplication getApplication() {
		return application;
	}

	public Context getContext() {
		return context;
	}

	private void setContext(Context context) {
		this.context = context;
	}

	/*****************************************  Activity  *****************************************/

	private Stack<BaseActivity> activities = new Stack<>();

	/**
	 * 功能：添加Activity
	 */
	public void addActivity(final BaseActivity activity) {
		synchronized (instance) {
			Log.d(tag, "[addActivity]" + activity);
			activities.push(activity);
			printAllActivity();
		}
	}

	/**
	 * 功能：获取当前Activity（堆栈中后一个压入的）
	 */
	public BaseActivity currentActivity() {
		synchronized (instance) {
			BaseActivity activity = null;
			if (!activities.isEmpty())
				activity = activities.peek();
			Log.d(tag, "[currentActivity]" + activity);
			return activity;
		}
	}

	/**
	 * 功能：结束当前Activity（堆栈中后一个压入的）
	 */
	public void finishCurrentActivity() {
		synchronized (instance) {
			BaseActivity activity = null;
			if (!activities.isEmpty())
				activity = activities.pop();
			Log.d(tag, "[finishCurrentActivity]" + activity);
			finishActivity(activity);
			printAllActivity();
		}
	}

	/**
	 * 功能：移除Activity
	 */
	public void removeActivity(BaseActivity activity) {
		synchronized (instance) {
			Log.d(tag, "[removeActivity]" + activity);
			if (activity != null) {
				activities.remove(activity);
			}
			printAllActivity();
		}
	}

	/**
	 * 功能：结束Activity
	 */
	public void finishActivity(BaseActivity activity) {
		synchronized (instance) {
			Log.d(tag, "[finishActivity]" + activity);
			if (activity != null) {
				activity.finish();
			}
			printAllActivity();
		}
	}

	/**
	 * 功能：结束Activity
	 */
	public void finishActivity(final Class<?> cls) {
		synchronized (instance) {
			Log.d(tag, "[finishActivity]" + cls.getSimpleName());
			if (cls != null)
				for(Iterator<BaseActivity> iterator = activities.iterator(); iterator.hasNext();) {
					BaseActivity activity = iterator.next();
					if (activity.getClass().equals(cls)) {
						finishActivity(activity);
					}
				}
			printAllActivity();
		}
	}

	/**
	 * 功能：结束Activity
	 */
	public void finishActivity(final Class<?>[] clss) {
		synchronized (instance) {
			for (int i = 0; i < clss.length; i++) {
				Log.d(tag, "[finishActivity]" + clss[i].getSimpleName());
			}
			for (int i = 0; i < clss.length; i++) {
				Class<?> cls = clss[i];
				for (BaseActivity activity : activities) {
					if (activity.getClass().equals(cls)) {
						finishActivity(activity);
					}
				}
			}
			printAllActivity();
		}
	}

	/**
	 * 功能：判断当前的Activity是否存在（堆栈中后一个压入的）
	 */
	public boolean isActivityAliive(final Class<?> cls) {
		synchronized (instance) {
			Log.d(tag, "[isActivityAliive]" + cls.getSimpleName());
			for (BaseActivity activity : activities) {
				if (activity.getClass().equals(cls)) {
					return true;
				}
			}
			return false;
		}
	}

	/**
	 * 功能：获得Activity
	 */
	public BaseActivity getActivity(final Class<?> cls) {
		synchronized (instance) {
			Log.d(tag, "[getActivity]" + cls.getSimpleName());
			for (BaseActivity activity : activities) {
				if (activity.getClass().equals(cls)) {
					return activity;
				}
			}
			return null;
		}
	}

	/**
	 * 功能：结束所有Activity
	 */
	public void finishAllActivity() {
		synchronized (instance) {
			int size = activities.size();
			Log.d(tag, "[finishAllActivity]" + size);
			for (int i = 0; i < size; i++) {
				if (null != activities.get(i)) {
					activities.get(i).finish();
				}
			}
			activities.clear();
			printAllActivity();
		}
	}

	/**
	 * 功能：打印所有Activity
	 */
	public void printAllActivity() {
		if (activities != null && activities.size() > 0) {
			Log.d(tag, "[printAllActivity]" + "------------------------------");
			for (int i = 0; i < activities.size(); i++) {
				Log.d(tag, "[printAllActivity]" + activities.get(i));
			}
			Log.d(tag, "[printAllActivity]" + "------------------------------");
		}
	}

	/**
	 * 功能：应用程序退出
	 */
	public void exit() {
		synchronized (instance) {
			try {
				finishAllActivity();
				Runtime.getRuntime().exit(0);
			} catch (Exception e) {
				Runtime.getRuntime().exit(-1);
			}
		}
	}

	/*****************************************   服务Service  *****************************************/

	private Vector<BaseService> services = new Vector<>();

	/**
	 * 功能：添加Service到集合
	 */
	public void addService(final BaseService service) {
		synchronized (instance) {
			if (service != null) {
				Log.d(tag, "[addService]" + service.getClass().getSimpleName());
				services.add(service);
				printAllService();
			}
		}
	}

	/**
	 * 功能：结束指定的Service
	 */
	public void removeService(final BaseService service) {
		synchronized (instance) {
			if (service != null) {
				Log.d(tag, "[removeService]" + service.getClass().getSimpleName());
				services.remove(service);
			}
			printAllService();
		}
	}

	/**
	 * 功能：结束指定类名的Service
	 */
	public void removeService(final Class<?> cls) {
		synchronized (instance) {
			if (cls != null) {
				Log.d(tag, "[removeService]" + cls.getSimpleName());
				for (BaseService service : services) {
					if (service.getClass().equals(cls)) {
						removeService(service);
					}
				}
				printAllService();
			}
		}
	}

	/**
	 * 功能：获得Service
	 */
	public Service getService(final Class<?> cls) {
		synchronized (instance) {
			if (cls != null) {
				Log.d(tag, "[getService]" + cls.getSimpleName());
				for (BaseService service : services) {
					if (service.getClass().equals(cls)) {
						return service;
					}
				}
			}
			return null;
		}
	}

	/**
	 * 功能：打印所有Service
	 */
	public void printAllService() {
		if (services != null && services.size() > 0) {
			Log.d(tag, "[printAllService]" + "------------------------------");
			for (int i = 0; i < services.size(); i++) {
				Log.d(tag, "[printAllService]" + services.get(i));
			}
			Log.d(tag, "[printAllService]" + "------------------------------");
		}
	}

	/*****************************************   视图View  *****************************************/

	private Set<View> views = new HashSet<>();

	/**
	 * 功能：添加View
	 */
	public void addView(final View view) {
		synchronized (instance) {
			if (view != null) {
				Log.d(tag, "[addView]" + view.getClass().getSimpleName());
				views.add(view);
				printAllView();
			}
		}
	}

	/**
	 * 功能：移除View
	 */
	public void removeView(final View view) {
		synchronized (instance) {
			if (view != null) {
				Log.d(tag, "[removeView]" + view.getClass().getSimpleName());
				views.remove(view);
			}
			printAllView();
		}
	}

	/**
	 * 功能：移除View
	 */
	public void removeView(final Class<?> cls) {
		synchronized (instance) {
			if (cls != null) {
				Log.d(tag, "[removeView]" + cls.getSimpleName());
				for (View view : views) {
					if (view.getClass().equals(cls)) {
						removeView(view);
					}
				}
				printAllView();
			}
		}
	}

	/**
	 * 功能：获得View
	 */
	public View getView(final Class<?> cls) {
		synchronized (instance) {
			if (cls != null) {
				Log.d(tag, "[getView]" + cls.getSimpleName());
				for (View view : views) {
					if (view.getClass().equals(cls)) {
						return view;
					}
				}
			}
			return null;
		}
	}

	/**
	 * 功能：获得View
	 */
	public List<View> getViews(final Class<?> cls) {
		synchronized (instance) {
			if (cls != null) {
				Log.d(tag, "[getViews]" + cls.getSimpleName());
				List<View> views = new ArrayList<View>();
				for (View view : this.views) {
					if (view.getClass().equals(cls)) {
						views.add(view);
					}
				}
				return views;
			}
			return null;
		}
	}

	/**
	 * 功能：打印所有Views
	 */
	public void printAllView() {
		if (views != null && views.size() > 0) {
			Log.d(tag, "[printAllView]" + "------------------------------");
			for (View view: views) {
				Log.d(tag, "[printAllView]" + view);
			}
			Log.d(tag, "[printAllView]" + "------------------------------");
		}
	}
}