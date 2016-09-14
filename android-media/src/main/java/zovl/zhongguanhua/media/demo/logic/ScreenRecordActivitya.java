package zovl.zhongguanhua.media.demo.logic;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.Process;
import android.util.Log;
import android.view.Surface;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 活动：5.0录屏（简化版）
 */
@TargetApi(21)
public class ScreenRecordActivitya extends Activity {

	private static final String TAG = ScreenRecordActivitya.class.getSimpleName();

	public static final boolean DEBUG = true;

	public static void startRecording(Context context, String path, Configuration configuration) {
		Intent intent = new Intent();
		intent.setClass(context, ScreenRecordActivitya.class);
		// intent.setAction(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		intent.putExtra("path", path);
		intent.putExtra("configuration", configuration);
		try {
			context.startActivity(intent);
			Log.d(TAG, "startRecording: true");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------

	private static final int REQUEST_CODE = 1;
	private PowerManager powerManager;
	private PowerManager.WakeLock wakeLock;
	private String path;
	private Configuration configuration;

	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			path = getIntent().getStringExtra("path");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			configuration = (Configuration) getIntent().getSerializableExtra("configuration");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (path == null) {
			TaskUtil.clearTaskAndAffinity(this);
		}

		powerManager = (PowerManager) getSystemService(POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, TAG);

		manager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);

		if (mediaMuxer != null) {
			stopRecording();
		}
		startRecording();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (wakeLock != null)
			wakeLock.acquire();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (wakeLock != null)
			wakeLock.release();
	}

	@Override
	protected void onDestroy() {
		if (handler != null)
			handler.removeCallbacksAndMessages(null);
		super.onDestroy();
	}

	@Override
	public void onConfigurationChanged(android.content.res.Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Log.d(TAG, "onConfigurationChanged: newConfig=" + newConfig);
	}

	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------

