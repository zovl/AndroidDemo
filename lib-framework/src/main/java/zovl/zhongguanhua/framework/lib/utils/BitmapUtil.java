package zovl.zhongguanhua.framework.lib.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import zovl.zhongguanhua.framework.lib.framework.AppManager;

/**
 * 功能：图片工具
 */
public class BitmapUtil {

	public static final String TAG = BitmapUtil.class.getSimpleName();

	/**
	 * 从Drawable中取得
	 */
	public static Bitmap fromDrawable(Drawable drawable) {
		if (drawable == null)
			return null;
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();
		Bitmap.Config config =
				drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
		Bitmap bitmap = null;
		try {
			bitmap = Bitmap.createBitmap(w, h, config);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//注意，下面三行代码要用到，否在在View或者SurfaceView里的Canvas.drawBitmap会看不到图
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		drawable.draw(canvas);
		Log.d(TAG, "fromDrawable: true");
		return bitmap;
	}

	/**
	 * 从Drawable中取得
	 */
	public static Bitmap fromDrawable_(Drawable drawable) {
		if (drawable == null)
			return null;
		BitmapDrawable bd = (BitmapDrawable) drawable;
		Bitmap bitmap = bd.getBitmap();
		Log.d(TAG, "fromDrawable_: true");
		return bitmap;
	}

	/**
	 * 到Drawable
	 */
	public static Drawable toDrawable(Bitmap bitmap) {
		if (bitmap == null)
			return null;
		Context context = AppManager.getInstance().getContext();
		Resources resources = context.getResources();
		BitmapDrawable drawable = new BitmapDrawable(resources, bitmap);
		Log.d(TAG, "toDrawable: true");
		return drawable;
	}

	//-----------------------------------------------------------------

	/**
	 * 保存Bitmap到本地（不压缩）
	 */
	public static boolean saveBitmap(byte[] bytes, String path) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(path);
			os.write(bytes);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * 保存Bitmap到本地（不压缩）
	 */
	public static boolean saveBitmap(Bitmap bitmap, String path) {
		if (bitmap == null || path == null)
			return false;
		Log.d(TAG, "saveBitmap: // ------------------------------------------");
		Log.d(TAG, "saveBitmap: path=" + path);
		Log.d(TAG, "saveBitmap: 0");
		File file = null;
		try {
			file = new File(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (file != null && file.exists())
			try {
				file.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Log.d(TAG, "saveBitmap: 1");
		if (fos == null)
			return false;
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
		try {
			fos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.d(TAG, "saveBitmap: true");
		return true;
	}

	/**
	 * 保存Bitmap到本地（不压缩）
	 */
	private static boolean saveBitmap_(Context context, Bitmap bitmap, String path) {
		if (bitmap == null || path == null)
			return false;
		Log.d(TAG, "saveBitmap_: // ------------------------------------------");
		Log.d(TAG, "saveBitmap_: path=" + path);
		Log.d(TAG, "saveBitmap_: 0");
		File file = null;
		try {
			file = new File(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (file != null && file.exists())
			try {
				file.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Log.d(TAG, "saveBitmap_: 1");
		if (fos == null)
			return false;
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (bos == null)
			return false;
		Log.d(TAG, "saveBitmap_: 2");
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
		try {
			bos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.d(TAG, "saveBitmap_: true");
		return true;
	}

	//-----------------------------------------------------------------

	/**
	 * 读取Res资源图片
	 */
	public static Bitmap readResBitmap(int resId) {
		Context context = AppManager.getInstance().getContext();
		return readResBitmap(context, resId);
	}

	/**
	 * 读取本地的图片（原图）
	 */
	public static Bitmap readLocalBitmap(String filePath) {
		return readLocalBitmapInSampleSize( filePath, 1);
	}

	/**
	 * 读取本地的图片（1/2）
	 */
	public static Bitmap readLocalBitmapHalf(String filePath) {
		return readLocalBitmapInSampleSize( filePath, 2);
	}

	/**
	 * 读取本地的图片（1/4）
	 */
	public static Bitmap readLocalBitmapQuarter(String filePath) {
		return readLocalBitmapInSampleSize(filePath, 4);
	}

	//-----------------------------------------------------------------

	/**
	 * 读取Res资源图片
	 */
	private static Bitmap readResBitmap(Context context, int resId) {
		if (context == null)
			return null;
		Log.d(TAG, "saveBitmap: // ------------------------------------------");
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inPurgeable = true;
		options.inInputShareable = true;
		InputStream is = context.getResources().openRawResource(resId);
		Log.d(TAG, "readResBitmap: true");
		return BitmapFactory.decodeStream(is, null, options);
	}

	/**
	 * 读取本地图片
	 *
	 * @param inSampleSize 1/inSampleSize 尺寸缩放 [1, ++)
	 *
	 */
	private static Bitmap readLocalBitmapInSampleSize(String filePath, int inSampleSize) {
		if (filePath == null)
			return null;
		Log.d(TAG, "saveBitmap: // ------------------------------------------");
		File file = null;
		try {
			file = new File(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (file == null || file.exists() == false)
			return null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = false;
		options.inSampleSize = inSampleSize; // 宽高设为原来的1/inSampleSize
		InputStream is = null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (is == null)
			return null;
		Log.d(TAG, "readLocalBitmapInSampleSize: true");
		return BitmapFactory.decodeStream(is, null, options);
	}

	/*
	private Bitmap decodeFile(File f) {
		try {
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);
			final int REQUIRED_SIZE = 70;
			int width = o.outWidth, height = o.outHeight;
			int scale = 1;
			while (true) {
				if (width / 2 < REQUIRED_SIZE
						|| height / 2 < REQUIRED_SIZE)
					break;
				width /= 2;
				height /= 2;
				scale *= 2;
			}
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, options);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}*/

	//-----------------------------------------------------------------

	/**
	 * 回收图片占用的内存
	 */
	public static void recycleBitmap(ImageView imageView) {
		Log.d(TAG, "recycleBitmap: // ------------------------------------------");
		if (imageView != null && imageView.getDrawable() != null) {
			Bitmap oldBitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
			imageView.setImageDrawable(null);
			if (oldBitmap != null && !oldBitmap.isRecycled()) {
				oldBitmap.recycle();
				System.gc();
				Log.d(TAG, "recycleBitmap: true");
			}
		}
	}

	/**
	 * 回收图片占用的内存
	 */
	public static void recycleBitmap(Bitmap bitmap) {
		Log.d(TAG, "recycleBitmap: // ------------------------------------------");
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
			System.gc();
			Log.d(TAG, "recycleBitmap: true");
		}
	}
}
