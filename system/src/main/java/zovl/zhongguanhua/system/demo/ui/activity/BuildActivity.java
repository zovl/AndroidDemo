package zovl.zhongguanhua.system.demo.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.system.demo.R;
import zovl.zhongguanhua.system.demo.logic.BuildHelper;

public class BuildActivity extends TBaseActivity {

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

    public static final String TAG = BuildActivity.class.getSimpleName();

    @Override
    public int getContentView() {
        return R.layout.activity_build;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.build,
            R.id.version,
            R.id.versionCodes})
    public void onClick(View view) {

        setTitle(getTitle() + "#");

        switch (view.getId()) {

            case R.id.build:
                String s = BuildHelper.build();
                setText(s);
                break;

            case R.id.version:
                s = BuildHelper.version();
                setText(s);
                break;

            case R.id.versionCodes:
                s = BuildHelper.versionCodes();
                setText(s);
                break;
        }
    }

    // ---------------------------------------------------------------------------------
}
