package zovl.zhongguanhua.compenent.demo.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.compenent.demo.R;
import zovl.zhongguanhua.compenent.demo.entity.Msg;
import zovl.zhongguanhua.compenent.demo.service.MessengerService;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;

public class MessengerActivity extends TBaseActivity {

    public static final String TAG = MessengerActivity.class.getSimpleName();

    @Bind(R.id.msg)
    EditText msg;

    public String getMsg() {
        if (msg.getText() == null)
            return "";
        return msg.getText().toString();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_messenger;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindService(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindService(this);
    }

    @OnClick({R.id.sendMessage})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.sendMessage:
                sendMessage(getMsg());
                break;
        }
    }

    // ---------------------------------------------------------------------------------

    private Messenger messenger;
    private Messenger replyMessenger;

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: name=" + name);
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: name=" + name);
            Log.d(TAG, "onServiceConnected: binder=" + service);
            messenger = new Messenger(service);
        }
    };

    private void bindService(Context context) {
        replyMessenger = new Messenger(replyHanlder);
        Intent intent = new Intent();
        intent.setClass(context, MessengerService.class);
        context.bindService(intent, connection, BIND_AUTO_CREATE);
    }

    private void unbindService(Context context) {
        if (connection != null) {
            context.unbindService(connection);
        }
    }

    public void sendMessage(String s) {
        Message msg = new Message();
        Bundle b = new Bundle();
        b.putString("msg", s);
        Msg m = new Msg();
        m.setMsg(s);
        msg.obj = b;
        msg.replyTo = replyMessenger;
        try {
            messenger.send(msg);
            Log.d(TAG, "sendMessage: 发送消息--" + s);
            ServiceActivity.printProcess(tag, this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private Handler replyHanlder = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "handleMessage: 收到信息--" + ((Bundle) msg.obj).getString("msg"));
            ServiceActivity.printProcess(tag, MessengerActivity.this);
        }
    };
}
