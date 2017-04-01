package zovl.zhongguanhua.okhttpscruffyfox.demo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import net.callumtaylor.asynchttp.AsyncHttpClient;
import net.callumtaylor.asynchttp.SyncHttpClient;
import net.callumtaylor.asynchttp.obj.ConnectionInfo;
import net.callumtaylor.asynchttp.obj.NameValuePair;
import net.callumtaylor.asynchttp.response.StringResponseHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import zovl.zhongguanhua.okhttpscruffyfox.demo.R;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        new Thread(new Runnable() {
            @Override
            public void run() {

                // syncGet();
                // syncPoatForm();
                syncPoatPart();
            }
        }).start();*/

        asyncGet();
    }

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    private void syncGet() {
        SyncHttpClient<String> client = new SyncHttpClient<>("http://httpbin.org");

        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new NameValuePair("parama", "value"));

        Headers headers = Headers.of("header", "value");

        String string = client.get("get", pairs, headers, new StringResponseHandler());
        Log.d(TAG, "syncGet: string=" + string);

        ConnectionInfo info = client.getConnectionInfo();
        connectionInfo(info);

        client.cancel();
    }

    private void syncPoatForm() {
        SyncHttpClient<String> client = new SyncHttpClient<>("http://httpbin.org");

        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("username", "Jake")
                .addFormDataPart("age", "24")
                .addFormDataPart("gender", "true")
                .build();

        String string = client.post("post", requestBody, new StringResponseHandler());
        Log.d(TAG, "syncGet: string=" + string);

        ConnectionInfo info = client.getConnectionInfo();
        connectionInfo(info);

        client.cancel();
    }

    private void syncPoatPart() {
        SyncHttpClient<String> client = new SyncHttpClient<>("http://httpbin.org");

        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("file", "file", RequestBody.create(MediaType.parse("application/json"), "{\n" +
                        "    \"username\": \"May\",\n" +
                        "    \"age\": 24,\n" +
                        "    \"genser\": false,\n" +
                        "}"))
                .build();

        String string = client.post("post", requestBody, new StringResponseHandler());
        Log.d(TAG, "syncGet: string=" + string);

        ConnectionInfo info = client.getConnectionInfo();
        connectionInfo(info);

        client.cancel();
    }

    private void asyncGet() {
        AsyncHttpClient client = new AsyncHttpClient("http://httpbin.org");

        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new NameValuePair("parama", "value"));

        Headers headers = Headers.of("header", "value");

        client.get("get", pairs, headers, new StringResponseHandler() {

            @Override
            public void onSuccess() {
                super.onSuccess();

                Log.d(TAG, "onFinish: content" + getContent());
                connectionInfo(getConnectionInfo());
            }

            @Override
            public void onFinish() {
                super.onFinish();

                Log.d(TAG, "onFinish: content" + getContent());
                connectionInfo(getConnectionInfo());
            }
        });

        client.cancel();
    }

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    private void connectionInfo(ConnectionInfo connectionInfo) {
        Log.d(TAG, "---------->" );

        Log.d(TAG, "connectionInfo: " + connectionInfo.toString());
        Log.d(TAG, "connectionInfo: connectionUrl=" + connectionInfo.connectionUrl);
        Log.d(TAG, "connectionInfo: connectionLength=" + connectionInfo.connectionLength);
        Log.d(TAG, "connectionInfo: connectionTime=" + connectionInfo.connectionTime);

        Log.d(TAG, "connectionInfo: requestMethod=" + connectionInfo.requestMethod);
        Map<String, List<String>> requestHeaders = connectionInfo.requestHeaders.toMultimap();
        if (requestHeaders != null) {
            Set<String> keys = requestHeaders.keySet();
            for (String key : keys) {
                List<String> values = requestHeaders.get(key);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < values.size(); i++) {
                    if (i == values.size() -1) {
                        sb.append(values);
                    } else {
                        sb.append(values + " ");
                    }
                }
                Log.d(TAG, "connectionInfo: requestHeader/" + key + "=" + sb.toString());
            }
        }

        Log.d(TAG, "connectionInfo: responseCode=" + connectionInfo.responseCode);
        Log.d(TAG, "connectionInfo: responseLength=" + connectionInfo.responseLength);
        Log.d(TAG, "connectionInfo: responseTime=" + connectionInfo.responseTime);
        Map<String, List<String>> responseHeaders = connectionInfo.responseHeaders.toMultimap();
        if (responseHeaders != null) {
            Set<String> keys = responseHeaders.keySet();
            for (String key : keys) {
                List<String> values = responseHeaders.get(key);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < values.size(); i++) {
                    if (i == values.size() -1) {
                        sb.append(values);
                    } else {
                        sb.append(values + " ");
                    }
                }
                Log.d(TAG, "connectionInfo: responseHeaders/" + key + "=" + sb.toString());
            }
        }
    }
}
