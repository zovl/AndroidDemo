package zovl.zhongguanhua.system.demo.compenent.recever;

import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

import zovl.zhongguanhua.framework.lib.framework.BaseReceiver;
import zovl.zhongguanhua.system.demo.logic.BatteryHelper;

public class BatteryReceiver extends BaseReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        BatteryHelper.printIntent(intent);
    }
}
