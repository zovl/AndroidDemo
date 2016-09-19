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
import zovl.zhongguanhua.media.demo.logic.ScreenRecordActivitya;
import zovl.zhongguanhua.media.demo.logic.ScreenRecordActivityb;

public class ScreenActivity extends TBaseActivity {

    private File videoFile, videoFileb, imageFile;

    @Override
    public int getContentView() {
        return R.layout.activity_screen;
    }

    @OnClick({ R.id.startScreenCapture,
            R.id.open,

            R.id.startScreenRecord,
            R.id.pauseScreenRecord,
            R.id.restartScreenRecord,
            R.id.stopScreenRecord,

            R.id.startScreenRecordb,
            R.id.pauseScreenRecordb,
            R.id.restartScreenRecordb,
            R.id.stopScreenRecordb })
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.startScreenCapture:
                if (Build.VERSION.SDK_INT >= 21) {
                    imageFile = StorageUtil.getRootFile("_image.png");
                    ScreenCaptureActivity.startCapture(this, imageFile.getPath());
                    ToastHelper.s(imageFile.getAbsolutePath());
                } else {
                    ToastHelper.s("系统版本低于21");
                }
                break;

            case R.id.open:
                if (imageFile != null) {
                    openImageFile(imageFile);
                }
                break;

            case R.id.startScreenRecord:
                if (Build.VERSION.SDK_INT >= 21) {
                    videoFile = StorageUtil.getRootFile("_video.mp4");
                    Configuration configuration = Configuration.DEFAULT;
                    configuration.setAudio(true);
                    configuration.setLandscape(true);
                    ScreenRecordActivitya.startRecording(this, videoFile.getPath(), configuration);
                    ToastHelper.s(videoFile.getAbsolutePath());
                } else {
                    ToastHelper.l("系统版本低于21");
                }
                break;

            case R.id.pauseScreenRecord:
                if (Build.VERSION.SDK_INT >= 21) {
                    ScreenRecordActivitya.pauseRecording();
                } else {
                    ToastHelper.l("系统版本低于21");
                }
                break;

            case R.id.restartScreenRecord:
                if (Build.VERSION.SDK_INT >= 21) {
                    ScreenRecordActivitya.restartRecording();
                } else {
                    ToastHelper.l("系统版本低于21");
                }
                break;

            case R.id.stopScreenRecord:
                if (Build.VERSION.SDK_INT >= 21) {
                    ScreenRecordActivitya.stopRecording();
                    ToastHelper.s(videoFile.getAbsolutePath());
                } else {
                    ToastHelper.l("系统版本低于21");
                }
                break;

            case R.id.startScreenRecordb:
                if (Build.VERSION.SDK_INT >= 21) {
                    videoFileb = StorageUtil.getRootFile("_video.mp4");
                    Configuration configuration = Configuration.DEFAULT;
                    configuration.setAudio(true);
                    configuration.setLandscape(true);
                    ScreenRecordActivityb.startRecording(this, videoFileb.getPath(), configuration);
                    ToastHelper.s(videoFileb.getAbsolutePath());
                } else {
                    ToastHelper.l("系统版本低于21");
                }
                break;

            case R.id.pauseScreenRecordb:
                if (Build.VERSION.SDK_INT >= 21) {
                    ScreenRecordActivityb.pauseRecording();
                } else {
                    ToastHelper.l("系统版本低于21");
                }
                break;

            case R.id.restartScreenRecordb:
                if (Build.VERSION.SDK_INT >= 21) {
                    ScreenRecordActivityb.restartRecording();
                } else {
                    ToastHelper.l("系统版本低于21");
                }
                break;

            case R.id.stopScreenRecordb:
                if (Build.VERSION.SDK_INT >= 21) {
                    ScreenRecordActivityb.stopRecording();
                    ToastHelper.s(videoFileb.getAbsolutePath());
                } else {
                    ToastHelper.l("系统版本低于21");
                }
                break;
        }
    }

    // ---------------------------------------------------------------------------------
}
