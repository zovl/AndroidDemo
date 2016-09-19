package zovl.zhongguanhua.media.demo.ui.activity;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.media.demo.R;

public class VideoViewActivity extends TBaseActivity {

    @Bind(R.id.videoView)
    VideoView videoView;
    @Bind(R.id.path)
    TextView path;

    private MediaController mediaController;

    @Override
    public int getContentView() {
        return R.layout.activity_videoview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        
        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
        mediaController.setAnchorView(videoView);
        videoView.setOnErrorListener(onErrorListener);
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            videoView.setOnInfoListener(onInfoListener);
        }
    }

    private MediaPlayer.OnErrorListener onErrorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            Log.d(tag, "onError: // -------------------------------------------------");
            Log.d(tag, "onError: what=" + what);
            Log.d(tag, "onError: extra=" + extra);
            return false;
        }
    };

    private MediaPlayer.OnInfoListener onInfoListener = new MediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            Log.d(tag, "onInfo: // -------------------------------------------------");
            Log.d(tag, "onInfo: what=" + what);
            Log.d(tag, "onInfo: extra=" + extra);
            return false;
        }
    };

    private MediaPlayer.OnPreparedListener onPreparedListener = new MediaPlayer.OnPreparedListener() {

        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            Log.d(tag, "onPrepared: // -------------------------------------------------");
            mediaPlayer.start();
        }
    };

    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mp) {
            Log.d(tag, "onCompletion: // -------------------------------------------------");
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (videoView != null) {
            videoView.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView != null) {
            videoView.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.stopPlayback();
        }
    }

    @OnClick({R.id.open})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.open:
                selectFile();
                break;
        }
    }

    @Override
    protected void onResultFile(String path) {
        super.onResultFile(path);

        if (Build.VERSION.SDK_INT >= 9) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        videoView.setVideoURI(Uri.parse(path));
        videoView.setOnPreparedListener(onPreparedListener);
        videoView.setOnCompletionListener(onCompletionListener);
    }
}
