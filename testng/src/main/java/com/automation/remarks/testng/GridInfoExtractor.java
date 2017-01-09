package com.automation.remarks.testng;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

/**
 * Created by sergey on 09.01.17.
 */
public class GridInfoExtractor {

    public static String getNodeIp(URL hubUrl, String sessionId) throws IOException {
        final String hubIp = hubUrl.getHost();
        final int hubPort = hubUrl.getPort();
        HttpHost host = new HttpHost(hubIp, hubPort);
        DefaultHttpClient client = new DefaultHttpClient();
        URL testSessionApi = new URL("http://" + hubIp + ":" + hubPort + "/grid/api/testsession?session=" + sessionId);
        BasicHttpEntityEnclosingRequest r = new
                BasicHttpEntityEnclosingRequest("POST", testSessionApi.toExternalForm());
        HttpResponse response = client.execute(host, r);
        JSONObject object = new JSONObject(EntityUtils.toString(response.getEntity()));
        return String.valueOf(object.get("proxyId"));
    }

}
