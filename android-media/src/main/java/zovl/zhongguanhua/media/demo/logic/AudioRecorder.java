package zovl.zhongguanhua.media.demo.logic;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;

public class AudioRecorder {

    public static final String TAG  = AudioRecorder.class.getSimpleName();

    // ---------------------------------------------------------------------------------

    private MediaRecorder mediaRecorder;

    /**
     * 开始录音
     */
    public void startRecord(String path) {
        stopRecord();
        Log.d(TAG, "----------------------------------[startRecord]----------------------------------");
        Log.d(TAG, "path=" + path);
        if (mediaRecorder == null) {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mediaRecorder.setAudioChannels(2);
            // 128, 192, 256, 320
            try {
                mediaRecorder.setAudioEncodingBitRate(256 * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 8, 32, 44.1, 48, 96
            try {
                mediaRecorder.setAudioSamplingRate((int) (96f * 1000f));
            } catch (Exception e) {
                e.printStackTrace();
            }
            /*
            CamcorderProfile camcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
            mediaRecorder.setProfile(camcorderProfile);*/
            try {
                mediaRecorder.setOutputFile(path);
                Log.d(TAG, "startRecord: 1");
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            try {
                mediaRecorder.prepare();
                Log.d(TAG, "startRecord: 2");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                mediaRecorder.start();
                Log.d(TAG, "startRecord: 3");
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "startRecord: true");
        }
    }

    /**
     * 停止录音
     */
    public void stopRecord() {
        Log.d(TAG, "----------------------------------[stopRecord]----------------------------------");
        if (mediaRecorder != null) {
            try {
                mediaRecorder.stop();
                Log.d(TAG, "stopRecord: 1");
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            try {
                mediaRecorder.reset();
                Log.d(TAG, "stopRecord: 2");
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            try {
                mediaRecorder.release();
                Log.d(TAG, "stopRecord: 3");
            } catch (Exception e) {
                e.printStackTrace();
            }
            mediaRecorder = null;
            Log.d(TAG, "stopRecord: true");
        }
    }

    /**
     * 是否在录音
     */
    public boolean isRecording() {
        Log.d(TAG, "----------------------------------[isRecording]----------------------------------");
        if (mediaRecorder != null) {
            Log.d(TAG, "isRecording: true");
            return true;
        } else {
            Log.d(TAG, "isRecording: false");
            return false;
        }
    }
}
