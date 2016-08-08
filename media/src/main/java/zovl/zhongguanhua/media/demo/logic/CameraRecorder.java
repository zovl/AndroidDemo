package zovl.zhongguanhua.media.demo.logic;

import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.util.Log;
import android.view.SurfaceView;

import java.io.IOException;

public class CameraRecorder {

    public static final String TAG  = CameraRecorder.class.getSimpleName();

    // ---------------------------------------------------------------------------------

    private MediaRecorder mediaRecorder;
    private Camera camera;

    public CameraRecorder() {
        super();

        mediaRecorder = new MediaRecorder();
    }


    // ---------------------------------------------------------------------------------

    /**
     * 开始拍摄
     */
    public void startRecord(String path, Camera camera) {
        stopRecord();
        Log.d(TAG, "----------------------------------[startCameraRecord]----------------------------------");
        Log.d(TAG, "path=" + path);
        this.camera = camera;
        if (mediaRecorder == null)
            mediaRecorder = new MediaRecorder();
        else
            mediaRecorder.reset();

        // mediaRecorder.setPreviewDisplay(surfaceView.getHolder().getSurface());

        camera.unlock();
        mediaRecorder.setCamera(camera);

        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        CamcorderProfile camcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
        mediaRecorder.setProfile(camcorderProfile);

/*
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mediaRecorder.setOrientationHint(0);
        mediaRecorder.setVideoSize(176, 144);// 176,144
        mediaRecorder.setVideoFrameRate(24);
        mediaRecorder.setVideoEncodingBitRate(128);
        mediaRecorder.setAudioChannels(2);
        mediaRecorder.setAudioSamplingRate(48);*/

        mediaRecorder.setOrientationHint(0);// 设置输出视频朝向
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
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
        try {
            mediaRecorder.start();
            Log.d(TAG, "startRecord: 3");
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        // camera.lock();
        Log.d(TAG, "startRecord: true");
    }

    /**
     * 停止拍摄
     */
    public void stopRecord() {
        Log.d(TAG, "----------------------------------[stopCameraRecord]----------------------------------");
        if (mediaRecorder != null) {
            try {
                mediaRecorder.stop();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 销毁资源
     */
    public void destroy() {
        Log.d(TAG, "----------------------------------[stopCameraRecord]----------------------------------");
        if (mediaRecorder != null) {
            try {
                mediaRecorder.stop();
                Log.d(TAG, "destroy: 1");
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            try {
                mediaRecorder.reset();
                Log.d(TAG, "destroy: 2");
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            try {
                mediaRecorder.release();
                Log.d(TAG, "destroy: 3");
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            mediaRecorder = null;
            Log.d(TAG, "destroy: true");
        }
    }
}
