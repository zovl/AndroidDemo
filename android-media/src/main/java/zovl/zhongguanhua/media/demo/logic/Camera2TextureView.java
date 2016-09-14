package zovl.zhongguanhua.media.demo.logic;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import zovl.zhongguanhua.framework.lib.utils.BitmapUtil;
import zovl.zhongguanhua.framework.lib.utils.StorageUtil;
/**
 * 视图：摄像头5.0
 */
@TargetApi(21)
public class Camera2TextureView extends TextureView  {

    public final static String TAG = Camera2TextureView.class.getSimpleName();

    // ---------------------------------------------------------------------------------

    private Activity activity;

    public Camera2TextureView(Context context) {
        this(context, null);
    }

    public Camera2TextureView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initialize();
        initLooperThread();
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    // ---------------------------------------------------------------------------------

    private String threadName = "HandlerThread-" + TAG;
    private HandlerThread thread;
    private Looper looper;
    private Handler handler;

    public void initialize() {

        setSurfaceTextureListener(listener);
    }

    public void initLooperThread() {

        thread = new HandlerThread(threadName);
        thread.start();
        looper = thread.getLooper();
        handler = new Handler(looper);
    }

    private TextureView.SurfaceTextureListener listener = new TextureView.SurfaceTextureListener() {

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture texture, int width, int height) {
            Log.d(TAG, "onSurfaceTextureAvailable: // --------------------------------------------------");
            Log.d(TAG, "onSurfaceTextureAvailable: texture=" + texture);
            Log.d(TAG, "onSurfaceTextureAvailable: width=" + width);
            Log.d(TAG, "onSurfaceTextureAvailable: height=" + height);

            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, 1);
                }
                return;
            }

            ImageReader reader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1);
            reader.setOnImageAvailableListener(new ImageCallback(), handler);

            CameraManager manager = (CameraManager) getContext().getSystemService(Context.CAMERA_SERVICE);
            try {
                String[] ids = manager.getCameraIdList();
                manager.openCamera("0",
                        new DeviceCallback(manager, reader),
                        handler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture texture) {
            Log.d(TAG, "onSurfaceTextureUpdated: // --------------------------------------------------");
            Log.d(TAG, "onSurfaceTextureUpdated: texture=" + texture);
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture texture) {
            Log.d(TAG, "onSurfaceTextureDestroyed: // --------------------------------------------------");
            Log.d(TAG, "onSurfaceTextureDestroyed: texture=" + texture);
            return true;
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture texture, int width, int height) {
            Log.d(TAG, "onSurfaceTextureSizeChanged: // --------------------------------------------------");
            Log.d(TAG, "onSurfaceTextureSizeChanged: texture=" + texture);
            Log.d(TAG, "onSurfaceTextureSizeChanged: width=" + width);
            Log.d(TAG, "onSurfaceTextureSizeChanged: height=" + height);

        }
    };

    private class DeviceCallback extends CameraDevice.StateCallback {

        private CameraManager manager;
        private ImageReader reader;
        private CameraDevice device;
        private CaptureRequest request;

        public DeviceCallback(CameraManager manager, ImageReader reader) {
            this.manager = manager;
            this.reader = reader;
        }

        @Override
        public void onOpened(CameraDevice device) {
            Log.d(TAG, "onOpened: // --------------------------------------------------");
            Log.d(TAG, "onOpened: device=" + device);
            this.device = device;

            try {
                CaptureRequest.Builder builder = device.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                List<Surface> surfaces = new ArrayList<>();

                CameraCharacteristics characteristics = manager.getCameraCharacteristics("0");
                StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                Size size = map.getOutputSizes(SurfaceTexture.class)[0];
                SurfaceTexture texture = getSurfaceTexture();
                Log.d(TAG, "onOpened: size--width=" + size.getWidth() + "--height=" + size.getHeight());
                texture.setDefaultBufferSize(size.getWidth(), size.getHeight());

                Size[] jpegSizes = null;
                if (characteristics != null) {
                    jpegSizes = map.getOutputSizes(ImageFormat.JPEG);
                }
                int width = 640;
                int height = 480;
                if (jpegSizes != null && 0 < jpegSizes.length) {
                    width = jpegSizes[0].getWidth();
                    height = jpegSizes[0].getHeight();
                }

                Surface surface = new Surface(texture);
                surfaces.add(surface);
                surface = reader.getSurface();
                surfaces.add(surface);
                builder.addTarget(surface);

                // builder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                // builder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
                request = builder.build();

                device.createCaptureSession(surfaces, new SessionCallback(request), handler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onDisconnected(CameraDevice device) {
            Log.d(TAG, "onDisconnected: // --------------------------------------------------");
            Log.d(TAG, "onDisconnected: device=" + device);
            this.device = device;

            try {
                device.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(CameraDevice device, int error) {
            Log.d(TAG, "onError: // --------------------------------------------------");
            Log.d(TAG, "onError: device=" + device);
            Log.d(TAG, "onError: error=" + error);
            this.device = device;

            try {
                device.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class SessionCallback extends CameraCaptureSession.StateCallback {

        private CameraCaptureSession session;
        private CaptureRequest request;

        public SessionCallback(CaptureRequest request) {
            this.request = request;
        }

        @Override
        public void onConfigured(CameraCaptureSession session) {
            Log.d(TAG, "onConfigured: // --------------------------------------------------");
            Log.d(TAG, "onConfigured: session=" + session);
            this.session = session;

            try {
                session.setRepeatingRequest(request, new CaptureCallback(), handler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onConfigureFailed(CameraCaptureSession session) {
            Log.d(TAG, "onConfigureFailed: // --------------------------------------------------");
            Log.d(TAG, "onConfigureFailed: session=" + session);
            this.session = session;
        }
    }

    public class CaptureCallback extends CameraCaptureSession.CaptureCallback {

        @Override
        public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
            super.onCaptureCompleted(session, request, result);
        }

        @Override
        public void onCaptureFailed(CameraCaptureSession session, CaptureRequest request, CaptureFailure failure) {
            super.onCaptureFailed(session, request, failure);
        }

    }

    public AtomicBoolean pic = new AtomicBoolean(false);

    private class ImageCallback implements ImageReader.OnImageAvailableListener {

        @Override
        public void onImageAvailable(ImageReader reader) {
            Log.d(TAG, "onImageAvailable: // --------------------------------------------------");
            Log.d(TAG, "onImageAvailable: reader=" + reader);
            if (pic.get()) {
                pic.set(false);
                File file = StorageUtil.getRootFile("_pic2.png");
                Image image = null;
                try {
                    image = reader.acquireLatestImage();
                    ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                    byte[] bytes = new byte[buffer.capacity()];
                    buffer.get(bytes);
                    BitmapUtil.saveBitmap(bytes, file.getPath());
                } finally {
                    if (image != null) {
                        try {
                            image.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
