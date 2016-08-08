package zovl.zhongguanhua.component.demo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.component.demo.R;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.component.demo.ui.activity.IIActivity;
import zovl.zhongguanhua.component.demo.ui.activity.IIIActivity;

public class IActivity extends TBaseActivity {

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
        return R.layout.activity_i;
    }

    @OnClick({R.id.startActivity,
            R.id.startActivities})
    public void onClick(View view) {

        setTitle(getTitle() + "#");

        switch (view.getId()) {

            case R.id.startActivity:
                startActivity(this);
                setText();
                break;

            case R.id.startActivities:
                startActivities(this);
                setText();
                break;
        }
    }

    // ---------------------------------------------------------------------------------

    private void startActivity(Activity activity) {

        log("startActivity: // --------------------------------------------------------");

        Intent intent = new Intent();
        intent.setClass(this, IIActivity.class);
        activity.startActivity(intent);
    }

    private void startActivities(Activity activity) {

        log("startActivities: // --------------------------------------------------------");

        Intent a = new Intent();
        a.setClass(this, IIActivity.class);

        Intent b = new Intent();
        b.setClass(this, IIIActivity.class);

        activity.startActivities(new Intent[]{ a, b });
    }
}
