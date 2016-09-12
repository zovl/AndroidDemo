package zovl.zhongguanhua.media.demo.logic;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class AudioRecorder2 {

    public static final String TAG  = AudioRecorder2.class.getSimpleName();

    // ---------------------------------------------------------------------------------

    private AudioRecord audioRecord;
    private AtomicBoolean isRecording = new AtomicBoolean(false);

    private final int SAMPLE_RATE = 44100;
    private final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    private final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    private final int SIZE_PER_FRAME = 1024;
    private final int FRAMES = 25;

    /**
     * 开始录音
     */
    public void startRecord(String path) {
        stopRecord();
        Log.d(TAG, "----------------------------------[startRecord]----------------------------------");

        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(path)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (dos == null) {
            return;
        }

        int minBufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT);
        int bufferSize = SIZE_PER_FRAME * FRAMES;
        if (bufferSize < minBufferSize) {
            bufferSize = (minBufferSize / SIZE_PER_FRAME + 1) * SIZE_PER_FRAME * 2;
        }

        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT, bufferSize);
        if (audioRecord.getState() == AudioRecord.STATE_UNINITIALIZED) {
            return;
        }
        audioRecord.startRecording();
        isRecording.set(true);

        while (isRecording.get()) {
            byte[] buffer = new byte[SIZE_PER_FRAME];
            int result = audioRecord.read(buffer, 0, SIZE_PER_FRAME);
            if (result == AudioRecord.ERROR_BAD_VALUE ||
                    result == AudioRecord.ERROR_INVALID_OPERATION) {
            } else {
                try {
                    dos.write(buffer);
                } catch (IOException e) {
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

    /**
     * 停止录音
     */
    public void stopRecord() {
        Log.d(TAG, "----------------------------------[stopRecord]----------------------------------");
        if (isRecording.get()) {
            isRecording.set(false);
        }
    }

    /**
     * 是否在录音
     */
    public boolean isRecording() {
        Log.d(TAG, "----------------------------------[isRecording]----------------------------------");
        return isRecording.get();
    }
}
