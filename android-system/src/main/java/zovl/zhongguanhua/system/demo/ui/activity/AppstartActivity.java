package zovl.zhongguanhua.system.demo.ui.activity;

import android.view.View;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.system.demo.R;
import zovl.zhongguanhua.system.demo.ui.service.ActivityManagerService;

public class AppstartActivity extends TBaseActivity {

    @Bind(R.id.root)
    View root;

    @Override
    public int getContentView() {
        return R.layout.activity_appstart;
    }

    @OnClick({R.id.build,
            R.id.system,
            R.id.memory,
            R.id.activityManager,
            R.id.activityService,
            R.id.packageManager,
            R.id.context,
            R.id.debug,
            R.id.screen,
            R.id.intent})
    public void onClick(View view) {

        setTitle(getTitle() + "#");

        switch (view.getId()) {

            case R.id.build:
                startActivity(BuildActivity.class);
                break;

            case R.id.system:
                startActivity(SystemActivity.class);
                break;

            case R.id.memory:
                startActivity(RuntimeActivity.class);
                break;

            case R.id.activityManager:
                startActivity(ActivityManagerActivity.class);
                break;

            case R.id.activityService:
                ActivityManagerService.startService(this);
                break;

            case R.id.packageManager:
                startActivity(PackageManagerActivity.class);
                break;

            case R.id.context:
                startActivity(ContextActivity.class);
                break;

            case R.id.debug:
                startActivity(DebugActivity.class);
                break;

            case R.id.screen:
                startActivity(ScreenActivity.class);
                break;

            case R.id.intent:
                startActivity(BatteryActivity.class);
                break;
        }
    }
}
