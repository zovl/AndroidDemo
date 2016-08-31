package zovl.zhongguanhua.glrecorder.demo.ui.activity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.View;

import com.research.GLRecorder.GLRecorder;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.framework.lib.utils.StorageUtil;
import zovl.zhongguanhua.glrecorder.demo.R;

public class
MainActivity extends TBaseActivity {

    @Bind(R.id.glSurfaceView)
    GLSurfaceView glSurfaceView;
    SurfaceHolder surfaceHolder;

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glSurfaceView.setEGLConfigChooser(GLRecorder.getEGLConfigChooser());
        glSurfaceView.setRenderer(renderer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }

    @OnClick({R.id.start,
            R.id.stop})
    public void onClick(View view) {

        setTitle(getTitle() + "#");

        switch (view.getId()) {

            case R.id.start:
                GLRecorder.beginDraw();
                GLRecorder.startRecording();
                break;

            case R.id.stop:
                GLRecorder.stopRecording();
                GLRecorder.endDraw();
                break;
        }
    }

    private GLSurfaceView.Renderer renderer = new GLSurfaceView.Renderer() {

        EGLConfig mEGLConfig;

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            mEGLConfig = config;
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLRecorder.init(width, height, mEGLConfig);
            GLRecorder.setRecordOutputFile(StorageUtil.getRootFile("glrecord.mp4").getPath());
        }

        @Override
        public void onDrawFrame(GL10 gl) {}
    };
}
