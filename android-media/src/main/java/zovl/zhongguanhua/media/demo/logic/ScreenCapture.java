package zovl.zhongguanhua.media.demo.logic;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * 4.4截屏
 */
public class ScreenCapture {

	private static final String TAG = ScreenCapture.class.getSimpleName();

	public static void startCapture(String path) {
		Log.d(TAG, "startCapture: ----------------------------------------------------------");

		// ./system/bin/
		final String executable = "screencap -p" +
				" " + path;

		new Thread(new Runnable() {
			@Override
			public void run() {

				runCommand(executable);
			}
		}, "newthread-ScreenCapture").start();
	}

	private static void runCommand(String command) {
		Log.d(TAG, "command=" + command);
		Process process = null;
		DataOutputStream dos = null;
		try {
			process = Runtime.getRuntime().exec("su");
			dos = new DataOutputStream(process.getOutputStream());
			dos.writeBytes(command + "\n");
			dos.writeBytes("exit\n");
			dos.flush();
			process.waitFor();
			DataInputStream dis = new DataInputStream(process.getInputStream());
			DataInputStream des = new DataInputStream(process.getErrorStream());
			while (dis.available() > 0)
				Log.d(TAG, "dis: " + dis.readLine() + "\n");
			while (des.available() > 0)
				Log.d(TAG, "des: " + des.readLine() + "\n");
			Log.d(TAG, "exitValue: " + process.exitValue());
		} catch (Exception e) {
			e.printStackTrace();
			Log.d(TAG, "exception: " + e);
		} finally {
			if (dos != null)
				try {
					dos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (process != null)
				try {
					process.destroy();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}
}
