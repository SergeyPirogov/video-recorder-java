package com.automation.remarks.remote.hub;

import com.automation.remarks.remote.node.VideoRecordingServlet;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.grid.internal.ProxySet;
import org.openqa.grid.internal.Registry;
import org.openqa.grid.internal.RemoteProxy;
import org.openqa.grid.web.servlet.RegistryBasedServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Serhii_Pirohov on 11.05.2016.
 */
public class Recording extends RegistryBasedServlet {

    private static final Logger LOGGER = Logger.getLogger(HubProxy.class.getName());

    public Recording() {
        this(null);
    }

    public Recording(Registry registry) {
        super(registry);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProxySet allProxies = getRegistry().getAllProxies();
        String path = req.getPathInfo();
        for (RemoteProxy proxy : allProxies) {
            String proxyId = proxy.getId();
            final String url = proxyId +
                    "/extra/" + VideoRecordingServlet.class.getSimpleName() + path;
            sendRecordingRequest(url);
        }
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
