package zovl.zhongguanhua.system.demo.ui.activity;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.system.demo.R;
import zovl.zhongguanhua.system.demo.logic.ContextHelper;

public class ContextActivity extends TBaseActivity {

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

    public static final String TAG = ContextActivity.class.getSimpleName();

    @Override
    public int getContentView() {
        return R.layout.activity_context;
    }

    @OnClick({R.id.context})
    public void onClick(View view) {

        setTitle(getTitle() + "#");

        switch (view.getId()) {

            case R.id.context:
                String s = ContextHelper.context(this);
                setText(s);
                break;
        }
    }

    // ---------------------------------------------------------------------------------
}
