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
/**
 * 工具：摄像头
 */
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
     * @param flag 是否打开前置相机
     */
    public static Camera openCamera(boolean flag) {
        Log.d(TAG, "openCamera: flag=" + flag);
        if (flag) {// 打开前置摄像头
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
        Log.d(TAG, "openCamera: i=" + i);
        try {
            Camera camera =  Camera.open(i);
            return camera;
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
                camera.setPreviewCallback(null);
                camera.setPreviewCallbackWithBuffer(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        int number = Camera.getNumberOfCameras();
        Log.d(TAG, "cameraSize: number=" + number);
        return number;
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
    public static boolean frontCamera(int i) {
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
        return restartPreview(camera, null);
    }

    /**
     * 相机预览
     */
    public static boolean restartPreview(Camera camera, Camera.PreviewCallback callback) {
        if (camera != null) {
            try {
                camera.stopPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (callback != null)
                camera.setPreviewCallback(callback);
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
        return startPreview(camera, null);
    }

    /**
     * 相机开始预览
     */
    public static boolean startPreview(Camera camera, Camera.PreviewCallback callback) {
        if (camera != null) {
            if (callback != null)
                camera.setPreviewCallback(callback);
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
                    Log.d(TAG, "setFlashMode: true");
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
            int maxZoom = camera.getParameters().getMaxZoom();
            Log.d(TAG, "maxZoom: maxZoom=" + maxZoom);
            return maxZoom;
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
            int zoom = camera.getParameters().getZoom();
            Log.d(TAG, "maxZoom: zoom=" + zoom);
            return zoom;
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
                    Log.d(TAG, "setPreviewParametersSmallest: previewSize--width=" + previewSize.width + "--height=" + previewSize.height);
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
                    Log.d(TAG, "setPreviewParametersLargest: previewSize--width=" + previewSize.width + "--height=" + previewSize.height);
                    parameters.setPreviewSize(previewSize.width, previewSize.height);
                }

                try {
                    camera.setParameters(parameters);
                    Log.d(TAG, "setPreviewParametersLargest: true");
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
                    Log.d(TAG, "setPreviewParametersLargest: frameRate=" + frameRate);
                    parameters.setPreviewFrameRate(frameRate);
                }

                try {
                    camera.setParameters(parameters);
                    Log.d(TAG, "setPreviewParametersLargest: true");
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
            Log.d(TAG, "autoFocusCamera: true");
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
            Log.d(TAG, "focusCamera: true");
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
    public static String printInfo(Camera.CameraInfo info) {
        Log.d(TAG, "----------------[printInfo]------------------");
        if (info != null) {
            StringBuffer buffer = new StringBuffer();
            buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");
            buffer.append("\n");

            buffer.append("canDisableShutterSound=" + info.canDisableShutterSound + "\n");
            buffer.append("facing=" + info.facing + "\n");
            buffer.append("orientation=" + info.orientation + "\n");

            buffer.append("\n");
            Log.d(TAG, "printInfo: " + "\n" + buffer.toString());
            return buffer.toString();
        }
        return "info is null";
    }

    /**
     * 打印相机参数
     */
    public static String printCamera(Camera camera) {
        Log.d(TAG, "----------------[printCamera]------------------");
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            if (parameters != null) {
                return printParameters(parameters);
            }
            return "parameters is null";
        }
        return "camera is null";

    }

    /**
     * 打印相机参数
     */
    public static String printParameters(Camera.Parameters parameters) {
        if (parameters != null) {
            StringBuffer buffer = new StringBuffer();
            buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");
            buffer.append("\n");

            // ----------------------------------------------------------------------

            buffer.append("[Preview]" + "\n");
            Camera.Size previewSize = parameters.getPreviewSize();
            buffer.append("previewSize" + "\n");
            buffer.append(cameraSize(previewSize));

            buffer.append("previewFrameRate=" + parameters.getPreviewFrameRate() + "\n");

            int previewFormat = parameters.getPreviewFormat();
            buffer.append("previewFormat" + "\n");
            buffer.append(imageFormat(previewFormat));

            List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
            buffer.append("supportedPreviewSizes" + "\n");
            buffer.append(cameraSizes(previewSizes));

            Camera.Size preferredPreviewSize = parameters.getPreferredPreviewSizeForVideo();
            buffer.append("preferredPreviewSizeForVideos" + "\n");
            buffer.append(cameraSize(preferredPreviewSize));

            List<Integer> previewFrameRates = parameters.getSupportedPreviewFrameRates();
            buffer.append("supportedPreviewFrameRates" + "\n");
            if (previewFrameRates != null) {
                for (Integer previewFrameRate: previewFrameRates){
                    buffer.append("\t" + previewFrameRate + "\n");
                }
            }

            List<int[]> list = parameters.getSupportedPreviewFpsRange();
            buffer.append("supportedPreviewFpsRanges" + "\n");
            if (list != null) {
                for (int[] arr: list){
                    for (int a : arr) {
                        buffer.append("\t" + a + "\n");
                    }
                }
            }

            List<Integer> previewFormats = parameters.getSupportedPreviewFormats();
            buffer.append("supportedPreviewFormats" + "\n");
            buffer.append(imageFormats(previewFormats));

            // ----------------------------------------------------------------------

            buffer.append("[Picture]" + "\n");
            // 相机图片格式
            buffer.append("pictureFormat" + "\n");
            int pictureFormat = parameters.getPictureFormat();
            buffer.append(imageFormat(pictureFormat));
            // 相机图片尺寸
            Camera.Size pictureSize = parameters.getPictureSize();
            buffer.append("pictureSize" + "\n");
            buffer.append(cameraSize(pictureSize));

            buffer.append("JpegQuality=" + parameters.getJpegQuality() + "\n");
            buffer.append("jpegThumbnailQuality=" + parameters.getJpegThumbnailQuality() + "\n");
            buffer.append("jpegThumbnailSize=" + parameters.getJpegThumbnailSize() + "\n");
            // 相机支持图片尺寸
            List<Camera.Size> pictureSizes = parameters.getSupportedPictureSizes();
            buffer.append("supportedPictureSizes" + "\n");
            buffer.append(cameraSizes(pictureSizes));
            // 相机支持图片格式
            List<Integer> picFormats = parameters.getSupportedPictureFormats();
            buffer.append("supportedPictureFormats" + "\n");
            buffer.append(imageFormats(picFormats));
            // 相机支持缩略图
            List<Camera.Size> jpegThumbnailSizes = parameters.getSupportedJpegThumbnailSizes();
            buffer.append("supportedJpegThumbnailSizes" + "\n");
            buffer.append(cameraSizes(jpegThumbnailSizes));

            // ----------------------------------------------------------------------

            buffer.append("[Video]" + "\n");
            buffer.append("videoStabilization=" + parameters.getVideoStabilization() + "\n");
            // 相机支持录像尺寸
            List<Camera.Size> videoSizes = parameters.getSupportedVideoSizes();
            buffer.append("supportedVideoSizes" + "\n");
            buffer.append(cameraSizes(videoSizes));

            // ----------------------------------------------------------------------

            buffer.append("[Zoom]" + "\n");
            buffer.append("zoom=" + parameters.getZoom() + "\n");
            buffer.append("isZoomSupported=" + parameters.isZoomSupported() + "\n");
            buffer.append("maxZoom=" + parameters.getMaxZoom() + "\n");
            buffer.append("zoomRatios=" + parameters.getZoomRatios() + "\n");

            // ----------------------------------------------------------------------

            buffer.append("[Parameters]" + "\n");
            buffer.append("FlashMode=" + parameters.getFlashMode() + "\n");
            buffer.append("focusMode=" + parameters.getFocusMode() + "\n");
            buffer.append("sceneMode=" + parameters.getSceneMode() + "\n");
            buffer.append("horizontalViewAngle=" + parameters.getHorizontalViewAngle() + "\n");

            List<String> flashModes = parameters.getSupportedFlashModes();
            if (flashModes != null) {
                for (String flashMode : flashModes) {
                    buffer.append("supportedFlashMode=" + flashMode + "\n");
                }
            }
            List<String> focusModes = parameters.getSupportedFocusModes();
            if (focusModes != null) {
                for (String focusMode :
                        focusModes) {
                    buffer.append("supportedFocusModes=" + focusMode + "\n");
                }
            }
            List<String> sceneModes = parameters.getSupportedSceneModes();
            if (sceneModes != null) {
                for (String sceneMode :
                        sceneModes) {
                    buffer.append("supportedSceneMode=" + sceneMode + "\n");
                }
            }
            List<String> antibandings = parameters.getSupportedAntibanding();
            if (antibandings != null) {
                for (String antibanding :
                        antibandings) {
                    buffer.append("supportedAntibanding=" + antibanding + "\n");
                }
            }
            List<String> whiteBalances = parameters.getSupportedWhiteBalance();
            if (whiteBalances != null) {
                for (String whiteBalance :
                        whiteBalances) {
                    buffer.append("supportedWhiteBalance=" + whiteBalance + "\n");
                }
            }
            List<String> colorEffects = parameters.getSupportedColorEffects();
            if (colorEffects != null) {
                for (String colorEffect :
                        colorEffects) {
                    buffer.append("supportedColorEffect=" + colorEffect + "\n");
                }
            }

            buffer.append("\n");
            Log.d(TAG, "printParameters: " + "\n" + buffer.toString());
            return buffer.toString();
        }
        return "parameters is null";
    }

    /**
     * 打印图片参数
     */
    public static String imageFormats(List<Integer> formats) {
        if (formats != null && formats.size() > 0) {
            StringBuffer buffer = new StringBuffer();
            for (Integer format : formats) {
                buffer.append("\tformat=" + format + "\n");
                buffer.append(imageFormat(format));
            }
            return buffer.toString();
        }
        return "";
    }

    /**
     * 打印图片参数
     */
    public static String imageFormat(Integer format) {
        if (format != null) {
            int bitsPerPixel = ImageFormat.getBitsPerPixel(format);
            StringBuffer buffer = new StringBuffer();
            buffer.append("\t\tbitsPerPixel=" + bitsPerPixel + "\n");
            switch (format) {
                case ImageFormat.RGB_565:
                    buffer.append("\t\tformat=" + "RGB_565" + "\n");
                case ImageFormat.NV16:
                    buffer.append("\t\tformat=" + "NV16" + "\n");
                case ImageFormat.YUY2:
                    buffer.append("\t\tformat=" + "YUY2" + "\n");
                case ImageFormat.YV12:
                    buffer.append("\t\tformat=" + "YV12" + "\n");
                case ImageFormat.DEPTH16:
                    buffer.append("\t\tformat=" + "DEPTH16" + "\n");
                case ImageFormat.NV21:
                    buffer.append("\t\tformat=" + "NV21" + "\n");
                case ImageFormat.YUV_420_888:
                    buffer.append("\t\tformat=" + "YUV_420_888" + "\n");
                case ImageFormat.YUV_422_888:
                    buffer.append("\t\tformat=" + "YUV_422_888" + "\n");
                case ImageFormat.YUV_444_888:
                    buffer.append("\t\tformat=" + "YUV_444_888" + "\n");
                case ImageFormat.FLEX_RGB_888:
                    buffer.append("\t\tformat=" + "FLEX_RGB_888" + "\n");
                case ImageFormat.FLEX_RGBA_8888:
                    buffer.append("\t\tformat=" + "FLEX_RGBA_8888" + "\n");
                case ImageFormat.RAW_SENSOR:
                    buffer.append("\t\tformat=" + "RAW_SENSOR" + "\n");
                case ImageFormat.RAW10:
                    buffer.append("\t\tformat=" + "RAW10" + "\n");
                case ImageFormat.RAW12:
                    buffer.append("\t\tformat=" + "RAW12" + "\n");
            }
            return buffer.toString();
        }
        return "";
    }

    /**
     * 打印相机尺寸
     */
    public static String cameraSizes(List<Camera.Size> sizes) {
        if (sizes != null && sizes.size() > 0) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("\tsizes" + "\n");
            for (Camera.Size size : sizes) {
                buffer.append(cameraSize(size));
            }
            return buffer.toString();
        }
        return "";
    }

    /**
     * 打印相机尺寸
     */
    public static String cameraSize(Camera.Size size) {
        if (size != null) {
            String s = "\t\twidth=" + size.width + "--height=" + size.height + "\n";
            return s;
        }
        return "";
    }
}
