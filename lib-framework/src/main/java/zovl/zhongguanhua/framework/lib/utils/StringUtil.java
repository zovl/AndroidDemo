package zovl.zhongguanhua.framework.lib.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能：字符串工具
 */
public class StringUtil {

	/**
	 * 功能：字符串转整数
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
		}
		return defValue;
	}

	/**
	 * 功能：对象转整数
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static int toInt(Object obj) {
		if (obj == null)
			return 0;
		return toInt(obj.toString(), 0);
	}

	/**
	 * 功能：对象转整数
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static long toLong(String obj) {
		try {
			return Long.parseLong(obj);
		} catch (Exception e) {
		}
		return 0;
	}

	/**
	 * 功能：字符串转布尔值
	 * 
	 * @param b
	 * @return 转换异常返回 false
	 */
	public static boolean toBool(String b) {
		try {
			return Boolean.parseBoolean(b);
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 功能：判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		boolean isNull = false;
		if (str == null || str.length() == 0 || str.equals("null")) {
			isNull = true;
		}
		return isNull;
	}

	/**
	 * 功能：转换空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String convert(String str) {
		if (str == null || str.length() == 0 || str.equals("null")) {
			str = "";
		}
		return str;
	}

	/**
	 * 功能：字符串替换
	 * 
	 * @param strSource
	 * @param strFrom
	 * @param strTo
	 * @return
	 */
	public static String replace(String strSource, String strFrom, String strTo) {

		String strDest = "";
		int intFromLen = strFrom.length();
		int intPos;

		if (strFrom.equals("")) {
			return strSource;
		}
		while ((intPos = strSource.indexOf(strFrom)) != -1) {
			strDest = strDest + strSource.substring(0, intPos);
			strDest = strDest + strTo;
			strSource = strSource.substring(intPos + intFromLen);
		}
		strDest = strDest + strSource;
		return strDest;
	}

	/**
	 * 功能：校验邮箱格式
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * 功能：校验手机格式
	 * 
	 * @Title: isMobileNumber
	 * @Description: TODO(手机格式)
	 * @param @param mobiles
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 * @author lkun
	 */
	public static boolean isMobileNumber(String mobiles) {

		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 方法1， 将数组转换为list， 然后使用list的contains方法来判断： Arrays.asList().contains()
	 * 方法2，遍历数组判断：
	 * 
	 * @param array
	 * @param v
	 * @return
	 */
	public static <T> boolean contains(final T[] array, final T v) {
		for (final T e : array)
			if (e == v || v != null && v.equals(e))
				return true;

		return false;
	}

	/**
	 * 功能：判断字符串是否为合法的时间戳
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isValidDate(String str) {
		// yyyy-MM-dd_HH_mm_ss
		String regEx = "^\\d{4}-\\d{2}-\\d{2}_\\d{2}_\\d{2}_\\d{2}$";
		// 编译正则表达式
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(str);
		// 字符串是否与正则表达式相匹配
		return matcher.matches();
	}

    /**
     * Java文件操作 获取文件扩展名
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * Java文件操作 获取不带扩展名的文件名
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }
}
