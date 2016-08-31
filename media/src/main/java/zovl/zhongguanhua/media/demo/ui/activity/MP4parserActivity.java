package zovl.zhongguanhua.media.demo.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.framework.lib.utils.StorageUtil;
import zovl.zhongguanhua.media.demo.R;
import zovl.zhongguanhua.media.demo.logic.MP4parserHelper;

public class MP4parserActivity extends TBaseActivity {

    public static final String TAG = MP4parserActivity.class.getSimpleName();

    @Bind(R.id.insertStartTime)
    EditText startTime;
    @Bind(R.id.insertEndTime)
    EditText endTime;
    @Bind(R.id.trimPath)
    EditText trimPath;
    @Bind(R.id.extractVideoPath)
    EditText extractVideoPath;
    @Bind(R.id.extractAudioPath)
    EditText extractAudioPath;
    @Bind(R.id.muxVideoPath)
    EditText muxVideoPath;
    @Bind(R.id.muxAudioPath)
    EditText muxAudioPath;

    public long getStartTime() {
        if (startTime.getText() != null)
            Long.valueOf(startTime.getText().toString());
        return 0;
    }

    public long getEndTime() {
        if (endTime.getText() != null)
            Long.valueOf(endTime.getText().toString());
        return 255 * 1000;
    }

    @Override
    public TextView getText() {
        return text;
    }

    @Override
    public ImageView getImage() {
        return image;
    }

    @Bind(R.id.image)
    ImageView image;

    @Bind(R.id.text)
    TextView text;

    @Override
    public int getContentView() {
        return R.layout.activity_mp4parser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String mediaPath = StorageUtil.getRootExitPath("media_b.mp4");
        setText(mediaPath);

        trimPath.setText(mediaPath);
        extractVideoPath.setText(mediaPath);
        extractAudioPath.setText(mediaPath);

        muxVideoPath.setText(StorageUtil.getRootExitPath("media_a.mp4"));
        muxAudioPath.setText(StorageUtil.getRootExitPath("media_b_audio.mp4"));
    }

    private File dstVideoPath, dstAudioPath;

    @OnClick({R.id.trim,
            R.id.extractVideo,
            R.id.extractAudio,
            R.id.mux,
            R.id.mutilMux})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.trim:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String src = trimPath.getText().toString();
                        final File dst = StorageUtil.getRootFile("_mp4parser_trim.mp4");
                        boolean flag = MP4parserHelper.trimMedia(src, dst.getPath(), getStartTime(), getEndTime());
                        if (flag)
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    toastShort(dst.getAbsolutePath());
                                    setText(dst.getAbsolutePath() + "\n");
                                }
                            });
                    }
                }).start();
                break;

            case R.id.extractAudio:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String src = extractAudioPath.getText().toString();
                        dstAudioPath = StorageUtil.getRootFile("_mp4parser_audio.mp4");
                        boolean flag = MP4parserHelper.extractAudio(src, dstAudioPath.getPath());
                        if (flag)
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    toastShort(dstAudioPath.getAbsolutePath());
                                    setText(dstAudioPath.getAbsolutePath() + "\n");
                                    muxAudioPath.setText(dstAudioPath.getPath());
                                }
                            });
                    }
                }).start();
                break;

            case R.id.extractVideo:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String src = extractVideoPath.getText().toString();
                        dstVideoPath = StorageUtil.getRootFile("_mp4parser_video.mp4");
                        boolean flag = MP4parserHelper.extractVideo(src, dstVideoPath.getPath());
                        if (flag)
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    toastShort(dstVideoPath.getAbsolutePath());
                                    setText(dstVideoPath.getAbsolutePath() + "\n");
                                    muxVideoPath.setText(dstVideoPath.getPath());
                                }
                            });
                    }
                }).start();
                break;

            case R.id.mux:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String videoPath = muxVideoPath.getText().toString();
                        final String audioPath = muxAudioPath.getText().toString();
                        final File dst = StorageUtil.getRootFile("_mp4parser_mux.mp4");
                        boolean flag = MP4parserHelper.muxVideoAndAudio(videoPath, audioPath, dst.getPath());
                        if (flag)
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    toastShort(dst.getAbsolutePath());
                                    setText(dst.getAbsolutePath() + "\n");
                                }
                            });
                    }
                }).start();
                break;

            case R.id.mutilMux:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String path = StorageUtil.getRootExitPath("video.mp4");
                        final File dst = StorageUtil.getRootFile("_mp4parser_mutilMux.mp4");
                        boolean flag = MP4parserHelper.muxMedia(new String[]{ new String(path),
                                new String(path),
                                new String(path) },
                                dst.getPath());
                        if (flag)
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    toastShort(dst.getAbsolutePath());
                                    setText(dst.getAbsolutePath() + "\n");
                                }
                            });
                    }
                }).start();
                break;
        }
    }

    // ---------------------------------------------------------------------------------

}
