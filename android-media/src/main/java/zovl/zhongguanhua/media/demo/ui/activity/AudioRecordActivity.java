package zovl.zhongguanhua.media.demo.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import java.io.File;

import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.framework.lib.utils.StorageUtil;
import zovl.zhongguanhua.media.demo.R;
import zovl.zhongguanhua.media.demo.logic.AudioRecorder;
import zovl.zhongguanhua.media.demo.logic.AudioRecorder2;

public class AudioRecordActivity extends TBaseActivity {

    public static final String TAG = AudioRecordActivity.class.getSimpleName();

    private File file, file2;
    private AudioRecorder audioRecorder = new AudioRecorder();
    private AudioRecorder2 audioRecorder2 = new AudioRecorder2();

    @Override
    public int getContentView() {
        return R.layout.activity_audio;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        audioRecorder.stopRecord();
    }

    @OnClick({R.id.start,
            R.id.stop,
            R.id.open,
            R.id.start2,
            R.id.stop2,
            R.id.open2})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.start:
                file = StorageUtil.getRootFile("_audio.mp4");
                audioRecorder.startRecord(file.getAbsolutePath());
                toastShort(file.getAbsolutePath());
                break;

            case R.id.stop:
                audioRecorder.stopRecord();
                toastShort(file.getAbsolutePath());
                break;

            case R.id.open:
                if (audioRecorder.isRecording())
                    return;
                openFile(file);
                break;

            case R.id.start2:
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        file2 = StorageUtil.getRootFile("_audio.pcm");
                        audioRecorder2.startRecord(file2.getAbsolutePath());
                        toastShort(file2.getAbsolutePath());
                    }
                }).start();
                break;

            case R.id.stop2:
                audioRecorder2.stopRecord();
                toastShort(file2.getAbsolutePath());
                break;

            case R.id.open2:
                if (audioRecorder2.isRecording())
                    return;
                openFile(file2);
                break;
        }
    }

    private void openFile(File file) {
        if (file != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndTypeAndNormalize(uri, "video/mp4");
            startActivity(intent);
        }
    }

    // ---------------------------------------------------------------------------------
}