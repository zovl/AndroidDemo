package zovl.zhongguanhua.event.demo.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class ParentLayout extends FrameLayout implements View.OnClickListener,
        View.OnLongClickListener,
        View.OnTouchListener{

    public static final String TAG = ParentLayout.class.getSimpleName();

    public ParentLayout(Context context) {
        this(context, null);
    }

    public ParentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
/*
        setOnClickListener(this);
        setOnLongClickListener(this);
        setOnTouchListener(this);*/
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d("EventDispatch", "========================>dispatch");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        Log.d("EventDispatch", "========================>intercept");
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("EventDispatch", "========================>event");
        return super.onTouchEvent(event);
    }

    // -----------------------------------------------------------------------------------------


    @Override
    public void onClick(View v) {
        Log.d("EventDispatch", "========================>onClick");
    }

    @Override
    public boolean onLongClick(View v) {
        Log.d("EventDispatch", "========================>onLongClick");
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d("EventDispatch", "========================>onTouch");
        return false;
    }
}
