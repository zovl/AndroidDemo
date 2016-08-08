package zovl.zhongguanhua.thread.demo.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.thread.demo.R;

public class InfoActivity extends TBaseActivity {

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

    public static final String TAG = InfoActivity.class.getSimpleName();

    @Override
    public int getContentView() {
        return R.layout.activity_info;
    }

    @OnClick({R.id.context})
    public void onClick(View view) {

        setTitle(getTitle() + "#");

        switch (view.getId()) {

            case R.id.context:
                setText();
                break;
        }
    }

    // ---------------------------------------------------------------------------------
}
