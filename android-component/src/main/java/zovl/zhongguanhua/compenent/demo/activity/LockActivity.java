package zovl.zhongguanhua.compenent.demo.activity;

import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.compenent.demo.R;
import zovl.zhongguanhua.framework.lib.framework.AppManager;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;

public class LockActivity extends TBaseActivity {

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
        return R.layout.activity_lock;
    }

    @OnClick({R.id.startLockTask,
            R.id.stopLockTask})
    public void onClick(View view) {

        setTitle(getTitle() + "#");

        MainActivity mainActivity = (MainActivity) AppManager.getInstance().getActivity(MainActivity.class);
        StartActivity startActivity = (StartActivity) AppManager.getInstance().getActivity(StartActivity.class);

        switch (view.getId()) {

            case R.id.startLockTask:
                if (Build.VERSION.SDK_INT >= 21) {
                    mainActivity.startLockTask();
                }
                break;

            case R.id.stopLockTask:
                if (Build.VERSION.SDK_INT >= 21) {
                    mainActivity.stopLockTask();
                }
                break;
        }
    }

    // ---------------------------------------------------------------------------------
}
