package zovl.zhongguanhua.framework.lib.framework;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    protected final String action = this.getClass().getName();
    protected final String tag = this.getClass().getSimpleName();

    public abstract int getContentView();

    public View getContentView2() {
        return null;
    }

    // ----------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(tag, "onCreate: // ------------------------------------------------------------");
        Log.d(tag, "onCreate: savedInstanceState=" + savedInstanceState);
        AppManager.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        if (getContentView2() != null) {
            setContentView(getContentView2());
        } else {
            setContentView(getContentView());
        }
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        Log.d(tag, "onStart: // ------------------------------------------------------------");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(tag, "onResume: // ------------------------------------------------------------");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Log.d(tag, "onRestart: // ------------------------------------------------------------");
        super.onRestart();
    }

    @Override
    protected void onPause() {
        Log.d(tag, "onPause: // ------------------------------------------------------------");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(tag, "onDestroy: // ------------------------------------------------------------");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d(tag, "onDestroy: // ------------------------------------------------------------");
        AppManager.getInstance().removeActivity(this);
        super.onDestroy();
    }

    // ----------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------

    @Override
    public void onAttachedToWindow() {
        Log.d(tag, "onAttachedToWindow: // ------------------------------------------------------------");
        super.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        Log.d(tag, "onDetachedFromWindow: // ------------------------------------------------------------");
        super.onDetachedFromWindow();
    }

    // ----------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------

    @Override
    public void onBackPressed() {
        Log.d(tag, "onBackPressed: // ------------------------------------------------------------");
        super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(tag, "onConfigurationChanged: // ------------------------------------------------------------");
        Log.e(tag, "onConfigurationChanged: newConfig=" + newConfig);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d(tag, "onNewIntent: // ------------------------------------------------------------");
        Log.e(tag, "onNewIntent: intent=" + intent);
        super.onNewIntent(intent);
    }
}
