package zovl.zhongguanhua.thread.demo.activity;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.app_name);
    }

    @OnClick({R.id.info,
            R.id.init,
            R.id.join})
    public void onClick(View view) {

        setTitle(getTitle() + "#");

        switch (view.getId()) {

            case R.id.info:
                startActivity(InfoActivity.class);
                break;

            case R.id.init:
                startActivity(NewActivity.class);
                break;

            case R.id.join:
                startActivity(JoinActivity.class);
                break;
        }
    }
}
