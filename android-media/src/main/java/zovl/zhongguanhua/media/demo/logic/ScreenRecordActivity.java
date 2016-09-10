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
 * 活动：5.0录屏
 */
@TargetApi(21)
public class ScreenRecordActivity extends Activity {

	private static final String TAG = ScreenRecordActivity.class.getSimpleName();

	public static void startScreenRecordActivity(Context context, String path, Configuration configuration) {
		Intent intent = new Intent();
		intent.setClass(context, ScreenRecordActivity.class);
		// intent.setAction(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		intent.putExtra("path", path);
		intent.putExtra("configuration", configuration);
		try {
			context.startActivity(intent);
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void stopRecording() {
		if (mediaMuxer != null) {
			isPauseRecording.set(false);
			isStopRecording.set(true);
		}
	}

	public static void pauseRecording() {
		if (mediaMuxer != null) {
			isPauseRecording.set(true);
			ptsPause = System.nanoTime();
		}
	}

	public static void restartRecording() {
		if (mediaMuxer != null) {
			isPauseRecording.set(false);
			ptsRestart = System.nanoTime() - 1000000000L;
			pts = pts + (ptsRestart - ptsPause);
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
	private MediaCodec.BufferInfo videoBuffer, audioBuffer;
	protected MediaCodec videoCodec, audioCodec;
	private VirtualDisplay virtualDisplay;
	private Surface surface;
	private AudioThread audioThread;
	public ArrayBlockingQueue<byte[]> audioQueue = new ArrayBlockingQueue<>(50);

	private int width;
	private int height;
	private int bitrate = 2000000;
	private int fps = 30;
	private int iframe = 2;
	private int dpi;
	private boolean isAudio = true;

	private static AtomicBoolean isStopRecording = new AtomicBoolean(false);
	private static AtomicBoolean isPauseRecording = new AtomicBoolean(false);
	private boolean isMuxerStarted = false;
	private int muxerTrackNumber = 0;
	private long audioStartTime = 0;
	private long videoStartTime = 0;
	private static long pts = 0;           // 时间戳的缩减量
	private static long ptsPause = 0;      // 暂停时的时间戳
	private static long ptsRestart = 0;    // 重启时的时间戳

	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		Log.d(TAG, "onActivityResult: ----------------------------------------------------------");
		Log.i(TAG, "onActivityResult/requestCode=" + requestCode);
		Log.i(TAG, "onActivityResult/resultCode=" + resultCode);
		Log.i(TAG, "onActivityResult/data=" + data);

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

					videoBuffer = new MediaCodec.BufferInfo();
					audioBuffer = new MediaCodec.BufferInfo();

					// 音频编码
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

						audioThread = new AudioThread(audioQueue);
						audioThread.start();
					}

					// 视频编码
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

							TaskUtil.clearTaskAndAffinity(ScreenRecordActivity.this);
						}
					});

