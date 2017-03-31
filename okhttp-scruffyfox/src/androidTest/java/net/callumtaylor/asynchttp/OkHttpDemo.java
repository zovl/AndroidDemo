package net.callumtaylor.asynchttp;

import android.test.AndroidTestCase;
import android.util.Log;

import com.google.gson.JsonElement;

import net.callumtaylor.asynchttp.response.JSONObjectResponseHandler;
import net.callumtaylor.asynchttp.response.StringResponseHandler;

public class OkHttpDemo extends AndroidTestCase {

    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGet() throws Exception {
        Log.d(TAG, "testGet: ");

        SyncHttpClient<String> client = new SyncHttpClient<>("http://httpbin.org");
        String string  = client.get("get");
        Log.d(TAG, "testGet: string=" + string);
    }
}