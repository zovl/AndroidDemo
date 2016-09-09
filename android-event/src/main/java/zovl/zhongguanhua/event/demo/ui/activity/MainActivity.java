package zovl.zhongguanhua.event.demo.ui.activity;

import android.view.View;

import butterknife.OnClick;
import zovl.zhongguanhua.event.demo.R;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;

public class MainActivity extends TBaseActivity {

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @OnClick({R.id.eventDispatch})
    public void onClick(View view) {

        setText("主页");
        setTitle(getTitle() + "#");

        switch (view.getId()) {

            case R.id.eventDispatch:
                startActivity(EventDispatchActivity.class);
                break;
        }
    }
}
