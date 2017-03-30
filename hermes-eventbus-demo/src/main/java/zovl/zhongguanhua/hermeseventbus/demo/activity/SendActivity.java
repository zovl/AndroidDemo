package zovl.zhongguanhua.hermeseventbus.demo.activity;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import xiaofei.library.hermeseventbus.HermesEventBus;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.hermeseventbus.demo.R;
import zovl.zhongguanhua.hermeseventbus.demo.entity.MsgEvent;

public class SendActivity extends TBaseActivity {

    @Bind(R.id.sendMsg)
    TextView sendMsg;

    @Override
    public int getContentView() {
        return R.layout.activity_send;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Send Activity");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }

    private int index;

    @OnClick(R.id.send)
    public void onClick() {
        ++index;
        HermesEventBus.getDefault().post(new MsgEvent(index));
        sendMsg.setText(sendMsg.getText() + "发送" + index + "条消息" + "\n");
    }
}
