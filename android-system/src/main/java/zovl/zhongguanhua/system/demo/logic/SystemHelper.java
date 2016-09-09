package zovl.zhongguanhua.system.demo.logic;

import android.os.SystemClock;
import android.util.Log;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 功能：系统工具
 */
public class SystemHelper {

	public static final String TAG = SystemHelper.class.getSimpleName();

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	public static String system() {
		Log.d(TAG, "----------------[system]------------------");

		StringBuffer buffer = new StringBuffer();
		buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");

		Map<String, String> env = System.getenv();//获取系统所有环境变量
		for (String  name: env.keySet()) {
			String value = env.get(name);
			buffer.append("env--name=" + name);
			buffer.append("env--value=" + value);
		}
		String JAVA_HOME = System.getenv("JAVA_HOME");//获取指定环境变量
		buffer.append("env--JAVA_HOME=" + JAVA_HOME);

		long currentTimeMillis = System.currentTimeMillis();
		long nanoTime = System.nanoTime();
		buffer.append("currentTimeMillis=" + currentTimeMillis);
		buffer.append("nanoTime=" + nanoTime);

		long currentThreadTimeMillis = SystemClock.currentThreadTimeMillis();
		long elapsedRealtime = SystemClock.elapsedRealtime();
		long elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos();
		long uptimeMillis = SystemClock.uptimeMillis();
		buffer.append("currentThreadTimeMillis=" + currentThreadTimeMillis);
		buffer.append("elapsedRealtime=" + elapsedRealtime);
		buffer.append("elapsedRealtimeNanos=" + elapsedRealtimeNanos);
		buffer.append("uptimeMillis=" + uptimeMillis);

		Log.d(TAG, "system: " + buffer.toString() + "\n");
		return buffer.toString();
	}

	// ---------------------------------------------------------------------------------

	public static String properties() {
		Log.d(TAG, "----------------[properties]------------------");

		Properties properties = System.getProperties();//获取所有系统属性
		String os_name = properties.getProperty("os.name");//获取指定环境变量

		StringBuffer buffer = new StringBuffer();
		buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");
		buffer.append("os_name=" + os_name);

		Log.d(TAG, "properties: " + buffer.toString() + "\n");
		return buffer.toString();
	}

	public static String propertyNames() {
		Log.d(TAG, "----------------[propertyNames]------------------");

		StringBuffer buffer = new StringBuffer();
		buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");

		Properties properties = System.getProperties();//获取所有系统属性
		Enumeration enumeration = properties.propertyNames();//得到配置文件的名字
		while(enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			String value = properties.getProperty(key);

			buffer.append("propertyNames: key=" + key);
			buffer.append("propertyNames: value=" + value);
		}

		Log.d(TAG, "properties: " + buffer.toString() + "\n");
		return buffer.toString();
	}

	public static String  stringPropertyNames() {
		Log.d(TAG, "----------------[stringPropertyNames]------------------");

		StringBuffer buffer = new StringBuffer();
		buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");

		Properties properties = System.getProperties();//获取所有系统属性
		Set<String> names = properties.stringPropertyNames();//得到配置文件的名字
		Iterator<String> it = names.iterator();
		while(it.hasNext()) {
			String name = it.next();
			buffer.append("stringPropertyNames: name=" + name);
		}

		Log.d(TAG, "properties: " + buffer.toString() + "\n");
		return buffer.toString();
	}

	public static void list() {
		Log.d(TAG, "----------------[list]------------------");

		Properties properties = System.getProperties();//获取所有系统属性
		properties.list(System.out);
	}
}
