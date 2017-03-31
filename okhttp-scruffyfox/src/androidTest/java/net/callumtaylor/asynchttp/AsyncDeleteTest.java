package net.callumtaylor.asynchttp;

import android.test.AndroidTestCase;

import com.google.gson.JsonElement;

import junit.framework.Assert;

import net.callumtaylor.asynchttp.response.JsonResponseHandler;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Callum Taylor
 */
public class AsyncDeleteTest extends AndroidTestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Tests a basic DELETE request
     *
     * @throws InterruptedException
     */
    public void testDelete() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);

        new AsyncHttpClient("http://httpbin.org/")
                .delete("delete", new JsonResponseHandler() {
                    @Override
                    public void onFinish() {
                        Assert.assertNotNull(getContent());

                        signal.countDown();
                    }
                });

        signal.await(60, TimeUnit.SECONDS);

        if (signal.getCount() != 0) {
            Assert.fail();
        }
    }

    /**
     * Tests response parses correctly from json
     *
     * @throws InterruptedException
     */
    public void testDeleteJson() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);

        new AsyncHttpClient("http://httpbin.org/")
                .delete("delete", new JsonResponseHandler() {
                    @Override
                    public void onFinish() {
                        Assert.assertNotNull(getContent());
                        Assert.assertTrue(getContent() instanceof JsonElement);

                        signal.countDown();
                    }
                });

        signal.await(60, TimeUnit.SECONDS);

        if (signal.getCount() != 0) {
            Assert.fail();
        }
    }

    /**
     * Tests response parses correctly from json
     *
     * @throws InterruptedException
     */
    public void testDeleteJsonBody() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);

        RequestBody postBody = RequestBody.create(MediaType.parse("application/json"), "{\"test\":\"hello world\"}");

        new AsyncHttpClient("http://httpbin.org/")
                .delete("delete", postBody, new JsonResponseHandler() {
                    @Override
                    public void onFinish() {
                        Assert.assertNotNull(getContent());
                        Assert.assertTrue(getContent() instanceof JsonElement);

                        signal.countDown();
                    }
                });

        signal.await(60, TimeUnit.SECONDS);

        if (signal.getCount() != 0) {
            Assert.fail();
        }
    }

    /**
     * Tests 404 response
     *
     * @throws InterruptedException
     */
    public void testDelete404() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);

        new AsyncHttpClient("http://httpbin.org/")
                .delete("status/404", new JsonResponseHandler() {
                    @Override
                    public void onFinish() {
                        Assert.assertNull(getContent());
                        Assert.assertEquals(404, getConnectionInfo().responseCode);

                        signal.countDown();
                    }
                });

        signal.await(60, TimeUnit.SECONDS);

        if (signal.getCount() != 0) {
            Assert.fail();
        }
    }

    /**
     * Tests SSL connection response
     *
     * @throws InterruptedException
     */
    public void testDeleteSslJson() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);

        new AsyncHttpClient("https://httpbin.org/")
                .delete("delete", new JsonResponseHandler() {
                    @Override
                    public void onFinish() {
                        Assert.assertNotNull(getContent());
                        Assert.assertTrue(getContent() instanceof JsonElement);

                        signal.countDown();
                    }
                });

        signal.await(60, TimeUnit.SECONDS);

        if (signal.getCount() != 0) {
            Assert.fail();
        }
    }
}
