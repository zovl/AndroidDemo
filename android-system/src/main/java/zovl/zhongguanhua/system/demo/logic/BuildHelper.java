package zovl.zhongguanhua.system.demo.logic;

import android.os.Build;
import android.util.Log;

/**
 * 功能：系统工具
 */
public class BuildHelper {

	public static final String TAG = BuildHelper.class.getSimpleName();

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	public static String build() {
		Log.d(TAG, "----------------[build]------------------");

		String board = Build.BOARD;
		String bootloader = Build.BOOTLOADER;
		String brand = Build.BRAND;
		String device = Build.DEVICE;
		String display = Build.DISPLAY;
		String fingerprint = Build.FINGERPRINT;
		String hardware = Build.HARDWARE;
		String host = Build.HOST;
		String id = Build.ID;
		String manufacturer = Build.MANUFACTURER;
		String model = Build.MODEL;
		String product = Build.PRODUCT;
		String serial = Build.SERIAL;
		String tags = Build.TAGS;
		String type = Build.TYPE;
		String user = Build.USER;
		String cpuAbi = Build.CPU_ABI;
		String cpuAbi2 = Build.CPU_ABI2;
		String[] supported32BitAbis = new String[0];
		String[] supported64BitAbis = new String[0];
		String[] supportedAbis = new String[0];
		if (Build.VERSION.SDK_INT >= 21) {
			supported32BitAbis = Build.SUPPORTED_32_BIT_ABIS;
			supported64BitAbis = Build.SUPPORTED_64_BIT_ABIS;
			supportedAbis = Build.SUPPORTED_ABIS;
		}
		String radio = Build.RADIO;
		long time = Build.TIME;
		String radioVersion = Build.getRadioVersion();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");
		buffer.append("board=" + board + "\n");
		buffer.append("bootloader=" + bootloader + "\n");
		buffer.append("brand=" + brand + "\n");
		buffer.append("device=" + device + "\n");
		buffer.append("display=" + display + "\n");
		buffer.append("fingerprint=" + fingerprint + "\n");
		buffer.append("hardware=" + hardware + "\n");
		buffer.append("host=" + host + "\n");
		buffer.append("id=" + id + "\n");
		buffer.append("manufacturer=" + manufacturer + "\n");
		buffer.append("model=" + model + "\n");
		buffer.append("product=" + product + "\n");
		buffer.append("serial=" + serial + "\n");
		buffer.append("tags=" + tags + "\n");
		buffer.append("type=" + type + "\n");
		buffer.append("user=" + user + "\n");
		buffer.append("cpuAbi=" + cpuAbi + "\n");
		buffer.append("cpuAbi2=" + cpuAbi2 + "\n");
		for (String supported32BitAbi : supported32BitAbis) {
			buffer.append("supported32BitAbi=" + supported32BitAbi + "\n");
		}
		for (String supported64BitAbi : supported64BitAbis) {
			buffer.append("supported64BitAbi=" + supported64BitAbi + "\n");
		}
		for (String supportedAbi : supportedAbis) {
			buffer.append("supportedAbi=" + supportedAbi + "\n");
		}
		buffer.append("time=" + time + "\n");
		buffer.append("radioVersion=" + radioVersion + "\n");

		Log.d(TAG, "build: " + buffer.toString() + "\n");
		return buffer.toString();
	}

	public static String version() {
		Log.d(TAG, "----------------[version]------------------");

		String baseOs = Build.VERSION.BASE_OS;
		String sdk = Build.VERSION.SDK;
		int sdkInt = Build.VERSION.SDK_INT;
		// int previewSdkInt = Build.VERSION.PREVIEW_SDK_INT;
		String codename = Build.VERSION.CODENAME;
		String incremental = Build.VERSION.INCREMENTAL;
		String release = Build.VERSION.RELEASE;
		String securityPatch = Build.VERSION.SECURITY_PATCH;

		StringBuffer buffer = new StringBuffer();
		buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");
		buffer.append("baseOs=" + baseOs + "\n");
		buffer.append("sdk=" + sdk + "\n");
		buffer.append("sdkInt=" + sdkInt + "\n");
		// buffer.append("previewSdkInt=" + previewSdkInt + "\n");
		buffer.append("codename=" + codename + "\n");
		buffer.append("incremental=" + incremental + "\n");
		buffer.append("release=" + release + "\n");
		buffer.append("securityPatch=" + securityPatch + "\n");

		Log.d(TAG, "build: " + buffer.toString() + "\n");
		return buffer.toString();
	}

	public static String versionCodes() {
		Log.d(TAG, "----------------[versionCodes]------------------");

		StringBuffer buffer = new StringBuffer();
		buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");
		buffer.append("LOLLIPOP=" + Build.VERSION_CODES.LOLLIPOP + "\n");
		buffer.append("M=" + Build.VERSION_CODES.M + "\n");
		buffer.append("HONEYCOMB_MR2=" + Build.VERSION_CODES.HONEYCOMB_MR2 + "\n");
		buffer.append("HONEYCOMB=" + Build.VERSION_CODES.HONEYCOMB + "\n");
		buffer.append("KITKAT=" + Build.VERSION_CODES.KITKAT + "\n");
		buffer.append("BASE=" + Build.VERSION_CODES.BASE + "\n");
		buffer.append("BASE_1_1=" + Build.VERSION_CODES.BASE_1_1 + "\n");
		buffer.append("CUPCAKE=" + Build.VERSION_CODES.CUPCAKE + "\n");
		buffer.append("CUR_DEVELOPMENT=" + Build.VERSION_CODES.CUR_DEVELOPMENT + "\n");
		buffer.append("DONUT=" + Build.VERSION_CODES.DONUT + "\n");
		buffer.append("ECLAIR=" + Build.VERSION_CODES.ECLAIR + "\n");
		buffer.append("ECLAIR_0_1=" + Build.VERSION_CODES.ECLAIR_0_1 + "\n");
		buffer.append("ECLAIR_MR1=" + Build.VERSION_CODES.ECLAIR_MR1 + "\n");
		buffer.append("FROYO=" + Build.VERSION_CODES.FROYO + "\n");
		buffer.append("GINGERBREAD=" + Build.VERSION_CODES.GINGERBREAD + "\n");
		buffer.append("GINGERBREAD_MR1=" + Build.VERSION_CODES.GINGERBREAD_MR1 + "\n");
		buffer.append("HONEYCOMB_MR1=" + Build.VERSION_CODES.HONEYCOMB_MR1 + "\n");
		buffer.append("HONEYCOMB_MR2=" + Build.VERSION_CODES.HONEYCOMB_MR2 + "\n");
		buffer.append("ICE_CREAM_SANDWICH=" + Build.VERSION_CODES.ICE_CREAM_SANDWICH + "\n");
		buffer.append("ICE_CREAM_SANDWICH_MR1=" + Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1 + "\n");
		buffer.append("JELLY_BEAN=" + Build.VERSION_CODES.JELLY_BEAN + "\n");
		buffer.append("JELLY_BEAN_MR1=" + Build.VERSION_CODES.JELLY_BEAN_MR1 + "\n");
		buffer.append("JELLY_BEAN_MR2=" + Build.VERSION_CODES.JELLY_BEAN_MR2 + "\n");
		buffer.append("KITKAT_WATCH=" + Build.VERSION_CODES.KITKAT_WATCH + "\n");
		buffer.append("LOLLIPOP_MR1=" + Build.VERSION_CODES.LOLLIPOP_MR1 + "\n");

		Log.d(TAG, "build: " + buffer.toString() + "\n");
		return buffer.toString();
	}
}
