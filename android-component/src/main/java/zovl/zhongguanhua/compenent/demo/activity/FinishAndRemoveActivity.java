package zovl.zhongguanhua.compenent.demo.activity;

import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.compenent.demo.R;
import zovl.zhongguanhua.framework.lib.framework.AppManager;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;

public class FinishAndRemoveActivity extends TBaseActivity {

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
        return R.layout.activity_finishandremove;
    }

    @OnClick({R.id.isFinishing,
            R.id.finish,
            R.id.finishAfterTransition,
            R.id.finishAndRemoveTask,
            R.id.finishAffinity,

            R.id.moveTaskToBack})
    public void onClick(View view) {

        setTitle(getTitle() + "#");

        MainActivity mainActivity = (MainActivity) AppManager.getInstance().getActivity(MainActivity.class);
        StartActivity startActivity = (StartActivity) AppManager.getInstance().getActivity(StartActivity.class);

        switch (view.getId()) {

            case R.id.isFinishing:
                this.isFinishing();
                break;

            case R.id.finish:
                this.finish();
                break;

            case R.id.finishAfterTransition:
                if (Build.VERSION.SDK_INT >= 21) {
                    this.finishAfterTransition();
                }
                break;

            case R.id.finishAndRemoveTask:
                if (Build.VERSION.SDK_INT >= 21) {
                    mainActivity.finishAndRemoveTask();
                }
                break;

            case R.id.finishAffinity:
                this.finishAffinity();
                break;

            case R.id.moveTaskToBack:
                startActivity.moveTaskToBack(false);
                break;
        }
    }

    // ---------------------------------------------------------------------------------
}
