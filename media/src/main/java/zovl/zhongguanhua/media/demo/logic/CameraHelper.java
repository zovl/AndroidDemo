package zovl.zhongguanhua.media.demo.logic;

import android.hardware.Camera;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class CameraHelper {

    public final static String TAG = CameraHelper.class.getSimpleName();

    // ---------------------------------------------------------------------------------

    private Camera camera;
    private int index = -1;
    private Camera.CameraInfo info;
    private boolean isOpenCamera;
    private boolean isFrontCamera;
    private String flashMode;
    private List<String> flashModes;

    public CameraHelper() {
        super();

        flashModes = new ArrayList<>();
        flashModes.add(Camera.Parameters.FLASH_MODE_AUTO);
        flashModes.add(Camera.Parameters.FLASH_MODE_ON);
        flashModes.add(Camera.Parameters.FLASH_MODE_OFF);
        flashModes.add(Camera.Parameters.FLASH_MODE_RED_EYE);
        flashModes.add(Camera.Parameters.FLASH_MODE_TORCH);
    }

    /**
     * 打开相机
     */
    public void switchCamera() {
        Log.d(TAG, "openCamera: // -----------------------------------------------------");
        int size = CameraUtil.cameraSize();
        int next = (index + 1) % size;
        Log.d(TAG, "switchCamera: size=" + size);
        Log.d(TAG, "switchCamera: next=" + next);
        if (size >= 2) {
            destroyCamera();
            camera = CameraUtil.openCamera(next);
            if (camera != null) {
                index = next;
                isOpenCamera = true;
                info = CameraUtil.cameraInfo(index);
                isFrontCamera = CameraUtil.isFrontCamera(index);
                Log.d(TAG, "switchCamera: 2");
            } else {
                camera = CameraUtil.openCamera(index);
                if (camera != null) {
                    isOpenCamera = true;
                    info = CameraUtil.cameraInfo(index);
                    isFrontCamera = CameraUtil.isFrontCamera(index);
                    Log.d(TAG, "switchCamera: 3");
                } else {
                    isOpenCamera = false;
                    info = null;
                    isFrontCamera = false;
                }
            }
        }

        Log.d(TAG, "switchCamera: camera=" + camera);
        Log.d(TAG, "switchCamera: index=" + index);
        Log.d(TAG, "switchCamera: isOpenCamera=" + isOpenCamera);
        Log.d(TAG, "switchCamera: info=" + info);
        Log.d(TAG, "switchCamera: isFrontCamera=" + isFrontCamera);
    }

    /**
     * 销毁相机
     */
    private void destroyCamera() {
        Log.d(TAG, "destroyCamera: // -----------------------------------------------------");
        CameraUtil.destroyCamera(camera);
        camera = null;
        Log.d(TAG, "destroyCamera: true");
    }

    private boolean isFrontCamera() {
        return isFrontCamera;
    }

    private boolean isOpenCamera() {
        return isOpenCamera;
    }

    public Camera camera() {
        return camera;
    }

    public void switchFlashMode() {
        if (flashMode == null) {
            flashMode = flashModes.get(0);
        } else {
            int index = flashModes.indexOf(flashMode);
            flashMode = flashModes.get((index + 1) % flashModes.size());
        }
        CameraUtil.setFlashMode(camera, flashMode);
    }
}
