package zovl.zhongguanhua.component.demo.ui.activity;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.component.demo.R;
import zovl.zhongguanhua.framework.lib.framework.AppManager;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;

public class IIActivity extends TBaseActivity {

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
        return R.layout.activity_ii;
    }

    @OnClick({R.id.isFinishing,
            R.id.finish,
            R.id.finishAfterTransition,
            R.id.finishAndRemoveTask,
            R.id.finishAffinity,

            R.id.moveTaskToBack,

            R.id.startLockTask,
            R.id.stopLockTask})
    public void onClick(View view) {

        setTitle(getTitle() + "#");

        MainActivity mainActivity = (MainActivity) AppManager.getInstance().getActivity(MainActivity.class);
        IActivity iActivity = (IActivity) AppManager.getInstance().getActivity(IActivity.class);

        switch (view.getId()) {

            case R.id.isFinishing:
                isFinishing(this);
                setText();
                break;

            case R.id.finish:
                finish(this);
                setText();
                break;

            case R.id.finishAfterTransition:
                finishAfterTransition(this);
                setText();
                break;

            case R.id.finishAndRemoveTask:
                finishAndRemoveTask(mainActivity);
                setText();
                break;

            case R.id.finishAffinity:
                finishAffinity(this);
                setText();
                break;

            case R.id.moveTaskToBack:
                moveTaskToBack(iActivity);
                setText();
                break;

            case R.id.startLockTask:
                startLockTask(mainActivity);
                setText();
                break;

            case R.id.stopLockTask:
                stopLockTask(mainActivity);
                setText();
                break;
        }
    }

    // ---------------------------------------------------------------------------------

    public void isFinishing(Activity activity) {
        log("isFinishing: // --------------------------------------------------------");
        log("isFinishing: isFinishing=" + activity.isFinishing());
    }

    public void finish(Activity activity) {
        log("finish: // --------------------------------------------------------");
        activity.finish();
    }

    public void finishAfterTransition(Activity activity) {
        log("finishAfterTransition: // --------------------------------------------------------");
        activity.finishAfterTransition();
    }

    public void finishAndRemoveTask(Activity activity) {
        log("finishAndRemoveTask: // --------------------------------------------------------");
        activity.finishAndRemoveTask();
    }

    public void finishAffinity(Activity activity) {
        log("finishAffinity: // --------------------------------------------------------");
        activity.finishAffinity();
    }

    private void moveTaskToBack(Activity activity) {
        log("moveTaskToBack: // --------------------------------------------------------");
        activity.moveTaskToBack(true);
    }

    // ---------------------------------------------------------------------------------

    private void startLockTask(Activity activity) {
        log("startLockTask: // --------------------------------------------------------");
        activity.startLockTask();
    }

    private void stopLockTask(Activity activity) {
        log("stopLockTask: // --------------------------------------------------------");
        activity.stopLockTask();
    }
}
