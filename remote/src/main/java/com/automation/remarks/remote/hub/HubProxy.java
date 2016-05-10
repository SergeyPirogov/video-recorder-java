package com.automation.remarks.remote.hub;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.grid.common.RegistrationRequest;
import org.openqa.grid.internal.Registry;
import org.openqa.grid.internal.TestSession;
import org.openqa.grid.selenium.proxy.DefaultRemoteProxy;

import java.util.logging.Logger;

/**
 * Created by Serhii_Pirohov on 10.05.2016.
 */
public class HubProxy extends DefaultRemoteProxy {

    private static final Logger LOGGER = Logger.getLogger(HubProxy.class.getName());

    public HubProxy(RegistrationRequest request, Registry registry) {
        super(request, registry);
    }

    @Override
    public void beforeSession(TestSession session) {
        super.beforeSession(session);
        processRecording(session, "start");
    }

    @Override
    public void afterSession(TestSession session) {
        super.afterSession(session);
        processRecording(session, "stop");
    }

    private void processRecording(final TestSession session, final String command) {
        final String url = "http://" + this.getRemoteHost().getHost() + ":" + this.getRemoteHost().getPort() +
                "/extra/" + VideoRecordingServlet.class.getSimpleName() + "/" + command;
        sendRecordingRequest(url);
    }

    private void sendRecordingRequest(final String url) {
        CloseableHttpResponse response = null;

        try (final CloseableHttpClient client = HttpClientBuilder.create().build()) {
            final HttpGet get = new HttpGet(url);
            response = client.execute(get);
            LOGGER.info("Node response: " + response);
        } catch (Exception ex) {
            LOGGER.severe("Unable to send recording request to node: " + ex);
        } finally {
            HttpClientUtils.closeQuietly(response);
        }
    }

}
