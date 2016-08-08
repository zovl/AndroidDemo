package zovl.zhongguanhua.media.demo.ui.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.framework.lib.utils.StorageUtil;
import zovl.zhongguanhua.media.demo.R;
import zovl.zhongguanhua.media.demo.logic.ExtractorHelper;

public class ExtractorActivity extends TBaseActivity {

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

    @Override
    public int getContentView() {
        return R.layout.activity_extractor;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String videoPath = StorageUtil.getRootExitPath("video.mp4");
        final String audioPath = StorageUtil.getRootExitPath("honor.mp3");
        this.path.setText(videoPath);
    }

    @OnClick(R.id.extract)
    public void onClick() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                ExtractorHelper.extract(path.getText().toString());
            }
        }).start();
    }

    // ---------------------------------------------------------------------------------
}
