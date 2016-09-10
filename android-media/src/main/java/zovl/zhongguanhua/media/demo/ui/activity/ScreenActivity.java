package zovl.zhongguanhua.media.demo.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;

import java.io.File;

import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.framework.lib.utils.StorageUtil;
import zovl.zhongguanhua.framework.lib.utils.ToastHelper;
import zovl.zhongguanhua.media.demo.R;
import zovl.zhongguanhua.media.demo.logic.Configuration;
import zovl.zhongguanhua.media.demo.logic.ScreenCaptureActivity;
import zovl.zhongguanhua.media.demo.logic.ScreenRecordActivity;

public class ScreenActivity extends TBaseActivity {

    private File videoFile, imageFile;

    @Override
    public int getContentView() {
        return R.layout.activity_screen;
    }

    @OnClick({ R.id.startScreenCapture,
            R.id.startScreenRecord,
            R.id.pauseScreenRecord,
            R.id.restartScreenRecord,
            R.id.stopScreenRecord,
            R.id.open })
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.startScreenCapture:
                if (Build.VERSION.SDK_INT >= 21) {
                    imageFile = StorageUtil.getRootFile("_image.png");
                    ScreenCaptureActivity.startScreenCaptureActivity(this, imageFile.getPath());
                } else {
                    ToastHelper.s("系统版本低于21");
                }
                break;

            case R.id.startScreenRecord:
                if (Build.VERSION.SDK_INT >= 21) {
                    videoFile = StorageUtil.getRootFile("_video.mp4");
                    ScreenRecordActivity.startScreenRecordActivity(this, videoFile.getPath(), Configuration.DEFAULT);
                } else {
                    ToastHelper.s("系统版本低于21");
                }
                break;

            case R.id.pauseScreenRecord:
                if (Build.VERSION.SDK_INT >= 21) {
                    ScreenRecordActivity.pauseRecording();
                } else {
                    ToastHelper.s("系统版本低于21");
                }
                break;

            case R.id.restartScreenRecord:
                if (Build.VERSION.SDK_INT >= 21) {
                    ScreenRecordActivity.restartRecording();
                } else {
                    ToastHelper.s("系统版本低于21");
                }
                break;

            case R.id.stopScreenRecord:
                if (Build.VERSION.SDK_INT >= 21) {
                    ScreenRecordActivity.stopRecording();
                } else {
                    ToastHelper.s("系统版本低于21");
                }
                break;

            case R.id.open:
                if (imageFile != null) {
                    openImageFile(imageFile);
                }
                if (videoFile != null) {
                    openImageFile(videoFile);
                }
                break;
        }
    }

    private void openVideoFile(File file) {
        if (file != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndTypeAndNormalize(uri, "video/mp4");
            startActivity(intent);
        }
    }

    private void openImageFile(File file) {
        if (file != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndTypeAndNormalize(uri, "image");
            startActivity(intent);
        }
    }

    // ---------------------------------------------------------------------------------
}