					videoStartTime = System.nanoTime();
					int newVideoIndex = 0;
					while (!isStopRecording.get()) {
						if (!isStopRecording.get()) {
							long frameTime = System.nanoTime();
							int videoIndex = videoCodec.dequeueOutputBuffer(videoBuffer, 1000L);
							//check Video index and add Video track
							if (videoIndex == MediaCodec.INFO_TRY_AGAIN_LATER) {
							} else if (videoIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
								if (!isMuxerStarted) {
									newVideoIndex = mediaMuxer.addTrack(videoCodec.getOutputFormat());
									muxerTrackNumber++;
									if (muxerTrackNumber == 2) {
										mediaMuxer.start();
										isMuxerStarted = true;
									}
								}
							} else if (videoIndex > 0 && isMuxerStarted) {
								ByteBuffer outputBuffer = videoCodec.getOutputBuffer(videoIndex);
								if (outputBuffer != null) {
									if ((videoBuffer.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
										videoBuffer.size = 0;
									}
									if (videoBuffer.size != 0) {
										outputBuffer.position(videoBuffer.offset);
										outputBuffer.limit(videoBuffer.offset + videoBuffer.size);
										videoBuffer.presentationTimeUs = (frameTime - videoStartTime - pts) / 1000;
										mediaMuxer.writeSampleData(newVideoIndex, outputBuffer, videoBuffer);
									}
									videoCodec.releaseOutputBuffer(videoIndex, false);
								}
							}
							
							if (isAudio) {
								int audioIndex = audioCodec.dequeueOutputBuffer(audioBuffer, 1000L);
								//check audio index
								if (audioIndex == MediaCodec.INFO_TRY_AGAIN_LATER) {
                                } else if (audioIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                                    if (!isMuxerStarted) {
                                        audioIndex = mediaMuxer.addTrack(audioCodec.getOutputFormat());
                                        muxerTrackNumber++;
                                        if (muxerTrackNumber == 2) {
											mediaMuxer.start();
                                            Log.d(TAG, "audio: start");
                                            isMuxerStarted = true;
                                        }
                                    }
                                } else if (audioIndex > 0 && isMuxerStarted) {
                                    ByteBuffer outputBuffer = audioCodec.getOutputBuffer(audioIndex);
                                    if (outputBuffer != null) {
                                        if ((audioBuffer.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
											audioBuffer.size = 0;
                                        }
                                        if (audioBuffer.size != 0) {
                                            outputBuffer.position(audioBuffer.offset);
                                            outputBuffer.limit(audioBuffer.offset + audioBuffer.size);
                                            audioBuffer.presentationTimeUs = (frameTime - videoStartTime - pts) / 1000;
											mediaMuxer.writeSampleData(audioIndex, outputBuffer, audioBuffer);
                                        }
                                        audioCodec.releaseOutputBuffer(audioIndex, false);
                                    }
                                }
								//put audio buffer to audioEncode input buffer
								byte[] audioData = audioQueue.peek();
								if (audioData != null) {
									audioQueue.remove();
                                    int inputBufferIdx = audioCodec.dequeueInputBuffer(-1);
                                    if (inputBufferIdx >= 0) {
                                        ByteBuffer inputBuffer = audioCodec.getInputBuffer(inputBufferIdx);
                                        inputBuffer.clear();
                                        inputBuffer.put(audioData);
                                        audioCodec.queueInputBuffer(inputBufferIdx, 0, audioData.length, (System.nanoTime() - audioStartTime) / 1000, 0);
                                    }
                                }
							}
						}
					}
					if (videoCodec != null) {
						videoCodec.stop();
						videoCodec.release();
					}
					if (audioCodec != null) {
						audioCodec.stop();
						audioCodec.release();
					}
					if (mediaMuxer != null) {
						try {
							mediaMuxer.stop();
							mediaMuxer.release();
						} catch (Exception e) {
							e.printStackTrace();
						}
						mediaMuxer = null;
					}
					if (virtualDisplay != null) {
						virtualDisplay.release();
					}
					if (projection != null) {
						projection.stop();
					}
					if (audioThread != null && audioThread.isAlive()) {
						audioThread.stopRecord();
					}
				}
			}, "newThread-ScreenRecordActivity").start();
		} else {
			// 录屏失败
			TaskUtil.clearTaskAndAffinity(this);
		}
	}

	public class AudioThread extends Thread {

		private final int SAMPLE_RATE = 44100;
		private final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
		private final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
		private final int SIZE_PER_FRAME = 1024;
		private final int FRAMES = 25;

		private int bufferSize;
		private AudioRecord audioRecord;
		private AtomicBoolean isRecording = new AtomicBoolean(false);
		private ArrayBlockingQueue<byte[]> queue;

		public AudioThread(ArrayBlockingQueue<byte[]> queue) {
			super("newThread-AudioThread");
			this.queue = queue;
		}

		@Override
		public void run() {
			Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);
			bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT);

			int buffer_size = SIZE_PER_FRAME * FRAMES;
			if (buffer_size < buffer_size) {
				buffer_size = (bufferSize / SIZE_PER_FRAME + 1) * SIZE_PER_FRAME * 2;
			}

			audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT, buffer_size);
			if (audioRecord.getState() == AudioRecord.STATE_UNINITIALIZED) {
				return;
			}
			audioRecord.startRecording();
			isRecording.set(true);

			while (isRecording.get()) {
				byte[] audioBuffer = new byte[SIZE_PER_FRAME];
				int result = audioRecord.read(audioBuffer, 0, SIZE_PER_FRAME);
				if (result == AudioRecord.ERROR_BAD_VALUE ||
						result == AudioRecord.ERROR_INVALID_OPERATION) {
				} else {
					try {
						queue.put(audioBuffer);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			if (audioRecord != null) {
				audioRecord.setRecordPositionUpdateListener(null);
				audioRecord.stop();
				audioRecord.release();
			}
		}

		public void stopRecord() {
			isRecording.set(false);
		}
	}
}
