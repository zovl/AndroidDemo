package zovl.zhongguanhua.component.demo.activity;

import android.os.Bundle;
import android.view.View;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.component.demo.R;

public class MainActivity extends TBaseActivity {

    @Bind(R.id.root)
    View root;

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.app_name);
    }

    @OnClick({R.id.startActivity,
            R.id.aActivity,
            R.id.secaActivity,

            R.id.task,
            R.id.lock,

            R.id.otherActivity,

            R.id.service,
            R.id.receiver,
            R.id.messenger})
    public void onClick(View view) {

        setTitle(getTitle() + "#");

        switch (view.getId()) {

            case R.id.startActivity:
                startActivity(StartActivity.class);
                break;

            case R.id.aActivity:
                startActivity(AActivity.class);
                break;

            case R.id.secaActivity:
                startActivity(SecaActivity.class);
                break;

            case R.id.otherActivity:
                startActivity("zovl.zhongguanhua.system.demo",
                        "zovl.zhongguanhua.system.demo.ui.activity.AppstartActivity");
                break;

            case R.id.task:
                startActivity(FinishAndRemoveActivity.class);
                break;

            case R.id.lock:
                startActivity(LockActivity.class);
                break;

            case R.id.service:
                startActivity(ServiceActivity.class);
                break;

            case R.id.receiver:
                startActivity(ReceiverActivity.class);
                break;

            case R.id.messenger:
                startActivity(MessengerActivity.class);
                break;
        }
    }
}
