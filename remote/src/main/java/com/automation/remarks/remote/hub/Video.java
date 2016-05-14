package com.automation.remarks.remote.hub;

import com.automation.remarks.remote.node.VideoServlet;
import org.apache.http.HttpStatus;
import org.openqa.grid.internal.ProxySet;
import org.openqa.grid.internal.Registry;
import org.openqa.grid.internal.RemoteProxy;
import org.openqa.grid.web.servlet.RegistryBasedServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.automation.remarks.remote.utils.RestUtils.sendRecordingRequest;
import static com.automation.remarks.remote.utils.RestUtils.updateResponse;

/**
 * Created by Serhii_Pirohov on 11.05.2016.
 */
public class Video extends RegistryBasedServlet {

    public Video() {
        this(null);
    }

    public Video(Registry registry) {
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
        String name = req.getParameter("name");
        try {
            for (RemoteProxy proxy : allProxies) {
                String proxyId = proxy.getId();
                final String url = proxyId +
                        "/extra/" + VideoServlet.class.getSimpleName() + path + "?name=" + name;
                String response = sendRecordingRequest(url);
                updateResponse(resp, HttpStatus.SC_OK, proxyId + " video command " + path + " " + response);
            }
        } catch (Exception ex) {
            updateResponse(resp, HttpStatus.SC_INTERNAL_SERVER_ERROR,
                    "Internal server error occurred while trying to start / stop recording on hub: " + ex);
        }
    }


}
