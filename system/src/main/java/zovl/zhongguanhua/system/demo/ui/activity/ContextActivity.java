package zovl.zhongguanhua.system.demo.ui.activity;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.system.demo.R;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;

public class ContextActivity extends TBaseActivity {

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

    public static final String TAG = ContextActivity.class.getSimpleName();

    @Override
    public int getContentView() {
        return R.layout.activity_context;
    }

    @OnClick({R.id.context})
    public void onClick(View view) {

        setTitle(getTitle() + "#");

        switch (view.getId()) {

            case R.id.context:
                context(this);
                setText();
                break;
        }
    }

    // ---------------------------------------------------------------------------------

    public void context(Context context) {

        log("#########################################");

        log("context=" + context);
        log("applicationContext=" + context.getApplicationContext());

        log("packageCodePath=" + context.getPackageCodePath());
        log("packageResourcePath=" + context.getPackageResourcePath());

        log("packageName=" + context.getPackageName());

        log("theme=" + context.getTheme());
        log("wallpaper=" + context.getWallpaper());

        String[] databases = context.databaseList();
        for (String database : databases) {
            log("database=" + database);
        }

        String[] files = context.fileList();
        for (String file : files) {
            log("file=" + file);
        }

        ApplicationInfo applicationInfo = context.getApplicationInfo();
        log("applicationInfo=" + applicationInfo);
    }
}
