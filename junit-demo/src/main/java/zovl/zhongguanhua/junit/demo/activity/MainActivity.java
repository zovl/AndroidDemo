package zovl.zhongguanhua.junit.demo.activity;

import android.os.Bundle;

import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.junit.demo.R;

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
}
