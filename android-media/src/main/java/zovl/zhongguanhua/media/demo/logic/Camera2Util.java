package zovl.zhongguanhua.media.demo.logic;

import android.annotation.TargetApi;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.util.Log;

import java.util.List;
/**
 * 工具：摄像头5.0
 */
@TargetApi(21)
public class Camera2Util {

    public final static String TAG = Camera2Util.class.getSimpleName();


    // ---------------------------------------------------------------------------------------------

    /**
     * 相机列表
     */
    public static String[] cameraIds(CameraManager manager) {
        Log.d(TAG, "----------------cameraIds------------------");
        if (manager != null) {
            try {
                return manager.getCameraIdList();
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 相机闪光灯
     */
    @TargetApi(23)
    public static boolean setTorchMode(CameraManager manager, String index, boolean enabled) {
        Log.d(TAG, "----------------setTorchMode------------------");
        if (manager != null) {
            try {
                manager.setTorchMode(index, enabled);
                return true;
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     *
     */
    public static boolean setTorchMode(CameraManager manager, String index) {
        Log.d(TAG, "----------------info------------------");
        if (manager != null) {

            CameraCharacteristics characteristics = null;
            try {
                characteristics = manager.getCameraCharacteristics(index);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
            if (characteristics != null) {

            }
        }
        return false;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * 打印相机信息
     */
    public static void printInfo(Camera.CameraInfo info) {
        if (info == null)
            return;
        Log.d(TAG, "----------------info------------------");
        Log.d(TAG, "printInfo: info=" + info);
        Log.d(TAG, "printInfo: canDisableShutterSound=" + info.canDisableShutterSound);
        Log.d(TAG, "printInfo: facing=" + info.facing);
        Log.d(TAG, "printInfo: orientation=" + info.orientation);
    }

    /**
     * 打印相机参数
     */
    public static void printParameters(Camera camera) {
        if (camera == null)
            return;
        if (camera.getParameters() == null)
            return;
        Camera.Parameters parameters = camera.getParameters();
        printParameters(parameters);
    }

    /**
     * 打印相机参数
     */
    public static void printParameters(Camera.Parameters parameters) {
        if (parameters == null)
            return;
        Log.d(TAG, "----------------parameters------------------");
        Log.d(TAG, "printParameters: parameters=" + parameters);

        // ---------------------------------------------------------
        Log.d(TAG, "------------------------------------------------");

        Log.d(TAG, "----------------supportedPreviewSizes------------------");
        List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
        if (previewSizes != null)
            for (Camera.Size previewSize : previewSizes) {
                Log.d(TAG, "printParameters: previewSizes--width=" + previewSize.width + "--height=" + previewSize.height);
            }
        Log.d(TAG, "----------------supportedPreviewFrameRates------------------");
        List<Integer> previewFrameRates = parameters.getSupportedPreviewFrameRates();
        if (previewFrameRates != null) {
            for (Integer previewFrameRate: previewFrameRates){
                Log.d(TAG, "printParameters: previewFrameRates=" + previewFrameRate);
            }
        }
        Log.d(TAG, "----------------supportedPreviewFpsRange------------------");
        List<int[]> list = parameters.getSupportedPreviewFpsRange();
        if (list != null) {
            for (int[] arr: list){
                Log.d(TAG, "----------------------------------");
                for (int a : arr) {
                    Log.d(TAG, "printParameters: previewFpsRange--a=" + a);
                }
            }
        }
        Log.d(TAG, "----------------supportedPreviewFormats------------------");
        List<Integer> previewFormats = parameters.getSupportedPreviewFormats();
        if (previewFormats != null) {
            for (int previewFormat :
                    previewFormats) {
                Log.d(TAG, "printParameters: previewFormat=" + previewFormat);
            }
        }

        Log.d(TAG, "----------------previewSize------------------");
        Camera.Size previewSize = parameters.getPreviewSize();
        if (previewSize != null) {
            Log.d(TAG, "printParameters: previewSize--width=" + previewSize.width + "--height=" + previewSize.height);
        }
        Log.d(TAG, "printParameters: previewFrameRate=" + parameters.getPreviewFrameRate());
        Log.d(TAG, "printParameters: previewFormat=" + parameters.getPreviewFormat());

        // ---------------------------------------------------------
        Log.d(TAG, "------------------------------------------------");

        Log.d(TAG, "printParameters: pictureFormat=" + parameters.getPictureFormat());
        Log.d(TAG, "printParameters: zoom=" + parameters.getZoom());
        Log.d(TAG, "printParameters: isZoomSupported=" + parameters.isZoomSupported());
        Log.d(TAG, "printParameters: maxZoom=" + parameters.getMaxZoom());
        Log.d(TAG, "printParameters: zoomRatios=" + parameters.getZoomRatios());
        Log.d(TAG, "printParameters: JpegQuality=" + parameters.getJpegQuality());
        Log.d(TAG, "printParameters: jpegThumbnailQuality=" + parameters.getJpegThumbnailQuality());
        Log.d(TAG, "printParameters: jpegThumbnailSize=" + parameters.getJpegThumbnailSize());
        Log.d(TAG, "printParameters: FlashMode=" + parameters.getFlashMode());
        Log.d(TAG, "printParameters: focusMode=" + parameters.getFocusMode());
        Log.d(TAG, "printParameters: sceneMode=" + parameters.getSceneMode());
        Log.d(TAG, "printParameters: horizontalViewAngle=" + parameters.getHorizontalViewAngle());

        Log.d(TAG, "----------------pictureSize------------------");
        Camera.Size pictureSize = parameters.getPictureSize();
        if (pictureSize != null) {
            Log.d(TAG, "printParameters: pictureSize--width=" + pictureSize.width + "--height=" + pictureSize.height);
        }

        Log.d(TAG, "----------------preferredPreviewSizeForVideo------------------");
        Camera.Size preferredPreviewSize = parameters.getPreferredPreviewSizeForVideo();
        if (preferredPreviewSize != null) {
            Log.d(TAG, "printParameters: preferredPreviewSize--width=" + preferredPreviewSize.width + "--height=" + preferredPreviewSize.height);
        }

        Log.d(TAG, "----------------supportedPictureSizes------------------");
        List<Camera.Size> pictureSizes = parameters.getSupportedPictureSizes();
        if (pictureSizes != null)
            for (Camera.Size picSize : pictureSizes) {
                Log.d(TAG, "printParameters: pictureSizes--width=" + picSize.width + "--height=" + picSize.height);
            }

        Log.d(TAG, "----------------supportedVideoSizes------------------");
        List<Camera.Size> videoSizes = parameters.getSupportedVideoSizes();
        if (videoSizes != null)
            for (Camera.Size videoSize : videoSizes) {
                Log.d(TAG, "printParameters: videoSizes--width=" + videoSize.width + "--height=" + videoSize.height);
            }

        // ---------------------------------------------------------
        Log.d(TAG, "------------------------------------------------");

        Log.d(TAG, "----------------supportedFlashModes------------------");
        List<String> flashModes = parameters.getSupportedFlashModes();
        if (flashModes != null) {
            for (String flashMode : flashModes) {
                Log.d(TAG, "printParameters: flashMode=" + flashMode);
            }
        }

        Log.d(TAG, "----------------supportedFocusModes------------------");
        List<String> focusModes = parameters.getSupportedFocusModes();
        if (focusModes != null) {
            for (String focusMode :
                    focusModes) {
                Log.d(TAG, "printParameters: focusMode=" + focusMode);
            }
        }

        Log.d(TAG, "----------------supportedSceneModes------------------");
        List<String> sceneModes = parameters.getSupportedSceneModes();
        if (sceneModes != null) {
            for (String sceneMode :
                    sceneModes) {
                Log.d(TAG, "printParameters: sceneMode=" + sceneMode);
            }
        }

        Log.d(TAG, "----------------supportedAntibanding------------------");
        List<String> antibandings = parameters.getSupportedAntibanding();
        if (antibandings != null) {
            for (String antibanding :
                    antibandings) {
                Log.d(TAG, "printParameters: antibanding=" + antibanding);
            }
        }

        Log.d(TAG, "----------------supportedWhiteBalance------------------");
        List<String> whiteBalances = parameters.getSupportedWhiteBalance();
        if (whiteBalances != null) {
            for (String whiteBalance :
                    whiteBalances) {
                Log.d(TAG, "printParameters: whiteBalance=" + whiteBalance);
            }
        }

        Log.d(TAG, "----------------supportedColorEffects------------------");
        List<String> colorEffects = parameters.getSupportedColorEffects();
        if (colorEffects != null) {
            for (String colorEffect :
                    colorEffects) {
                Log.d(TAG, "printParameters: colorEffect=" + colorEffect);
            }
        }
    }
}
