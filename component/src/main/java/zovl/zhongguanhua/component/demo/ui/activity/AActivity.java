package zovl.zhongguanhua.component.demo.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import zovl.zhongguanhua.component.demo.ui.view.ABCDView;
import zovl.zhongguanhua.component.demo.R;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;

public class AActivity extends TBaseActivity {

    @Bind(R.id.abcd)
    ABCDView view;

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
        return R.layout.activity_a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view.setActivity(this);
    }
}
