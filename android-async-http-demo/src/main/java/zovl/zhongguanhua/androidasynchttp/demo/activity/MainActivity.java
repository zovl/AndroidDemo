package zovl.zhongguanhua.androidasynchttp.demo.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.LogInterface;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.util.EncodingUtils;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // doGet();
        doPost();
    }

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    private void doGet() {

        final AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth("username", "Ily");
        client.setBasicAuth("password", "123456");

        client.setConnectTimeout(12000);
        client.setResponseTimeout(12000);
        client.setTimeout(12000);
        client.setMaxConnections(120);
        client.setEnableRedirects(true);
        client.setLoggingEnabled(true);
        client.setLoggingLevel(LogInterface.VERBOSE);
        client.setURLEncodingEnabled(true);

        Map<String, String> map = new HashMap<>();
        map.put("description", "Everything is possible!");
        RequestParams params = new RequestParams(map);
        params.add("username", "Lily");
        params.put("age", 24);
        params.put("gender", true);

        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("accept", "application/json");

        client.get(this, "http://httpbin.org/get", headers, params, handler);

        client(client);
        // client.cancelAllRequests(true);
    }

    private void doPost() {

        final AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        try {
            File directory = Environment.getExternalStorageDirectory();
            File file = new File(directory.getPath() +  "/Download/14-47-41-20140105152924-832653858.jpg");
            params.put("file", new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        client.post("http://httpbin.org/post", params, handler);

        client(client);
        // client.cancelAllRequests(true);
    }

    private final AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            Log.d(TAG, "onSuccess: statusCode=" + statusCode);
            String result = EncodingUtils.getString(responseBody, "UTF-8");
            Log.d(TAG, "onSuccess: result=" + result);
            if (headers != null) {
                for (int i = 0; i < headers.length; i++) {
                    Log.d(TAG, "onSuccess: headers/" + headers[i].getName() + "=" + headers[i].getValue());
                }
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Log.d(TAG, "onFailure: error=" + error);
            Log.d(TAG, "onFailure: statusCode=" + statusCode);
            String result = EncodingUtils.getString(responseBody, "UTF-8");
            Log.d(TAG, "onSuccess: result=" + result);
            if (headers != null) {
                for (int i = 0; i < headers.length; i++) {
                    Log.d(TAG, "onFailure: headers/" + headers[i].getName() + "=" + headers[i].getValue());
                }
            }
        }
    };

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    private void client(AsyncHttpClient client) {
        Log.d(TAG, "---------->" );

        Log.d(TAG, "client: httpClient=" + client.getHttpClient());
        Log.d(TAG, "client: httpContext=" + client.getHttpContext());
        Log.d(TAG, "client: threadPool=" + client.getThreadPool());
        Log.d(TAG, "client: logInterface=" + client.getLogInterface());
        Log.d(TAG, "client: connectTimeout=" + client.getConnectTimeout());
        Log.d(TAG, "client: loggingLevel=" + client.getLoggingLevel());
        Log.d(TAG, "client: maxConnections=" + client.getMaxConnections());
        Log.d(TAG, "client: responseTimeout=" + client.getResponseTimeout());
    }
}
