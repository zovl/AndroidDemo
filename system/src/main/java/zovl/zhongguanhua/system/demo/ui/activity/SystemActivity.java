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

    @Bind(R.id.exitText)
    TextView exitText;

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
                system();
                break;

            // -------------------------------

            case R.id.properties:
                properties();
                propertyNames();
                stringPropertyNames();
                list();
                break;

            // -------------------------------

            case R.id.gc:
                gc();
                break;

            case R.id.runFinalization:
                runFinalization();
                break;

            // -------------------------------

            case R.id.exit:
                exit(Integer.valueOf(exitText.getText().toString()));
                break;

            // -------------------------------

            case R.id.identityHashCode:
                identityHashCode(new Object());
                break;

            // -------------------------------

            case R.id.arraycopy:
                arraycopyString();
                break;
        }
        setText();
    }

    // ---------------------------------------------------------------------------------

    private void system() {

        log("system: // --------------------------------------------------------");

        Map<String, String> env = System.getenv();//获取系统所有环境变量
        for (String  name: env.keySet()) {
            String value = env.get(name);
            log("system: env/name=" + name);
            log("system: env/value=" + value);
        }
        String JAVA_HOME = System.getenv("JAVA_HOME");//获取指定环境变量
        log("system: env/JAVA_HOME=" + JAVA_HOME);

        long currentTimeMillis = System.currentTimeMillis();
        long nanoTime = System.nanoTime();
        log("system: currentTimeMillis=" + currentTimeMillis);
        log("system: nanoTime=" + nanoTime);

        long currentThreadTimeMillis = SystemClock.currentThreadTimeMillis();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos();
        long uptimeMillis = SystemClock.uptimeMillis();
        log("system: currentThreadTimeMillis=" + currentThreadTimeMillis);
        log("system: elapsedRealtime=" + elapsedRealtime);
        log("system: elapsedRealtimeNanos=" + elapsedRealtimeNanos);
        log("system: uptimeMillis=" + uptimeMillis);
    }

    // ---------------------------------------------------------------------------------

    private void properties() {

        log("properties: // --------------------------------------------------------");

        Properties properties = System.getProperties();//获取所有系统属性

        String os_name = properties.getProperty("os.name");//获取指定环境变量
        log("system: os_name=" + os_name);
    }

    private void propertyNames() {

        log("propertyNames: // --------------------------------------------------------");

        Properties properties = System.getProperties();//获取所有系统属性

        Enumeration enumeration = properties.propertyNames();//得到配置文件的名字
        while(enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            String value = properties.getProperty(key);
            log("propertyNames: key=" + key);
            log("propertyNames: value=" + value);
        }
    }

    private void stringPropertyNames() {

        log("stringPropertyNames: // --------------------------------------------------------");

        Properties properties = System.getProperties();//获取所有系统属性

        Set<String> names = properties.stringPropertyNames();//得到配置文件的名字
        Iterator<String> it = names.iterator();
        while(it.hasNext()) {
            String name = it.next();
            log("stringPropertyNames: name=" + name);
        }
    }

    private void list() {

        log("list: // --------------------------------------------------------");

        Properties properties = System.getProperties();//获取所有系统属性

        properties.list(System.out);
    }

    private void load() {

//        System.getProperties().load(null);
//        System.getProperties().loadFromXML(null);
//        System.getProperties().store(null);
//        System.getProperties().storeToXML(null);
    }

    // ---------------------------------------------------------------------------------

    private void gc() {

        log("gc: // --------------------------------------------------------");

        System.gc();
    }

    private void runFinalization() {

        log("runFinalization: // --------------------------------------------------------");

        System.runFinalization();
    }

    private void exit(int code) {

        log("exit: // --------------------------------------------------------");

        System.exit(code);
    }

    private int identityHashCode(Object o) {

        log("identityHashCode: // --------------------------------------------------------");

        int hashCode = System.identityHashCode(o);
        log("identityHashCode: hashCode=" + hashCode);
        return hashCode;
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

    // ---------------------------------------------------------------------------------

    private void getSecurityManager() {
        System.getSecurityManager();
        System.setSecurityManager(null);
    }

    // ---------------------------------------------------------------------------------

    private void setCurrentTimeMillis() {

        SystemClock.setCurrentTimeMillis(100000l);
        SystemClock.sleep(1000l);

    }
}
