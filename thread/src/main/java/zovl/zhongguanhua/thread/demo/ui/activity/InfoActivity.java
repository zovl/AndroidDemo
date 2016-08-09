package zovl.zhongguanhua.thread.demo.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.thread.demo.R;
import zovl.zhongguanhua.thread.demo.logic.ThreadUtil;

public class InfoActivity extends TBaseActivity {

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

    public static final String TAG = InfoActivity.class.getSimpleName();

    @Override
    public int getContentView() {
        return R.layout.activity_info;
    }

    @OnClick({R.id.printThread,
            R.id.printGroup,

            R.id.getRoot,

            R.id.getAllGroups,
            R.id.getAllThreads})
    public void onClick(View view) {

        setTitle(getTitle() + "#");

        Thread thread = Thread.currentThread();
        ThreadGroup group = thread.getThreadGroup();
        ThreadGroup root = ThreadUtil.getRoot();

        switch (view.getId()) {

            case R.id.printThread:
                String s = ThreadUtil.printThread(thread);
                setText(s);
                break;

            case R.id.printGroup:
                s = ThreadUtil.printGroup(group);
                setText(s);
                break;

            case R.id.getRoot:
                s = ThreadUtil.printGroup(root);
                setText(s);
                break;

            case R.id.getAllGroups:
                ThreadGroup[] groups = ThreadUtil.getAllGroups();
                s = ThreadUtil.printGroups(groups);
                setText(s);
                break;

            case R.id.getAllThreads:
                Thread[] threads = ThreadUtil.getAllThreads();
                s = ThreadUtil.printThreads(threads);
                setText(s);
                break;
        }
    }

    // ---------------------------------------------------------------------------------
}
