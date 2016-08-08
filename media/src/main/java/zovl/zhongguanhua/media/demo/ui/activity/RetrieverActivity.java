package zovl.zhongguanhua.media.demo.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.framework.lib.utils.StorageUtil;
import zovl.zhongguanhua.media.demo.R;
import zovl.zhongguanhua.media.demo.logic.RetrieverHelper;

public class RetrieverActivity extends TBaseActivity {

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

    @Bind(R.id.path)
    EditText path;

    @Bind(R.id.frameTime)
    EditText frameTime;

    RetrieverHelper helper;

    @Override
    public int getContentView() {
        return R.layout.activity_retriever;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String videoPath = StorageUtil.getRootExitPath("video.mp4");
        final String audioPath = StorageUtil.getRootExitPath("honor.mp3");
        this.path.setText(videoPath);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.retrieve,
            R.id.frame})
    public void onClick(View view) {

        helper = new RetrieverHelper(path.getText().toString());

        switch (view.getId()) {

            case R.id.retrieve:

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        helper.retrieve();
                    }
                }).start();
                break;

            case R.id.frame:

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Bitmap bitmap = helper.frameAtTime(Integer.valueOf(frameTime.getText().toString()));

                        load(bitmap);
                    }
                }).start();
                break;
        }
    }

    // ---------------------------------------------------------------------------------
}
