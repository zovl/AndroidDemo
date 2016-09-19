package zovl.zhongguanhua.media.demo.logic;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;
/**
 * 视图：摄像头5.0
 */
public class CameraTextureView extends TextureView {

    public final static String TAG = CameraTextureView.class.getSimpleName();

    // ---------------------------------------------------------------------------------

    public CameraTextureView(Context context) {
        this(context, null);
    }

    public CameraTextureView(Context context, AttributeSet attrs) {
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

        setSurfaceTextureListener(listener);
    }

    private SurfaceTextureListener listener = new SurfaceTextureListener() {

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture texture, int width, int height) {
            Log.d(TAG, "onSurfaceTextureAvailable: // --------------------------------------------------");
            Log.d(TAG, "onSurfaceTextureAvailable: texture=" + texture);
            Log.d(TAG, "onSurfaceTextureAvailable: width=" + width);
            Log.d(TAG, "onSurfaceTextureAvailable: height=" + height);

            cameraHelper.switchCamera();
            CameraUtil.setOrientation(cameraHelper.camera(), 90);
            CameraUtil.setPreviewParametersLargest(cameraHelper.camera());
            CameraUtil.setPreview(cameraHelper.camera(), CameraTextureView.this);
            CameraUtil.startPreview(cameraHelper.camera());

            CameraUtil.printCamera(cameraHelper.camera());
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture texture) {
            Log.d(TAG, "onSurfaceTextureUpdated: // --------------------------------------------------");
            Log.d(TAG, "onSurfaceTextureUpdated: texture=" + texture);

            CameraUtil.restartPreview(cameraHelper.camera());
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture texture) {
            Log.d(TAG, "onSurfaceTextureDestroyed: // --------------------------------------------------");
            Log.d(TAG, "onSurfaceTextureDestroyed: texture=" + texture);

            CameraUtil.destroyCamera(cameraHelper.camera());
            return true;
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture texture, int width, int height) {
            Log.d(TAG, "onSurfaceTextureSizeChanged: // --------------------------------------------------");
            Log.d(TAG, "onSurfaceTextureSizeChanged: texture=" + texture);
            Log.d(TAG, "onSurfaceTextureSizeChanged: width=" + width);
            Log.d(TAG, "onSurfaceTextureSizeChanged: height=" + height);

            CameraUtil.restartPreview(cameraHelper.camera());
        }
    };
}
