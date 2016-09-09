package zovl.zhongguanhua.system.demo.ui.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.system.demo.R;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.system.demo.logic.SystemHelper;

public class SystemActivity extends TBaseActivity {

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

    @Bind(R.id.exitCode)
    TextView exitCode;

    public Integer getExitCode() {
        try {
            return Integer.valueOf(exitCode.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static final String TAG = SystemActivity.class.getSimpleName();

    @Override
    public int getContentView() {
        return R.layout.activity_system;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.system,

            R.id.properties,

            R.id.gc,
            R.id.runFinalization,

            R.id.exit,

            R.id.identityHashCode,

            R.id.arraycopy})
    public void onClick(View view) {

        setTitle(getTitle() + "#");

        switch (view.getId()) {

            case R.id.system:
                String s = SystemHelper.system();
                setText(s);
                break;

            // -------------------------------

            case R.id.properties:
                StringBuffer buffer = new StringBuffer();
                buffer.append(SystemHelper.properties() + "\n");
                buffer.append(SystemHelper.propertyNames() + "\n");
                buffer.append(SystemHelper.stringPropertyNames() + "\n");
                SystemHelper.list();
                setText(buffer.toString());
                break;

            // -------------------------------

            case R.id.gc:
                System.gc();
                break;

            case R.id.runFinalization:
                System.runFinalization();
                break;

            // -------------------------------

            case R.id.exit:
                System.exit(getExitCode());
                break;

            // -------------------------------

            case R.id.identityHashCode:
                Object o = new Object();
                int hashCode = System.identityHashCode(o);
                setText("hashCode=" + hashCode);
                break;

            // -------------------------------

            case R.id.arraycopy:
                arraycopyString();
                break;
        }
    }

    // ---------------------------------------------------------------------------------

    private void arraycopyString() {

        log("arraycopyString: // --------------------------------------------------------");

        String[] a = new String[]{ "可没", "叫姐姐", "积极", "扣扣", "录屏", "是的" };
        String[] b = new String[a.length];

        System.arraycopy(a, 0, b, 0, a.length);

        for (String s :
                a) {
            log("arraycopyString: a=" + s + "/" + s.hashCode());
        }
        for (String s :
                b) {
            log("arraycopyString: b=" + s + "/" + s.hashCode());
        }
    }

    private void arraycopyInteger() {

        log("arraycopyString: // --------------------------------------------------------");

        String[] a = new String[]{ "可没", "叫姐姐", "积极", "扣扣", "录屏", "是的" };
        String[] b = new String[a.length];

        System.arraycopy(a, 0, b, 0, a.length);

        for (String s :
                a) {
            log("arraycopyString: a=" + s + "/" + s.hashCode());
        }
        for (String s :
                b) {
            log("arraycopyString: b=" + s + "/" + s.hashCode());
        }
    }

    /*
    System.getSecurityManager();
    System.setSecurityManager(null);*/

    /*
    System.getProperties().load(null);
    System.getProperties().loadFromXML(null);
    System.getProperties().store(null);
    System.getProperties().storeToXML(null);*/

    /*
    SystemClock.setCurrentTimeMillis(100000l);
    SystemClock.sleep(1000l);*/
}
