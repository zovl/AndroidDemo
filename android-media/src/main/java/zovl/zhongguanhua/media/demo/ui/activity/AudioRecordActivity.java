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

public class AudioRecordActivity extends TBaseActivity {

    public static final String TAG = AudioRecordActivity.class.getSimpleName();

    private File file;
    private AudioRecorder audioRecorder = new AudioRecorder();

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
            R.id.open})
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
