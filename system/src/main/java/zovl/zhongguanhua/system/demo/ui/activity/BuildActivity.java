package zovl.zhongguanhua.system.demo.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.system.demo.R;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;

public class BuildActivity extends TBaseActivity {

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

    public static final String TAG = BuildActivity.class.getSimpleName();

    @Override
    public int getContentView() {
        return R.layout.activity_build;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.build,
            R.id.version,
            R.id.versionCodes})
    public void onClick(View view) {

        setTitle(getTitle() + "#");

        switch (view.getId()) {

            case R.id.build:
                build();
                setText();
                break;

            case R.id.version:
                version();
                setText();
                break;

            case R.id.versionCodes:
                versionCodes();
                setText();
                break;
        }
    }

    // ---------------------------------------------------------------------------------

    private void build() {

        log("#########################################");

        String board = Build.BOARD;
        String bootloader = Build.BOOTLOADER;
        String brand = Build.BRAND;
        String device = Build.DEVICE;
        String display = Build.DISPLAY;
        String fingerprint = Build.FINGERPRINT;
        String hardware = Build.HARDWARE;
        String host = Build.HOST;
        String id = Build.ID;
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        String product = Build.PRODUCT;
        String serial = Build.SERIAL;
        String tags = Build.TAGS;
        String type = Build.TYPE;
        String user = Build.USER;
        String cpuAbi = Build.CPU_ABI;
        String cpuAbi2 = Build.CPU_ABI2;
        String[] supported32BitAbis = new String[0];
        String[] supported64BitAbis = new String[0];
        String[] supportedAbis = new String[0];
        if (Build.VERSION.SDK_INT >= 21) {
            supported32BitAbis = Build.SUPPORTED_32_BIT_ABIS;
            supported64BitAbis = Build.SUPPORTED_64_BIT_ABIS;
            supportedAbis = Build.SUPPORTED_ABIS;
        }
        String radio = Build.RADIO;
        long time = Build.TIME;
        String radioVersion = Build.getRadioVersion();

        log("board=" + board);
        log("bootloader=" + bootloader);
        log("brand=" + brand);
        log("device=" + device);
        log("display=" + display);
        log("fingerprint=" + fingerprint);
        log("hardware=" + hardware);
        log("host=" + host);
        log("id=" + id);
        log("manufacturer=" + manufacturer);
        log("model=" + model);
        log("product=" + product);
        log("serial=" + serial);
        log("tags=" + tags);
        log("type=" + type);
        log("user=" + user);
        log("cpuAbi=" + cpuAbi);
        log("cpuAbi2=" + cpuAbi2);
        for (String supported32BitAbi : supported32BitAbis) {
            log("supported32BitAbi=" + supported32BitAbi);
        }
        for (String supported64BitAbi : supported64BitAbis) {
            log("supported64BitAbi=" + supported64BitAbi);
        }
        for (String supportedAbi : supportedAbis) {
            log("supportedAbi=" + supportedAbi);
        }
        log("time=" + time);
        log("radioVersion=" + radioVersion);
    }

    private void version() {

        log("#########################################");

        String baseOs = Build.VERSION.BASE_OS;
        String sdk = Build.VERSION.SDK;
        int sdkInt = Build.VERSION.SDK_INT;
        // int previewSdkInt = Build.VERSION.PREVIEW_SDK_INT;

        String codename = Build.VERSION.CODENAME;
        String incremental = Build.VERSION.INCREMENTAL;
        String release = Build.VERSION.RELEASE;
        String securityPatch = Build.VERSION.SECURITY_PATCH;

        log("baseOs=" + baseOs);
        log("sdk=" + sdk);
        log("sdkInt=" + sdkInt);
        // log("previewSdkInt=" + previewSdkInt);

        log("codename=" + codename);
        log("incremental=" + incremental);
        log("release=" + release);
        log("securityPatch=" + securityPatch);
    }

    private void versionCodes() {

        log("#########################################");

        // log("versionCodes=" + Build.VERSION_CODES);
    }
}
