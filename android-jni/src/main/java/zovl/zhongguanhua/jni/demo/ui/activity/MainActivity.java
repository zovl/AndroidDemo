package zovl.zhongguanhua.jni.demo.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.jni.demo.R;
import zovl.zhongguanhua.jni.demo.jni.Person;

public class MainActivity extends TBaseActivity {

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

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String info = Person.getInfo("Jonh", 25, true);
        setText("info=" + info);
    }
}
