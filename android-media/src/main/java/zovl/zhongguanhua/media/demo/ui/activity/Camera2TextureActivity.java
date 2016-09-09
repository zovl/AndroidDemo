package zovl.zhongguanhua.media.demo.ui.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.framework.lib.utils.StorageUtil;
import zovl.zhongguanhua.media.demo.R;
import zovl.zhongguanhua.media.demo.logic.Camera2TextureView;

public class Camera2TextureActivity extends TBaseActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback {

    @Bind(R.id.texture)
    Camera2TextureView textureView;

    File file;

    @Override
    public int getContentView() {
        return R.layout.activity_camera2texture;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        textureView.setActivity(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1) {
            if (grantResults.length != 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                // error
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
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
                toastShort(file.getAbsolutePath());
                break;

            case R.id.stop:
                toastShort(file.getPath());
                break;

            case R.id.swt:
                break;

            case R.id.flash:
                break;

            case R.id.add:
                break;

            case R.id.mis:
                break;

            case R.id.pic:
                textureView.pic.set(true);
                break;
        }
    }
}