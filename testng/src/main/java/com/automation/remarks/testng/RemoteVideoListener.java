package com.automation.remarks.testng;

import com.automation.remarks.video.VideoConfiguration;
import com.automation.remarks.video.annotations.Video;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.lang.reflect.Method;
import java.util.logging.Logger;

/**
 * Created by sergey on 12.05.16.
 */
public class RemoteVideoListener implements ITestListener {

    private static final Logger LOGGER = Logger.getLogger(RemoteVideoListener.class.getName());

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getTestName();
        Video video = MethodUtils.getVideoAnnotation(result.getMethod());
        if (video != null && video.enabled()) {
            String url = VideoConfiguration.REMOTE + "/grid/admin/Video/start/" + testName;
            sendRecordingRequest(url);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {

    }

    @Override
    public void onTestFailure(ITestResult result) {

    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }

    private void sendRecordingRequest(final String url) {
        CloseableHttpResponse response = null;
        try (final CloseableHttpClient client = HttpClientBuilder.create().build()) {
            final HttpGet get = new HttpGet(url);
            response = client.execute(get);
            LOGGER.info("Response: " + response);
        } catch (Exception ex) {
            LOGGER.severe("Unable to send recording request to node: " + ex);
        } finally {
            HttpClientUtils.closeQuietly(response);
        }
    }
}
