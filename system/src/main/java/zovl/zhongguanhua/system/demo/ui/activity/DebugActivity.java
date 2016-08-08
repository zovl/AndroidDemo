package zovl.zhongguanhua.system.demo.ui.activity;

import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.system.demo.R;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.framework.lib.utils.FormatUtil;

public class DebugActivity extends TBaseActivity {

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

    public static final String TAG = DebugActivity.class.getSimpleName();

    @Override
    public int getContentView() {
        return R.layout.activity_debug;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.nativeMemory})
    public void onClick(View view) {

        setTitle(getTitle() + "#");

        switch (view.getId()) {

            case R.id.nativeMemory:
                nativeMemory();
                setText();
                break;
        }
    }

    // ---------------------------------------------------------------------------------

    private void nativeMemory() {

        log("nativeMemory: // --------------------------------------------------------");

        long nativeHeapSize = Debug.getNativeHeapSize();
        long nativeHeapAllocatedSize = Debug.getNativeHeapAllocatedSize();
        long nativeHeapFreeSize = Debug.getNativeHeapFreeSize();

        log("nativeHeapSize=" + nativeHeapSize + "byte");
        log("nativeHeapSize=" + FormatUtil.format(nativeHeapSize));
        log("nativeHeapAllocatedSize=" + nativeHeapAllocatedSize + "byte");
        log("nativeHeapAllocatedSize=" + FormatUtil.format(nativeHeapAllocatedSize));
        log("nativeHeapFreeSize=" + nativeHeapFreeSize + "byte");
        log("nativeHeapFreeSize=" + FormatUtil.format(nativeHeapFreeSize));
    }
}
