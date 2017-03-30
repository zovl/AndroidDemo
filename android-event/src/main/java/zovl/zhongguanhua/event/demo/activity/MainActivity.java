package zovl.zhongguanhua.event.demo.activity;

import android.os.Bundle;
import android.view.View;

import butterknife.OnClick;
import zovl.zhongguanhua.event.demo.R;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;

public class MainActivity extends TBaseActivity {

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.app_name);
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
