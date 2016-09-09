package zovl.zhongguanhua.event.demo.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import zovl.zhongguanhua.event.demo.R;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;

/**
 * 活动：事件的分发和消费机制
 */
public class EventDispatchActivity extends TBaseActivity {

    @Override
    public int getContentView() {
        return R.layout.activity_eventdispatch;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setText("事件的分发和消费机制");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d("EventDispatch", "============>dispatch");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("EventDispatch", "============>event");
        return super.onTouchEvent(event);
    }
}
