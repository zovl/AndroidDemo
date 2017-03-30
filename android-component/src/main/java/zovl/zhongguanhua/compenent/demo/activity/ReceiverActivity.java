package zovl.zhongguanhua.compenent.demo.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.compenent.demo.R;
import zovl.zhongguanhua.compenent.demo.service.ReceiverService;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;

public class ReceiverActivity extends TBaseActivity {

    public static final String TAG = ReceiverActivity.class.getSimpleName();

    @Bind(R.id.msg)
    EditText msg;

    public String getMsg() {
        if (msg.getText() == null)
            return "";
        return msg.getText().toString();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_receiver;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver(this);
        ReceiverService.startService(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this);
        ReceiverService.stopService(this);
    }

    @OnClick({R.id.sendBroadcast})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.sendBroadcast:
                sendBroadcast(this, getMsg());
                break;
        }
    }

    // ---------------------------------------------------------------------------------

    private Receiver receiver;

    private void registerReceiver(Context context) {
        receiver = new Receiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.Receiver");
        registerReceiver(receiver, filter);
        context.registerReceiver(receiver, filter);
        Log.d(tag, "registerReceiver: ");
    }

    private void unregisterReceiver(Context context) {
        if (receiver != null) {
            context.unregisterReceiver(receiver);
            Log.d(tag, "registerReceiver: ");
        }
    }

    public static void sendBroadcast(Context context, String s) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.Receiver");
        intent.putExtra("msg", s);
        context.sendBroadcast(intent);
        Log.d(TAG, "sendBroadcast: msg=" + s);
        Log.d(TAG, "sendBroadcast: ");
    }

    public class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ServiceActivity.printProcess(TAG, context);
            Log.d(tag, "onReceive: context=" + context);
            Log.d(tag, "onReceive: intent=" + intent);
            Log.d(tag, "onReceive: action=" + intent.getAction());
            Log.d(tag, "onReceive: msg=" + intent.getStringExtra("msg"));
        }
    }
}
