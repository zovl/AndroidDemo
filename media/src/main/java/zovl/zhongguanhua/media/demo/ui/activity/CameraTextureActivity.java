package zovl.zhongguanhua.media.demo.ui.activity;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import zovl.zhongguanhua.media.demo.logic.CameraTextureView;

public class CameraTextureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        new Thread(new Runnable() {
            @Override
            public void run() {

                Looper.prepare();
                final CameraTextureView view = new CameraTextureView(CameraTextureActivity.this);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        setContentView(view);
                    }
                });

                Looper.loop();
            }
        }).start();
    }
}
