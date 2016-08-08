package zovl.zhongguanhua.media.demo.logic;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.ImageFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CameraUtil {

    public final static String TAG = CameraUtil.class.getSimpleName();

    // ---------------------------------------------------------------------------------

    /**
     * 是否能够使用相机
     */
    private boolean hardwareCamera(Context context) {
        return hardwareCamera(context, PackageManager.FEATURE_CAMERA);
    }

    private boolean hardwareAutofocus(Context context) {
        return hardwareCamera(context, PackageManager.FEATURE_CAMERA_AUTOFOCUS);
    }

    private boolean hardwareCamera(Context context, String featuremName) {
        PackageManager packageManager = context.getPackageManager();
        try {
            if (packageManager.hasSystemFeature(featuremName)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ---------------------------------------------------------------------------------

    /**
     * 打开相机
     *
     * @param isFrontCamera 是否打开前置相机
     */
    public static Camera openCamera(boolean isFrontCamera) {
        Log.i(TAG, "isFrontCamera=" + isFrontCamera);
        if (isFrontCamera) {// 打开前置摄像头
            Camera.CameraInfo info = new Camera.CameraInfo();
            for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
                Camera.getCameraInfo(i, info);
                if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    try {
                        return Camera.open(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {// 打开默认摄像头
            try {
                return Camera.open();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 打开相机
     *
     * @param i 相机标示
     */
    public static Camera openCamera(int i) {
        Log.i(TAG, "i=" + i);
        try {
            return Camera.open(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 销毁相机
     */
    public static void destroyCamera(Camera camera) {
        if (camera != null) {
            try {
                camera.stopPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                camera.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 相机数量
     */
    public static int cameraSize() {
        return Camera.getNumberOfCameras();
    }

    /**
     * 获得相机信息
     */
    public static Camera.CameraInfo cameraInfo(int i) {
        try {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            return info;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得相机信息
     */
    public static boolean isFrontCamera(int i) {
        Camera.CameraInfo info = cameraInfo(i);
        if (info != null) {
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                return true;
            } else if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                return false;
            }
        }
        return false;
    }

    /**
     * 获得相机信息
     */
    public static void reconnectCamera(Camera camera, Camera.Parameters parameters) {
        if (camera != null) {
            try {
                camera.reconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                camera.stopPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                camera.setParameters(parameters);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                camera.startPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // ---------------------------------------------------------------------------------

    /**
     * 相机预览
     */
    public static boolean setPreview(Camera camera, SurfaceView surfaceView) {
        if (surfaceView != null) {
            return setPreview(camera, surfaceView.getHolder());
        }
        return false;
    }

    /**
     * 相机预览
     */
    public static boolean setPreview(Camera camera, TextureView textureView) {
        if (textureView != null) {
            return setPreview(camera, textureView.getSurfaceTexture());
        }
        return false;
    }

    /**
     * 相机预览
     */
    public static boolean setPreview(Camera camera, SurfaceHolder surfaceHolder) {
        if (camera != null &&
                surfaceHolder != null) {
            try {
                camera.setPreviewDisplay(surfaceHolder);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 相机预览
     */
    public static boolean setPreview(Camera camera, SurfaceTexture surfaceTexture) {
        if (camera != null &&
                surfaceTexture != null) {
            try {
                camera.setPreviewTexture(surfaceTexture);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 相机预览
     */
    public static boolean restartPreview(Camera camera) {
        if (camera != null) {
            try {
                camera.stopPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                camera.startPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                camera.autoFocus(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    /**
     * 相机开始预览
     */
    public static boolean startPreview(Camera camera) {
        if (camera != null) {
            try {
                camera.startPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                camera.autoFocus(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    /**
     * 相机停止预览
     */
    public static boolean stopPreview(Camera camera) {
        if (camera != null) {
            try {
                camera.stopPreview();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * 设置闪光灯
     */
    public static boolean setFlashMode(Camera camera, String mode) {
        if (camera != null && mode != null) {
            Camera.Parameters parameters = camera.getParameters();
            if (parameters != null) {
                try {
                    parameters.setFlashMode(mode);
                    camera.setParameters(parameters);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 设置相机参数（预览旋转角度）
     */
    public static boolean setOrientation(Camera camera, int degrees) {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            if (parameters != null) {
                // 图片转角度
                parameters.setRotation(degrees);
                try {
                    camera.setParameters(parameters);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (Build.VERSION.SDK_INT >= 14) {
                // 设置视频预览朝向
                camera.setDisplayOrientation(degrees);
                return true;
            }
        }
        return false;
    }

    /**
     * 设置相机参数（预览旋转角度）
     */
    public static boolean setOrientation(Camera camera, Context context) {
        if (camera != null && context != null) {
            if (context.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                camera.setDisplayOrientation(90);
                return true;
            } else {
                camera.setDisplayOrientation(0);
                return true;
            }
        }
        return false;
    }

    /**
     * 设置相机参数（相机缩放）
     */
    public static boolean setZoom(Camera camera, int zoom) {
        Log.d(TAG, "setZoom: zoom=" + zoom);
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            if (parameters != null) {
                if (parameters.isZoomSupported()) {
                    int maxZoom = parameters.getMaxZoom();
                    if (zoom < 0) {
                        parameters.setZoom(0);
                    } else if (zoom <= maxZoom) {
                        // 相机缩放
                        parameters.setZoom(zoom);
                    } else {
                        parameters.setZoom(maxZoom);
                    }
                    try {
                        camera.setParameters(parameters);
                        Log.d(TAG, "setZoom: true");
                        Log.d(TAG, "setZoom: zoom=" + camera.getParameters().getZoom());
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取相机参数（相机最大缩放）
     */
    public static int maxZoom(Camera camera) {
        try {
            return camera.getParameters().getMaxZoom();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取相机参数（相机最大缩放）
     */
    public static int zoom(Camera camera) {
        try {
            return camera.getParameters().getZoom();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 设置相机参数（相机缩放/放大）
     */
    public static boolean addZoom(Camera camera) {
        try {
            setZoom(camera, camera.getParameters().getZoom() + 1);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 设置相机参数（相机缩放/缩小）
     */
    public static boolean minusZoom(Camera camera) {
        try {
            setZoom(camera, camera.getParameters().getZoom() - 1);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 设置相机参数（拍照参数）
     */
    public static boolean setVideoParameters(Camera camera,
                                             boolean toggle) {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            if (parameters != null) {
                parameters.setVideoStabilization(toggle);
                try {
                    camera.setParameters(parameters);
                    Log.d(TAG, "setVideoParameters: true");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 设置相机参数（拍照参数）
     */
    public static boolean setPictureParameters(Camera camera,
                                               int width, int height,
                                               int format,
                                               int quality,
                                               int thumbnailQuality) {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            if (parameters != null) {
                parameters.setPictureSize(width, height);
                parameters.setPictureFormat(format);
                parameters.setJpegQuality(quality);
                parameters.setJpegThumbnailQuality(thumbnailQuality);
                try {
                    camera.setParameters(parameters);
                    Log.d(TAG, "setPictureParameters: true");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 设置相机参数（拍照参数/小于100W像素，JPEG）
     */
    public static boolean setPictureParameters2(Camera camera) {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            if (parameters != null) {
                // 设置图片尺寸
                Camera.Size pictureSize = parameters.getPictureSize();
                List<Camera.Size> pictureSizes = parameters.getSupportedPictureSizes();
                if (pictureSizes != null) {
                    for (Camera.Size size : pictureSizes) {
                        if (size.width >= pictureSize.width &&
                                size.height >= pictureSize.height &&
                                size.width * size.height < 100 * 10000) {
                            pictureSize = size;
                        }
                    }
                    Log.d(TAG, "setPictureParameters2: pictureSize--width=" + pictureSize.width + "--height=" + pictureSize.height);
                    parameters.setPictureSize(pictureSize.width, pictureSize.height);
                }
                // 设置图片格式
                int pictureFormat = parameters.getPictureFormat();
                List<Integer> pictureFormats = parameters.getSupportedPictureFormats();
                if (pictureSizes != null) {
                    for (Integer format : pictureFormats) {
                        if (format == ImageFormat.JPEG) {
                            pictureFormat = format;
                            break;
                        }
                    }
                    parameters.setPictureFormat(pictureFormat);
                }
                parameters.setJpegQuality(100);
                parameters.setJpegThumbnailQuality(100);
                try {
                    camera.setParameters(parameters);
                    Log.d(TAG, "setPictureParameters2: true");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 设置相机参数（拍照参数/最大）
     */
    public static boolean setPictureParametersLargest(Camera camera) {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            if (parameters != null) {
                // 设置图片尺寸
                Camera.Size pictureSize = parameters.getPictureSize();
                List<Camera.Size> pictureSizes = parameters.getSupportedPictureSizes();
                if (pictureSizes != null) {
                    for (Camera.Size size : pictureSizes) {
                        if (size.width >= pictureSize.width &&
                                size.height >= pictureSize.height) {
                            pictureSize = size;
                        }
                    }
                    parameters.setPictureSize(pictureSize.width, pictureSize.height);
                }
                // 设置图片格式
                int pictureFormat = parameters.getPictureFormat();
                List<Integer> pictureFormats = parameters.getSupportedPictureFormats();
                if (pictureSizes != null) {
                    for (Integer format : pictureFormats) {
                        if (format == ImageFormat.JPEG) {
                            pictureFormat = format;
                            break;
                        }
                    }
                    Log.d(TAG, "setPictureParametersLargest: pictureSize--width=" + pictureSize.width + "--height=" + pictureSize.height);
                    parameters.setPictureFormat(pictureFormat);
                }
                parameters.setJpegQuality(100);
                parameters.setJpegThumbnailQuality(100);
                try {
                    camera.setParameters(parameters);
                    Log.d(TAG, "setPictureParametersLargest: true");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 设置相机参数（预览参数）
     */
    public static boolean setPreviewParameters(Camera camera,
                                               int width, int height,
                                               int format,
                                               int fps,
                                               int min, int max) {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            if (parameters != null) {
                // ImageFormat.JPEG
                parameters.setPreviewFormat(format);
                parameters.setPreviewSize(width, height);
                parameters.setPreviewFrameRate(fps);
                parameters.setPreviewFpsRange(min, max);
                try {
                    camera.setParameters(parameters);
                    Log.d(TAG, "setPreviewParameters: true");
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 设置相机参数（预览参数/最小）
     */
    public static void setPreviewParametersSmallest(Camera camera) {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            // 设置最小的预览尺寸
            if (parameters != null) {
                List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
                // Comparators.sortCameraSize(sizes);
                Camera.Size previewSize = parameters.getPreviewSize();
                if (previewSizes != null) {
                    for (Camera.Size size : previewSizes) {
                        if (size.width <= previewSize.width
                                && size.height <= previewSize.height) {
                            previewSize = size;
                        }
                    }
                    Log.d(TAG, "printParameters: previewSize--width=" + previewSize.width + "--height=" + previewSize.height);
                    parameters.setPreviewSize(previewSize.width, previewSize.height);
                }

                try {
                    camera.setParameters(parameters);
                    Log.d(TAG, "setPreviewParametersSmallest: true");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 设置最小的预览帧率
            parameters = camera.getParameters();
            if (parameters != null) {
                List<Integer> frameRates = parameters.getSupportedPreviewFrameRates();
                int frameRate = parameters.getPreviewFrameRate();
                if (frameRates != null) {
                    for (Integer rate : frameRates) {
                        if (frameRate > rate) {
                            frameRate = rate;
                        }
                    }
                    Log.d(TAG, "setPreviewParametersSmallest: frameRate=" + frameRate);
                    parameters.setPreviewFrameRate(frameRate);
                }

                try {
                    camera.setParameters(parameters);
                    Log.d(TAG, "setPreviewParametersSmallest: true");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 设置相机参数（预览参数/最大）
     */
    public static void setPreviewParametersLargest(Camera camera) {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            // 设置最小的预览尺寸
            if (parameters != null) {
                List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
                // Comparators.sortCameraSize(sizes);
                Camera.Size previewSize = parameters.getPreviewSize();
                if (previewSizes != null) {
                    for (Camera.Size size : previewSizes) {
                        if (size.width >= previewSize.width
                                && size.height >= previewSize.height) {
                            previewSize = size;
                        }
                    }
                    Log.d(TAG, "printParameters: previewSize--width=" + previewSize.width + "--height=" + previewSize.height);
                    parameters.setPreviewSize(previewSize.width, previewSize.height);
                }

                try {
                    camera.setParameters(parameters);
                    Log.d(TAG, "setPreviewParametersSmallest: true");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 设置最小的预览帧率
            parameters = camera.getParameters();
            if (parameters != null) {
                List<Integer> frameRates = parameters.getSupportedPreviewFrameRates();
                int frameRate = parameters.getPreviewFrameRate();
                if (frameRates != null) {
                    for (Integer rate : frameRates) {
                        if (frameRate < rate) {
                            frameRate = rate;
                        }
                    }
                    Log.d(TAG, "setPreviewParametersSmallest: frameRate=" + frameRate);
                    parameters.setPreviewFrameRate(frameRate);
                }

                try {
                    camera.setParameters(parameters);
                    Log.d(TAG, "setPreviewParametersSmallest: true");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * 自动聚焦
     */
    public static void autoFocusCamera(Camera camera) {
        if (camera == null)
            return;
        try {
            camera.autoFocus(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置聚焦
     */
    public static void focusCamera(Camera camera, Point point) {
        if (camera == null)
            return;
        Camera.Parameters parameters = camera.getParameters();

        // 不支持设置自定义聚焦，则使用自动聚焦
        if (parameters.getMaxNumFocusAreas() <= 0) {
            try {
                camera.autoFocus(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        List<Camera.Area> areas = new ArrayList<>();
        int left = point.x - 300;
        int top = point.y - 300;
        int right = point.x + 300;
        int bottom = point.y + 300;
        left = left < -1000 ? -1000 : left;
        top = top < -1000 ? -1000 : top;
        right = right > 1000 ? 1000 : right;
        bottom = bottom > 1000 ? 1000 : bottom;
        areas.add(new Camera.Area(new Rect(left, top, right, bottom), 100));
        parameters.setFocusAreas(areas);
        try {
            // 有些机型会出异常
            camera.setParameters(parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            camera.autoFocus(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * 预览回调
     */
    public static void setPreviewCallback(Camera camera, Camera.PreviewCallback callback) {
        if (camera != null) {
            try {
                camera.setPreviewCallback(callback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 预览回调
     */
    public static void setPreviewCallbackWithBuffer(Camera camera, Camera.PreviewCallback callback) {
        if (camera != null) {
            try {
                camera.setPreviewCallbackWithBuffer(callback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 拍照
     */
    public static void takePicture(Camera camera,
                                   Camera.ShutterCallback shutter,
                                   Camera.PictureCallback raw,
                                   Camera.PictureCallback jpeg) {
        if (camera != null) {
            try {
                camera.takePicture(shutter, raw, jpeg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 拍照
     */
    public static void takePicture(Camera camera,
                                   Camera.ShutterCallback shutter,
                                   Camera.PictureCallback raw,
                                   Camera.PictureCallback postview,
                                   Camera.PictureCallback jpeg) {
        if (camera != null) {
            try {
                camera.takePicture(shutter, raw, postview, jpeg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
