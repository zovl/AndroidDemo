package zovl.zhongguanhua.media.demo.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.framework.lib.utils.ToastHelper;
import zovl.zhongguanhua.media.demo.R;

public class MainActivity extends TBaseActivity {

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.extractor,
            R.id.retriever,
            R.id.codec,
            R.id.mp4prser,
            R.id.screen,
            R.id.audioRecord,
            R.id.cameraSurface,
            R.id.cameraTexture,
            R.id.camera2Texture,
            R.id.previewSurface})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.audioRecord:
                startActivity(AudioRecordActivity.class);
                break;

            case R.id.cameraSurface:
                startActivity(CameraSurfaceActivity.class);
                break;

            case R.id.cameraTexture:
                startActivity(CameraTextureActivity.class);
                break;

            case R.id.camera2Texture:
                if (Build.VERSION.SDK_INT >= 21) {
                    startActivity(Camera2TextureActivity.class);
                } else {
                    ToastHelper.l("系统版本低于21");
                }
                break;

            case R.id.previewSurface:
                startActivity(PreviewSurfaceActivity.class);
                break;

            case R.id.extractor:
                startActivity(ExtractorActivity.class);
                break;

            case R.id.retriever:
                startActivity(RetrieverActivity.class);
                break;

            case R.id.codec:
                startActivity(CodecActivity.class);
                break;

            case R.id.mp4prser:
                startActivity(MP4parserActivity.class);
                break;

            case R.id.screen:
                startActivity(ScreenActivity.class);
                break;
        }
    }
}
