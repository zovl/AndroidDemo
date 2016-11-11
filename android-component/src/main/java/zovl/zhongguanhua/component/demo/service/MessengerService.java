package zovl.zhongguanhua.component.demo.service;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import zovl.zhongguanhua.component.demo.activity.ServiceActivity;
import zovl.zhongguanhua.component.demo.entity.Msg;
import zovl.zhongguanhua.framework.lib.framework.BaseService;

public class MessengerService extends BaseService {

    public static final String TAG = MessengerService.class.getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: intent=" + intent);
        return new Messenger(handler).getBinder();
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
            final Messenger callback = msg.replyTo;
            try {
                String s = "reply..";
                Bundle b = new Bundle();
                b.putString("msg", s);
                Message newMsg = new Message();
                Msg m = new Msg();
                m.setMsg(s);
                newMsg.obj = b;
                callback.send(newMsg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };
}
