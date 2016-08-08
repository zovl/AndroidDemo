package zovl.zhongguanhua.component.demo.ui.activity;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.component.demo.R;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;

public class IIIActivity extends TBaseActivity {

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
        return R.layout.activity_iii;
    }

    @OnClick({R.id.button})
    public void onClick(View view) {

        setTitle(getTitle() + "#");

        switch (view.getId()) {

            case R.id.button:
                button(this);
                setText();
                break;
        }
    }

    // ---------------------------------------------------------------------------------

    public void button(Context context) {

        log("finish: // --------------------------------------------------------");

        log("####################################################");
    }
}