	private void startRecording() {
		Intent intent = manager.createScreenCaptureIntent();
		try {
			startActivityForResult(intent, REQUEST_CODE);
			Log.d(TAG, "startRecording: true");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void stopRecording() {
		restartRecording();
		if (mediaMuxer != null) {
			isStopRecording.set(true);
			Log.d(TAG, "stopRecording: true");
		}
	}

	public static void pauseRecording() {
		if (mediaMuxer != null) {
			if (!isPauseRecording.get()) {
				isPauseRecording.set(true);
				ptsPause = System.nanoTime();
				Log.d(TAG, "pauseRecording: true");
			}
		}
	}

	public static void restartRecording() {
		if (mediaMuxer != null) {
			if (isPauseRecording.get()) {
				isPauseRecording.set(false);
				ptsRestart = System.nanoTime() - 1000000000L;
				pts = pts + (ptsRestart - ptsPause);
				Log.d(TAG, "restartRecording: true");
			}
		}
	}

	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------

	private MediaProjectionManager manager;
	private MediaProjection projection;
	private Handler handler = new Handler(Looper.getMainLooper());
	private static MediaMuxer mediaMuxer;
	private MediaCodec.BufferInfo videoInfo = new MediaCodec.BufferInfo();
	private MediaCodec.BufferInfo audioInfo = new MediaCodec.BufferInfo();
	private MediaCodec videoCodec, audioCodec;
	private VirtualDisplay virtualDisplay;
	private Surface surface;
	private AudioThread audioThread;
	private ArrayBlockingQueue<byte[]> audioQueue = new ArrayBlockingQueue<>(50);

	private Object syncObj = new Object();

	private int width;
	private int height;
	private int bitrate = 2000000;
	private int fps = 30;
	private int iframe = 2;
	private int dpi;
	private boolean isAudio = true;

	private static AtomicBoolean isStopRecording = new AtomicBoolean(false);
	private static AtomicBoolean isPauseRecording = new AtomicBoolean(false);
	private AtomicBoolean isMuxerStarted = new AtomicBoolean(false);
	private AtomicBoolean isAudioStarted = new AtomicBoolean(false);
	private int MUXER_TRACK_NUMBER = 1;
	private int muxerTrackNumber = 0;
	private long mediaStartTime = 0;
	private static long pts = 0;           // 时间戳的缩减量
	private static long ptsPause = 0;      // 暂停时的时间戳
	private static long ptsRestart = 0;    // 重启时的时间戳

	private final int SAMPLE_RATE = 44100;
	private final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
	private final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
	private final int SIZE_PER_FRAME = 1024;
	private final int FRAMES = 25;

	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		Log.d(TAG, "onActivityResult: ----------------------------------------------------------");
		Log.d(TAG, "onActivityResult: requestCode=" + requestCode);
		Log.d(TAG, "onActivityResult: resultCode=" + resultCode);
		Log.d(TAG, "onActivityResult: data=" + data);

		// 录屏参数
		width = configuration.getWidth();
		height= configuration.getHeight();
		bitrate = configuration.getBitrate();
		fps = configuration.getFps();
		dpi = configuration.getDpi();
		isAudio = configuration.isAudio();
		if (configuration.isLandscape()) {// 横屏
			width = configuration.getHeight();
			height = configuration.getWidth();
		} else {// 竖屏
			width = configuration.getWidth();
			height = configuration.getHeight();
		}

		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

			moveTaskToBack(true);
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					
					isStopRecording.set(false);
					isPauseRecording.set(false);

					pts = 0;
					ptsPause = 0;
					ptsRestart = 0;
					
					projection = manager.getMediaProjection(resultCode, data);

					// 音频编码器
					if (isAudio) {
						MediaFormat audioFormat = MediaFormat.createAudioFormat("audio/mp4a-latm", 44100, 1);
						audioFormat.setInteger(MediaFormat.KEY_AAC_PROFILE, MediaCodecInfo.CodecProfileLevel.AACObjectLC);
						audioFormat.setInteger(MediaFormat.KEY_CHANNEL_MASK, AudioFormat.CHANNEL_IN_MONO);
						audioFormat.setInteger(MediaFormat.KEY_BIT_RATE, 64000);
						audioFormat.setInteger(MediaFormat.KEY_CHANNEL_COUNT, 1);
						try {
                            audioCodec = MediaCodec.createEncoderByType("audio/mp4a-latm");
                            audioCodec.configure(audioFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
                            audioCodec.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

						if (audioCodec != null) {
							audioThread = new AudioThread(syncObj);
							audioThread.start();
							synchronized (syncObj) {
                                try {
                                    syncObj.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
						}

						if (isAudioStarted.get()) {
							MUXER_TRACK_NUMBER = 2;
						}
					}

					// 视频编码器
					MediaFormat format = MediaFormat.createVideoFormat("video/avc", width, height);
					format.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
					format.setInteger(MediaFormat.KEY_BIT_RATE, bitrate);
					format.setInteger(MediaFormat.KEY_FRAME_RATE, fps);
					format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, iframe);
					try {
						videoCodec = MediaCodec.createEncoderByType("video/avc");
						videoCodec.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
						surface = videoCodec.createInputSurface();
						videoCodec.start();
					} catch (IOException e) {
						e.printStackTrace();
					}

					try {
						mediaMuxer = new MediaMuxer(path, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
					} catch (IOException e) {
						e.printStackTrace();
					}

					virtualDisplay = projection.createVirtualDisplay("display",
							width,
							height,
							1,
							DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC,
							surface,
							null,
							null);

					handler.post(new Runnable() {
						@Override
						public void run() {

							TaskUtil.clearTaskAndAffinity(ScreenRecordActivitya.this);
						}
					});

					if (DEBUG) Log.d(TAG, "codec: start");

					mediaStartTime = System.nanoTime();
					int newVideoIndex = 0;
					int newAudioIndex = 0;
					while (!isStopRecording.get()) {
						if (!isPauseRecording.get()) {
							long frameTime = System.nanoTime();
							// 取出录音器的数据，写入音频编码器
							if (isAudio && isAudioStarted.get() && isMuxerStarted.get()) {
								byte[] audioBytes = audioQueue.peek();
								if (audioBytes != null) {
                                    audioQueue.remove();
                                    int inputIndex = audioCodec.dequeueInputBuffer(-1);
                                    if (inputIndex >= 0) {
                                        ByteBuffer inputBuffer = audioCodec.getInputBuffer(inputIndex);
                                        inputBuffer.clear();
                                        inputBuffer.put(audioBytes);
										long nanoTime = System.nanoTime();
                                        audioCodec.queueInputBuffer(inputIndex, 0, audioBytes.length, nanoTime / 1000, 0);
                                    }
                                }
							}
							// 视频编码
							int videoIndex = videoCodec.dequeueOutputBuffer(videoInfo, 1000L);
							if (DEBUG) Log.d(TAG, "video: videoIndex=" + videoIndex);
							if (videoIndex == MediaCodec.INFO_TRY_AGAIN_LATER) {// -1
							} else if (videoIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {// -2
								if (!isMuxerStarted.get()) {
									newVideoIndex = mediaMuxer.addTrack(videoCodec.getOutputFormat());
									if (DEBUG) Log.d(TAG, "video: muxerTrackNumber=" + muxerTrackNumber);
									muxerTrackNumber++;
									if (muxerTrackNumber == MUXER_TRACK_NUMBER) {
										mediaMuxer.start();
										isMuxerStarted.set(true);
										if (DEBUG) Log.d(TAG, "muxer: start");
									}
								}
							} else if (videoIndex > 0 && isMuxerStarted.get()) {// 写入混合器
								ByteBuffer outputBuffer = videoCodec.getOutputBuffer(videoIndex);
								if (outputBuffer != null) {
									if ((videoInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
										videoInfo.size = 0;
									}
									if (videoInfo.size != 0) {
										outputBuffer.position(videoInfo.offset);
										outputBuffer.limit(videoInfo.offset + videoInfo.size);
										videoInfo.presentationTimeUs = (frameTime - mediaStartTime - pts) / 1000;
										mediaMuxer.writeSampleData(newVideoIndex, outputBuffer, videoInfo);
									}
									videoCodec.releaseOutputBuffer(videoIndex, false);
								}
							}
							// 音频编码
							if (isAudio && isAudioStarted.get()) {
								int audioIndex = audioCodec.dequeueOutputBuffer(audioInfo, 1000L);
								if (DEBUG) Log.d(TAG, "audio: audioIndex=" + audioIndex);
								if (audioIndex == MediaCodec.INFO_TRY_AGAIN_LATER) {// -1
                                } else if (audioIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {// -2
                                    if (!isMuxerStarted.get()) {
										newAudioIndex = mediaMuxer.addTrack(audioCodec.getOutputFormat());
										if (DEBUG) Log.d(TAG, "audio: muxerTrackNumber=" + muxerTrackNumber);
										muxerTrackNumber++;
                                        if (muxerTrackNumber == MUXER_TRACK_NUMBER) {
											mediaMuxer.start();
                                            isMuxerStarted.set(true);
											if (DEBUG) Log.d(TAG, "muxer: start");
                                        }
                                    }
                                } else if (audioIndex > 0 && isMuxerStarted.get()) {// 写入混合器
                                    ByteBuffer outputBuffer = audioCodec.getOutputBuffer(audioIndex);
                                    if (outputBuffer != null) {
                                        if ((audioInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
											audioInfo.size = 0;
                                        }
                                        if (audioInfo.size != 0) {
                                            outputBuffer.position(audioInfo.offset);
                                            outputBuffer.limit(audioInfo.offset + audioInfo.size);
                                            audioInfo.presentationTimeUs = (frameTime - mediaStartTime - pts) / 1000;
											mediaMuxer.writeSampleData(newAudioIndex, outputBuffer, audioInfo);
                                        }
                                        audioCodec.releaseOutputBuffer(audioIndex, false);
                                    }
                                }
							}
						}
					}
					if (DEBUG) Log.d(TAG, "codec: end");
					if (mediaMuxer != null) {
						mediaMuxer.stop();
						mediaMuxer.release();
						mediaMuxer = null;
						if (DEBUG) Log.d(TAG, "muxer: stop");
					}
					if (videoCodec != null) {
						videoCodec.stop();
						videoCodec.release();
						if (DEBUG) Log.d(TAG, "video: stop");
					}
					if (audioCodec != null) {
						audioCodec.stop();
						audioCodec.release();
						if (DEBUG) Log.d(TAG, "audio: stop");
					}
					if (projection != null) {
						projection.stop();
						if (DEBUG) Log.d(TAG, "projection: stop");
					}
					if (virtualDisplay != null) {
						virtualDisplay.release();
						if (DEBUG) Log.d(TAG, "virtualDisplay: stop");
					}
					isAudioStarted.set(false);
					if (DEBUG) Log.d(TAG, "audioThread: stop");
				}
			}, "newThread-ScreenRecordActivitya").start();
		} else {
			// 录屏失败
			TaskUtil.clearTaskAndAffinity(this);
		}
	}

	public class AudioThread extends Thread {

		private AudioRecord audioRecord;

		private Object syncObj;

		public AudioThread(Object syncObj) {
			super("newThread-AudioThread");
			this.syncObj = syncObj;
		}

		@Override
		public void run() {
			super.run();
			Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);

			int minBufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT);
			int bufferSize = SIZE_PER_FRAME * FRAMES;
			if (bufferSize < minBufferSize) {
				bufferSize = (minBufferSize / SIZE_PER_FRAME + 1) * SIZE_PER_FRAME * 2;
			}

			audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT, bufferSize);
			if (audioRecord.getState() == AudioRecord.STATE_UNINITIALIZED) {
				synchronized (syncObj) {
					if (syncObj != null) {
						syncObj.notifyAll();
					}
				}
				return;
			}
			audioRecord.startRecording();
			isAudioStarted.set(true);

			synchronized (syncObj) {
				if (syncObj != null) {
					syncObj.notifyAll();
				}
			}

			if (DEBUG) Log.d(TAG, "audioRecord: start");

			while (isAudioStarted.get()) {
				byte[] audioBuffer = new byte[SIZE_PER_FRAME];
				int result = audioRecord.read(audioBuffer, 0, SIZE_PER_FRAME);
				if (result == AudioRecord.ERROR_BAD_VALUE ||
						result == AudioRecord.ERROR_INVALID_OPERATION) {
				} else {
					try {
						audioQueue.put(audioBuffer);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			if (DEBUG) Log.d(TAG, "audioRecord: end");

			if (audioRecord != null) {
				audioRecord.setRecordPositionUpdateListener(null);
				audioRecord.stop();
				audioRecord.release();

				if (DEBUG) Log.d(TAG, "audioRecord: stop");
			}
		}
	}
}
