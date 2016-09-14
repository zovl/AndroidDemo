package zovl.zhongguanhua.media.demo.logic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.File;

import zovl.zhongguanhua.framework.lib.utils.BitmapUtil;
import zovl.zhongguanhua.framework.lib.utils.StorageUtil;
/**
 * 视图：摄像头
 */
public class CameraSurfaceView extends SurfaceView {

    public final static String TAG = CameraSurfaceView.class.getSimpleName();

    // ---------------------------------------------------------------------------------

    public CameraSurfaceView(Context context) {
        this(context, null);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initialize();
    }

    // ---------------------------------------------------------------------------------

    private CameraHelper cameraHelper;

    public Camera camera() {
        return cameraHelper.camera();
    }

    public void initialize() {

        cameraHelper = new CameraHelper();
        getHolder().addCallback(callback);

        setOnTouchListener(onTouchListener);
    }

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction() &
                    MotionEvent.ACTION_MASK) {

                case MotionEvent.ACTION_DOWN:
                    Point point = new Point((int) event.getX(), (int) event.getY());
                    CameraUtil.focusCamera(camera(), point);
                    break;
            }
            return true;
        }
    };

    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.d(TAG, "surfaceCreated: // --------------------------------------------------");
            Log.d(TAG, "surfaceCreated: holder=" + holder);
            cameraHelper.switchCamera();

            CameraUtil.setOrientation(camera(), 90);
            CameraUtil.setPictureParameters2(camera());
            CameraUtil.setPreviewParametersLargest(camera());

            CameraUtil.setPreview(camera(), CameraSurfaceView.this);
            CameraUtil.startPreview(camera());

            CameraUtil.printCamera(camera());
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Log.d(TAG, "surfaceChanged: // --------------------------------------------------");
            Log.d(TAG, "surfaceChanged: holder=" + holder);
            Log.d(TAG, "surfaceChanged: format=" + format);
            Log.d(TAG, "surfaceChanged: width=" + width);
            Log.d(TAG, "surfaceChanged: height=" + height);
            CameraUtil.restartPreview(camera());
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.d(TAG, "surfaceDestroyed: // --------------------------------------------------");
            Log.d(TAG, "surfaceDestroyed: holder=" + holder);
            CameraUtil.destroyCamera(camera());
        }
    };

    public void switchCamera() {
        if (cameraHelper != null) {
            CameraUtil.stopPreview(camera());
            cameraHelper.switchCamera();

            CameraUtil.setOrientation(camera(), 90);
            CameraUtil.setPictureParameters2(camera());
            CameraUtil.setPreviewParametersLargest(camera());

            CameraUtil.setPreview(camera(), CameraSurfaceView.this);
            CameraUtil.startPreview(camera());

            CameraUtil.printCamera(camera());
        }
    }

    public void switchFlashMode() {
        if (cameraHelper != null) {
            cameraHelper.switchFlashMode();
        }
    }

    public void takePicture() {
        CameraUtil.autoFocusCamera(camera());
        CameraUtil.takePicture(camera(), null, null, null, pictureCallback);
        CameraUtil.restartPreview(camera());
    }

    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            File file = StorageUtil.getRootFile("_pic.png");
            BitmapUtil.saveBitmap(bitmap, file.getPath());
        }
    };
}
