package zovl.zhongguanhua.system.demo.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.system.demo.R;
import zovl.zhongguanhua.system.demo.logic.BatteryHelper;

public class BatteryActivity extends TBaseActivity {

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

    public static final String TAG = BatteryActivity.class.getSimpleName();

    @Override
    public int getContentView() {
        return R.layout.activity_battery;
    }

    @OnClick({R.id.intent,
            R.id.property})
    public void onClick(View view) {

        setTitle(getTitle() + "#");

        switch (view.getId()) {

            case R.id.intent:
                BatteryHelper.intent(this);
                setText();
                break;

            case R.id.property:
                BatteryHelper.property(this);
                setText();
                break;
        }
    }

    // ---------------------------------------------------------------------------------
}
