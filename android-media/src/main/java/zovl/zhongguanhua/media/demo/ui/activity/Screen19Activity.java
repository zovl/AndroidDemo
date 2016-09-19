package zovl.zhongguanhua.media.demo.ui.activity;

import android.os.Build;
import android.view.View;

import java.io.File;

import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.framework.lib.utils.StorageUtil;
import zovl.zhongguanhua.framework.lib.utils.ToastHelper;
import zovl.zhongguanhua.media.demo.R;
import zovl.zhongguanhua.media.demo.logic.Configuration;
import zovl.zhongguanhua.media.demo.logic.ScreenCapture;
import zovl.zhongguanhua.media.demo.logic.ScreenRecorder;

public class Screen19Activity extends TBaseActivity {

    private File videoFile, imageFile;
    private ScreenRecorder screenRecorder;

    @Override
    public int getContentView() {
        return R.layout.activity_screen19;
    }

    @OnClick({ R.id.startScreenCapture,

            R.id.startScreenRecord,
            R.id.pauseScreenRecord,
            R.id.restartScreenRecord,
            R.id.stopScreenRecord })
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.startScreenCapture:
                if (Build.VERSION.SDK_INT >= 19) {
                    imageFile = StorageUtil.getRootFile("_image.png");

                    ScreenCapture.startCapture(imageFile.getPath());

                    ToastHelper.s(imageFile.getAbsolutePath());
                } else {
                    ToastHelper.s("系统版本低于19");
                }
                break;

            case R.id.startScreenRecord:
                if (Build.VERSION.SDK_INT >= 19) {
                    videoFile = StorageUtil.getRootFile("_video.mp4");

                    Configuration configuration = Configuration.DEFAULT;
                    configuration.setAudio(true);
                    configuration.setLandscape(true);

                    screenRecorder = new ScreenRecorder(videoFile.getPath(), configuration);
                    if (!screenRecorder.isRecording()) {
                        screenRecorder.startRecording();
                    }

                    ToastHelper.s(videoFile.getAbsolutePath());
                } else {
                    ToastHelper.l("系统版本低于19");
                }
                break;

            case R.id.pauseScreenRecord:
                break;

            case R.id.restartScreenRecord:
                break;

            case R.id.stopScreenRecord:
                if (Build.VERSION.SDK_INT >= 19) {
                    if (screenRecorder != null && screenRecorder.isRecording()) {
                        screenRecorder.stopRecording();
                    }

                    ToastHelper.s(videoFile.getAbsolutePath());
                } else {
                    ToastHelper.l("系统版本低于19");
                }
                break;
        }
    }

    // ---------------------------------------------------------------------------------
}
