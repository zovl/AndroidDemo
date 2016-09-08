package zovl.zhongguanhua.media.demo.logic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.opengl.GLES11Ext;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import zovl.zhongguanhua.framework.lib.utils.BitmapUtil;
import zovl.zhongguanhua.framework.lib.utils.StorageUtil;

public class PreviewSurfaceView extends SurfaceView {

    public final static String TAG = PreviewSurfaceView.class.getSimpleName();

    // ---------------------------------------------------------------------------------

    public PreviewSurfaceView(Context context) {
        this(context, null);
    }

    public PreviewSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initialize();
    }

    // ---------------------------------------------------------------------------------

    /**
     * 图片线程
     */
    private String threadName = "LoopThread-PreviewSurfaceView";
    private HandlerThread thread;
    private Handler handler;
    private Handler h = new Handler(Looper.getMainLooper());

    private Camera camera;
    private SurfaceTexture texture;
    private int width, height;
    private byte[] previewBuffer;
    private int format;
    private long nanoSecs;

    public void initialize() {

        texture = new SurfaceTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES);

        getHolder().addCallback(callback);
    }

    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.d(TAG, "surfaceCreated: // --------------------------------------------------");
            Log.d(TAG, "surfaceCreated: holder=" + holder);

            thread = new HandlerThread(threadName, android.os.Process.THREAD_PRIORITY_BACKGROUND);
            thread.start();
            handler = new Handler(thread.getLooper());

            handler.post(new Runnable() {
                @Override
                public void run() {

                    // 打开相机
                    Camera.CameraInfo info = new Camera.CameraInfo();
                    int number = Camera.getNumberOfCameras();
                    for (int i = 0; i < number; i++) {
                        Camera.getCameraInfo(i, info);
                        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                            try {
                                camera = Camera.open(i);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    if (camera != null) {
                        Camera.Parameters parameters = camera.getParameters();
                        if (parameters != null) {

                            // 获取最小预览尺寸
                            List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();
                            Camera.Size s = parameters.getPreviewSize();
                            if (sizes != null && sizes.size() > 0) {
                                for (Camera.Size size : sizes) {
                                    if (size.width <= s.width
                                            && size.height <= s.height) {
                                        s = size;
                                    }
                                }
                                width = s.width;
                                height = s.height;
                            }
/*
                            // 倒数第二小预览尺寸
                            Comparators.sortCameraSize(sizes);
                            int index = 1;
                            if (sizes.size() > index) {
                                width = sizes.get(index).width;
                                height = sizes.get(index).height;
                            }*/

                            parameters.setPreviewSize(width, height);
                            // parameters.setPreviewFrameRate(15);
                            // parameters.setPreviewFpsRange(0, 15);
                            List<Integer> formats = parameters.getSupportedPreviewFormats();
                            for (Integer format : formats) {
                                if (format == ImageFormat.FLEX_RGB_888) {
                                    parameters.setPreviewFormat(ImageFormat.FLEX_RGB_888);
                                    break;
                                }
                            }

                            try {
                                camera.setParameters(parameters);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        width = camera.getParameters().getPreviewSize().width;
                        height = camera.getParameters().getPreviewSize().height;
                        format = camera.getParameters().getPreviewFormat();

                        Log.d(TAG, "surfaceCreated: width=" + width);
                        Log.d(TAG, "surfaceCreated: height=" + height);
                        Log.d(TAG, "surfaceCreated: format=" + format);

                        try {
                            camera.setPreviewTexture(texture);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
/*
                        int bufferSize = width * height * ImageFormat.getBitsPerPixel(format) / 8;
                        previewBuffer = new byte[bufferSize];
                        camera.addCallbackBuffer(previewBuffer);
                        camera.setPreviewCallbackWithBuffer(previewCallback);*/

                        camera.setPreviewCallback(previewCallback);

                        camera.startPreview();
                    }
                    h.post(new Runnable() {
                        @Override
                        public void run() {

                            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                                    FrameLayout.LayoutParams.MATCH_PARENT,
                                    FrameLayout.LayoutParams.MATCH_PARENT);
                            /*
                            // 设置显示画面大小：按照相机预览尺寸大小
                            params.width = width;
                            params.height = height;*/

                            // 设置显示画面大小：按照相机预览尺寸大小裁剪成正方形
                            if (width - height >= 0) {
                                params.width = height;
                                params.height = height;
                            } else {
                                params.width = width;
                                params.height = width;

                            }

                            params.gravity = Gravity.CENTER;
                            setLayoutParams(params);
                            Log.d(TAG, "surfaceCreated: width=" + params.width);
                            Log.d(TAG, "surfaceCreated: height=" + params.height);
                        }
                    });
                }
            });
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Log.d(TAG, "surfaceChanged: // --------------------------------------------------");
            Log.d(TAG, "surfaceChanged: holder=" + holder);
            Log.d(TAG, "surfaceChanged: format=" + format);
            Log.d(TAG, "surfaceChanged: width=" + width);
            Log.d(TAG, "surfaceChanged: height=" + height);

            if (camera != null) {
                camera.stopPreview();
                try {
                    camera.reconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                camera.setPreviewCallback(previewCallback);
                camera.startPreview();
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.d(TAG, "surfaceDestroyed: // --------------------------------------------------");
            Log.d(TAG, "surfaceDestroyed: holder=" + holder);

            if (camera != null) {
                camera.addCallbackBuffer(null);
                camera.setPreviewCallback(null);
                camera.setPreviewCallbackWithBuffer(null);
                camera.stopPreview();
                camera.release();
                camera = null;
            }

            if (thread != null)
                try {
                    thread.quit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    };

    // ---------------------------------------------------------------------------------

    private Object syncObject = new Object();
    private int index = 0;

    private Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {

            // Log.d(TAG, "onPreviewFrame: // --------------------------------------------------");
            // Log.d(TAG, "onPreviewFrame: thread=" + Thread.currentThread());
            // Log.d(TAG, "onPreviewFrame: data=" + data.length);
/*
            if (camera != null && texture != null) {
                texture.updateTexImage();
                nanoSecs = texture.getTimestamp() / 1000000;
                camera.addCallbackBuffer(previewBuffer);
                Log.d(TAG, "onPreviewFrame: nanoSecs=" + nanoSecs);
                Log.d(TAG, "onPreviewFrame: previewBuffer=" + previewBuffer.length);
            }*/

            ++ index;
            // Log.d(TAG, "onPreviewFrame: index=" + index);
            // TODO: 08/09/2016  不要自此处做耗时的工作，因为那么极可能会导致丢掉一些预览数据帧
            // TODO: 2016/7/11 此处处理如果如果用线程池，线程切换容易导致阻塞
            // index % 2 == 0 间隔取帧会导致画面不连续
            SurfaceHolder holder = getHolder();
            if (camera != null && holder != null) {

                Bitmap srcBitmap = null, secBitmap = null, thirBitmap = null, fourBitmap = null;
                try {
/*
                    // 第一种：元数据像素格式转换，直接转换颜色字节数组
                    int newData[] = new int[width * height];
                    ImageUtil.RGBtoYUV420SP(newData, data, width, height);
                    srcBitmap = Bitmap.createBitmap(newData, width, height, Bitmap.Config.ARGB_8888);*/

                    // 第二种：元数据像素格式转换，利用接口转换
                    YuvImage image = new YuvImage(data, format, width, height, null);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    image.compressToJpeg(new Rect(0, 0, width, height), 100, stream);
                    srcBitmap = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());
                    stream.close();

                    // 图片处理：旋转，裁剪
                    int w = srcBitmap.getWidth();
                    int h = srcBitmap.getHeight();
                    int x, y, a;
                    if (w < h) {
                        x = 0;
                        y = Math.abs(h - w) / 2;
                        a = w;
                    } else {
                        x = Math.abs(h - w) / 2;
                        y = 0;
                        a = h;
                    }
                    secBitmap = Bitmap.createBitmap(srcBitmap, x, y, a, a);

                    int rotation = -90;
                    Matrix matrix = new Matrix();
                    matrix.setRotate(rotation, a / 2, a / 2);
                    thirBitmap = Bitmap.createBitmap(secBitmap, 0, 0, a, a, matrix, false);

                    // fourBitmap = Bitmap.createScaledBitmap(thirBitmap, 420, 420, false);

                    if (srcBitmap != null) {
                        synchronized (syncObject) {
                            // 通过画笔绘图
                            Canvas canvas = holder.lockCanvas();
                            canvas.drawBitmap(thirBitmap, 0, 0, null);
                            holder.unlockCanvasAndPost(canvas);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (srcBitmap != null) {
                        try {
                            srcBitmap.recycle();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (secBitmap != null) {
                        try {
                            srcBitmap.recycle();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (thirBitmap != null) {
                        try {
                            srcBitmap.recycle();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (fourBitmap != null) {
                        try {
                            srcBitmap.recycle();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    };
}
