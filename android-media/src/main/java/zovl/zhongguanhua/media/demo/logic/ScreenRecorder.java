package zovl.zhongguanhua.media.demo.logic;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicBoolean;

import zovl.zhongguanhua.framework.lib.utils.StorageUtil;

/**
 * 4.4录屏
 */
public class ScreenRecorder {

	private static final String TAG = ScreenRecorder.class.getSimpleName();

	private String path;
	private Configuration configuration;
	private AtomicBoolean isRecording = new AtomicBoolean(false);

	private Process process;
	private InputStream is;
	private InputStream es;
	private OutputStream os;

	private Handler handler = new Handler(Looper.getMainLooper());

	public boolean isRecording() {
		return isRecording.get();
	}

	public ScreenRecorder(String path, Configuration configuration) {
		this.path = path;
		this.configuration = configuration;

		Log.d(TAG, "ScreenRecorder: path=" + path);
		Log.d(TAG, "ScreenRecorder: configuration=" + configuration);
	}

	/**
	 * 开始录制（4.4）
	 */
	public void startRecording() {
		Log.d(TAG, "startRecording: ----------------------------------------------------------");
		Log.d(TAG, "startRecording: ");
		String audio;
		final int bitrate;
		final int width, height;

		if (configuration.isLandscape()) {// 横屏
			width = configuration.getHeight();
			height = configuration.getWidth();
		} else {// 竖屏
			width = configuration.getWidth();
			height = configuration.getHeight();
		}

		bitrate = configuration.getBitrate();
		if (configuration.isAudio())
			audio = "--audio";
		else
			audio = "";

		// ./system/bin/
		final String executable = "screenrecord"  +
				" --size " + width + "x" + height +
				" --bit-rate " + bitrate +
				" " + path;

		new Thread(new Runnable() {
			@Override
			public void run() {

				try {
					process = Runtime.getRuntime().exec(new String[]{"su", "-c", executable});
					Log.d(TAG, "exec: executable=" + executable);
					isRecording.set(true);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (process != null) {
					is = process.getInputStream();
					es = process.getErrorStream();
					os = process.getOutputStream();
				}
				if (is != null) {
					new Thread(new Runnable() {
						@Override
						public void run() {

							readStream(is);
						}
					}, "newthread-ScreenRecorder-is").start();
				}
				if (es != null) {
					new Thread(new Runnable() {
						@Override
						public void run() {

							readStream(es);
						}
					}, "newthread-ScreenRecorder-es").start();
				}
				if (process != null) {
					try {
                        int extValue = process.waitFor();
                        Log.d(TAG, "waitFor: extValue=" + extValue);
                        // destroy();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
				}
			}
		}, "newthread-ScreenRecorder").start();
	}

	/**
	 * 停止录制（4.4）
	 */
	public void stopRecording() {
		Log.d(TAG, "stopRecording: ----------------------------------------------------------");

		new Thread(new Runnable() {
			@Override
			public void run() {

				// kill 5568
				// killall mysql
				// kill -STOP 2231
				// kill -CONT 2231
				// kill -KILL 2231
				// kill -9 -1
				try {
					Runtime.getRuntime().exec(new String[]{"su", "-c", "kill -STOP screenrecord"});
				} catch (IOException e) {
					e.printStackTrace();
				}

				// ctrl+c
				// \\cC
				// runCommand("ctrl+c");

				destroy();
			}
		}, "newthread-ScreenRecorder").start();
	}

	private void destroy() {
		Log.d(TAG, "destroy: ----------------------------------------------------------");
		if (os != null)
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		if (is != null)
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
		if (process != null) {
			try {
				process.destroy();
				Log.d(TAG, "destroy: ");
				isRecording.set(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void runCommand(String command) {
		Log.d(TAG, "runCommand: ----------------------------------------------------------");
		Log.d(TAG, "runCommand: " + command);
		String s = command + "\n";
		byte[] b = s.getBytes(Charset.defaultCharset());
		if (os != null && command != null) {
			try {
				os.write(b);
				os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
		}
	}

	public void readStream(InputStream is) {
		Log.d(TAG, "readStream: ----------------------------------------------------------");
		if (is != null)
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(is), 4096);
				String line;
				int i = 0;
				while ((line = br.readLine()) != null) {
					if (0 != i)
						Log.d(TAG, "readStream: " + line);
					i++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}
