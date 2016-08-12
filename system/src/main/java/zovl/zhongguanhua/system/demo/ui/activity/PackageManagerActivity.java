package zovl.zhongguanhua.system.demo.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.framework.lib.utils.ViewUtil;
import zovl.zhongguanhua.system.demo.R;
import zovl.zhongguanhua.system.demo.logic.PackageHelper;

public class PackageManagerActivity extends TBaseActivity {

    @Bind(R.id.getPackageArchiveInfo_edit)
    EditText paEdit;

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

    public static final String TAG = PackageManagerActivity.class.getSimpleName();

    @Override
    public int getContentView() {
        return R.layout.activity_packagemanager;
    }

    @OnClick({R.id.getInstalledPackages,
            R.id.getPackageInfo,
            R.id.getSystemPackages,
            R.id.getUserPackages,
            R.id.getPackageArchiveInfo,

            R.id.getInstalledApplications,
            R.id.getApplicationInfo,
            R.id.getActivityInfo,
            R.id.getReceiverInfo,
            R.id.getServiceInfo,
            R.id.getProviderInfo,

            R.id.getLaunchIntentForPackage,
            R.id.getLeanbackLaunchIntentForPackage})
    public void onClick(View view) {

        setTitle(getTitle() + "#");

        switch (view.getId()) {

            case R.id.getInstalledPackages:
                ViewUtil.enabled(view, 1200);

                List<PackageInfo> packageInfos = PackageHelper.getInstalledPackages(this);
                StringBuffer buffer = new StringBuffer();
                for (PackageInfo p : packageInfos) {
                    String s = PackageHelper.printPackageInfo(this, p);
                    buffer.append(s);
                }
                setText(buffer.toString());
                break;

            case R.id.getSystemPackages:
                ViewUtil.enabled(view, 1200);

                packageInfos = PackageHelper.getSystemPackages(this);
                buffer = new StringBuffer();
                for (PackageInfo p : packageInfos) {
                    String s = PackageHelper.printPackageInfo(this, p);
                    buffer.append(s);
                }
                setText(buffer.toString());
                break;

            case R.id.getUserPackages:
                ViewUtil.enabled(view, 1200);

                packageInfos = PackageHelper.getUserPackages(this);
                buffer = new StringBuffer();
                for (PackageInfo p : packageInfos) {
                    String s = PackageHelper.printPackageInfo(this, p);
                    buffer.append(s);
                }
                setText(buffer.toString());
                break;

            case R.id.getPackageInfo:

                PackageInfo p = PackageHelper.getPackageInfo(this, getPackageName());
                String s = PackageHelper.printPackageInfo(this, p);
                setText(s);
                break;

            case R.id.getPackageArchiveInfo:

                p = PackageHelper.getPackageArchiveInfo(this, paEdit.getText().toString());
                s = PackageHelper.printPackageInfo(this, p);
                setText(s);
                break;

            // --------------------------------

            case R.id.getInstalledApplications:

                List<ApplicationInfo> applicationInfos = PackageHelper.getInstalledApplications(this);
                buffer = new StringBuffer();
                for (ApplicationInfo a : applicationInfos) {
                    s = PackageHelper.printApplicationInfo(this, a);
                    buffer.append(s);
                }
                setText(buffer.toString());
                break;

            case R.id.getApplicationInfo:

                ApplicationInfo applicationInfo = PackageHelper.getApplicationInfo(this, "com.bbk.appstore");
                s = PackageHelper.printApplicationInfo(this, applicationInfo);
                setText(s);
                break;

            case R.id.getActivityInfo:
                PackageHelper.getActivityInfo(this, PackageManagerActivity.class);
                setText();
                break;

            case R.id.getReceiverInfo:

                ActivityInfo activityInfo = PackageHelper.getReceiverInfo(this, PackageManagerActivity.class);
                s = PackageHelper.printActivityInfo(activityInfo);
                setText(s);
                break;

            case R.id.getServiceInfo:

                ServiceInfo serviceInfo = PackageHelper.getServiceInfo(this, PackageManagerActivity.class);
                s = PackageHelper.printServiceInfo(serviceInfo);
                setText(s);
                break;

            case R.id.getProviderInfo:

                ProviderInfo providerInfo = PackageHelper.getProviderInfo(this, PackageManagerActivity.class);
                s = PackageHelper.printProviderInfo(this, providerInfo);
                setText(s);
                break;

            // --------------------------------

            case R.id.getLaunchIntentForPackage:
                Intent intent = PackageHelper.getLaunchIntentForPackage(this, "com.bbk.appstore");
                startActivity(intent);
                setText(intent.toString());
                break;

            case R.id.getLeanbackLaunchIntentForPackage:
                intent = PackageHelper.getLeanbackLaunchIntentForPackage(this, "com.bbk.appstore");
                startActivity(intent);
                setText(intent.toString());
                break;
        }
    }

    // ---------------------------------------------------------------------------------
}
