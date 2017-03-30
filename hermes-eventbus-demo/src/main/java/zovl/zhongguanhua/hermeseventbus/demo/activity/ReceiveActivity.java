package zovl.zhongguanhua.hermeseventbus.demo.activity;

import android.os.Bundle;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import xiaofei.library.hermeseventbus.HermesEventBus;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.hermeseventbus.demo.R;
import zovl.zhongguanhua.hermeseventbus.demo.entity.MsgEvent;

public class ReceiveActivity extends TBaseActivity {

    @Bind(R.id.receiveMsg)
    TextView receiveMsg;

    @Override
    public int getContentView() {
        return R.layout.activity_receive;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HermesEventBus.getDefault().register(this);

        setTitle("Receive Activity");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        HermesEventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void mainThread(MsgEvent event) {
        receiveMsg.setText(receiveMsg.getText() + "接受" + event.index + "条消息" + "\n");
    }
}
