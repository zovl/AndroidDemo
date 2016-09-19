package zovl.zhongguanhua.media.demo.ui.activity;

import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.framework.lib.utils.StorageUtil;
import zovl.zhongguanhua.framework.lib.utils.ToastHelper;
import zovl.zhongguanhua.media.demo.R;
import zovl.zhongguanhua.media.demo.logic.CameraRecorder;
import zovl.zhongguanhua.media.demo.logic.CameraSurfaceView;
import zovl.zhongguanhua.media.demo.logic.CameraUtil;

public class CameraSurfaceActivity extends TBaseActivity {

    @Bind(R.id.surface)
    CameraSurfaceView surfaceView;

    @Bind(R.id.seekBar)
    SeekBar seekBar;

    CameraRecorder cameraRecorder;
    File file;

    public Camera camera() {
        return surfaceView.camera();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_camerasurface;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        cameraRecorder = new CameraRecorder();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                seekBar.setMax(CameraUtil.maxZoom(camera()));
                seekBar.setProgress(CameraUtil.zoom(camera()));
                seekBar.setOnSeekBarChangeListener(listener);
            }
        }, 800);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cameraRecorder.destroy();
    }

    @OnClick({R.id.start,
            R.id.stop,

            R.id.swt,

            R.id.flash,

            R.id.add,
            R.id.mis,

            R.id.pic})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.start:
                file = StorageUtil.getRootFile("_camera.mp4");
                cameraRecorder.startRecord(file.getPath(), surfaceView.camera());
                ToastHelper.s(file.getAbsolutePath());
                break;

            case R.id.stop:
                cameraRecorder.stopRecord();
                ToastHelper.s(file.getPath());
                break;

            case R.id.swt:
                surfaceView.switchCamera();
                break;

            case R.id.flash:
                surfaceView.switchFlashMode();
                break;

            case R.id.add:
                CameraUtil.addZoom(camera());
                break;

            case R.id.mis:
                CameraUtil.minusZoom(camera());
                break;

            case R.id.pic:
                surfaceView.takePicture();
                break;
        }
    }

    private SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            CameraUtil.setZoom(camera(), progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}
