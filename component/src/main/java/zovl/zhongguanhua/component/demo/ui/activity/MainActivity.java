package zovl.zhongguanhua.component.demo.ui.activity;

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

    @OnClick({R.id.iActivity,
            R.id.aActivity,
            R.id.secaActivity,

            R.id.appstartActivity})
    public void onClick(View view) {

        setTitle(getTitle() + "#");

        switch (view.getId()) {

            case R.id.iActivity:
                startActivity(IActivity.class);
                break;

            case R.id.aActivity:
                startActivity(AActivity.class);
                break;

            case R.id.secaActivity:
                startActivity(SecaActivity.class);
                break;

            case R.id.appstartActivity:
                startActivity("zovl.zhongguanhua.system.demo",
                        "zovl.zhongguanhua.system.demo.ui.activity.AppstartActivity");
                break;
        }
    }
}
