package zovl.zhongguanhua.thread.demo.ui.activity;

import android.view.View;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.thread.demo.R;

public class MainActivity extends TBaseActivity {

    @Bind(R.id.root)
    View root;

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @OnClick({R.id.build})
    public void onClick(View view) {

        setTitle(getTitle() + "#");

        switch (view.getId()) {

            case R.id.build:
                startActivity(InfoActivity.class);
                break;
        }
    }
}
