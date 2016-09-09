package zovl.zhongguanhua.component.demo.ui.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import zovl.zhongguanhua.component.demo.ui.activity.AActivity;
import zovl.zhongguanhua.component.demo.ui.activity.MainActivity;
import zovl.zhongguanhua.component.demo.ui.activity.BActivity;
import zovl.zhongguanhua.component.demo.ui.activity.CActivity;
import zovl.zhongguanhua.component.demo.ui.activity.DActivity;
import zovl.zhongguanhua.component.demo.ui.activity.SecaActivity;
import zovl.zhongguanhua.component.demo.ui.activity.SecbActivity;
import zovl.zhongguanhua.component.demo.ui.activity.SeccActivity;
import zovl.zhongguanhua.component.demo.ui.activity.SecdActivity;
import zovl.zhongguanhua.framework.lib.utils.ActivityHelper;
import zovl.zhongguanhua.component.demo.R;

public class ABCDView extends FrameLayout implements View.OnClickListener {

    public ABCDView(Context context) {
        this(context, null);
    }

    public ABCDView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initialize(context);
    }

    private Activity activity;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    private void initialize(Context context) {
        inflate(context, R.layout.view_abcd, this);

        findViewById(R.id.aActivity).setOnClickListener(this);
        findViewById(R.id.bActivity).setOnClickListener(this);
        findViewById(R.id.cActivity).setOnClickListener(this);
        findViewById(R.id.dActivity).setOnClickListener(this);

        findViewById(R.id.secaActivity).setOnClickListener(this);
        findViewById(R.id.secbActivity).setOnClickListener(this);
        findViewById(R.id.seccActivity).setOnClickListener(this);
        findViewById(R.id.secdActivity).setOnClickListener(this);

        findViewById(R.id.mainActivity).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.aActivity:
                ActivityHelper.startActivity(activity, AActivity.class);
                break;

            case R.id.bActivity:
                ActivityHelper.startActivity(activity, BActivity.class);
                break;

            case R.id.cActivity:
                ActivityHelper.startActivity(activity, CActivity.class);
                break;

            case R.id.dActivity:
                ActivityHelper.startActivity(activity, DActivity.class);
                break;

            // --------------------------------------------------------------

            case R.id.secaActivity:
                ActivityHelper.startActivity(activity, SecaActivity.class);
                break;

            case R.id.secbActivity:
                ActivityHelper.startActivity(activity, SecbActivity.class);
                break;

            case R.id.seccActivity:
                ActivityHelper.startActivity(activity, SeccActivity.class);
                break;

            case R.id.secdActivity:
                ActivityHelper.startActivity(activity, SecdActivity.class);
                break;

            // --------------------------------------------------------------

            case R.id.mainActivity:
                ActivityHelper.startActivity(activity, MainActivity.class);
                break;
        }
    }
}
