package zovl.zhongguanhua.component.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.component.demo.R;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;

public class StartActivity extends TBaseActivity {

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
        return R.layout.activity_start;
    }

    @OnClick({R.id.aStartActivity,
            R.id.bStartActivity,
            R.id.startActivities})
    public void onClick(View view) {

        setTitle(getTitle() + "#");

        switch (view.getId()) {

            case R.id.aStartActivity:
                aStartActivity(this);
                break;

            case R.id.bStartActivity:
                bStartActivity(this);
                break;

            case R.id.startActivities:
                startActivities(this);
                break;
        }
    }

    // ---------------------------------------------------------------------------------

    private void aStartActivity(Activity activity) {

        Intent intent = new Intent();
        intent.setClass(this, AStartActivity.class);
        activity.startActivity(intent);
    }

    private void bStartActivity(Activity activity) {

        Intent intent = new Intent();
        intent.setClass(this, BStartActivity.class);
        activity.startActivity(intent);
    }

    private void startActivities(Activity activity) {

        Intent a = new Intent();
        a.setClass(this, AStartActivity.class);

        Intent b = new Intent();
        b.setClass(this, BStartActivity.class);

        activity.startActivities(new Intent[]{ a, b });
    }
}
