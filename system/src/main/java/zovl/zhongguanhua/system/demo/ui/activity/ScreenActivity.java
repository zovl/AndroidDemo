package zovl.zhongguanhua.system.demo.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.system.demo.R;
import zovl.zhongguanhua.system.demo.logic.ScreenUtil;

public class ScreenActivity extends TBaseActivity {

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

    public static final String TAG = ScreenActivity.class.getSimpleName();

    @Override
    public int getContentView() {
        return R.layout.activity_screen;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.getAreaOne,
            R.id.getAreaTwo,
            R.id.getAreaThree})
    public void onClick(View view) {

        setTitle(getTitle() + "#");

        switch (view.getId()) {

            case R.id.getAreaOne:
                ScreenUtil.getAreaOne(this);
                break;

            case R.id.getAreaTwo:
                ScreenUtil.getAreaTwo(this);
                break;

            case R.id.getAreaThree:
                ScreenUtil.getAreaThree(this);
                break;
        }
    }

    // ---------------------------------------------------------------------------------
}
