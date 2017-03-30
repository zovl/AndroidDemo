package zovl.zhongguanhua.compenent.demo.service;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import zovl.zhongguanhua.compenent.demo.activity.ServiceActivity;
import zovl.zhongguanhua.framework.lib.framework.BaseService;

public class MessengerService extends BaseService {

    public static final String TAG = MessengerService.class.getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: intent=" + intent);
        Messenger messenger = new Messenger(handler);
        IBinder binder = messenger.getBinder();
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: intent=" + intent);
        return super.onUnbind(intent);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "handleMessage: 收到信息--" + ((Bundle) msg.obj).getString("msg"));
            ServiceActivity.printProcess(tag, MessengerService.this);
            try {
                Message newMsg = new Message();
                String s = "reply..";
                Bundle b = new Bundle();
                b.putString("msg", s);
                newMsg.obj = b;
                msg.replyTo.send(newMsg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };
}
