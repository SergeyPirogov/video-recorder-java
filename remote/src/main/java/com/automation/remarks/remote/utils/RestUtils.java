package com.automation.remarks.remote.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Serhii_Pirohov on 11.05.2016.
 */
public class RestUtils {

    private RestUtils() {
    }

    private static final Logger LOGGER = Logger.getLogger(RestUtils.class.getName());

    public static CloseableHttpResponse sendRecordingRequest(final String url) {
        CloseableHttpResponse response = null;
        try (final CloseableHttpClient client = HttpClientBuilder.create().build()) {
            final HttpGet get = new HttpGet(url);
            response = client.execute(get);
            LOGGER.info("Response: " + response);
        } catch (Exception ex) {
            LOGGER.severe("Request: " + ex);
        } finally {
            HttpClientUtils.closeQuietly(response);
        }
        return response;
    }

    public static void updateResponse(final HttpServletResponse response, final int status, final String message) throws IOException {
        response.setStatus(status);
        response.getWriter().write(message);
    }

}
