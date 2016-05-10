package com.automation.remarks.remote.hub;

import com.automation.remarks.remote.Command;
import org.apache.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Serhii_Pirohov on 10.05.2016.
 */
public class VideoRecordingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();
        try {
            switch (path) {
                case "/start":
                    System.out.println("START!!!");
                    updateResponse(resp, HttpStatus.SC_OK, "Started recording");
                    break;
                case "/stop":
                    System.out.println("STOP!!!");
                    updateResponse(resp, HttpStatus.SC_OK, "Stopped recording");
                    break;
            }
        } catch (Exception ex) {
            updateResponse(resp, HttpStatus.SC_INTERNAL_SERVER_ERROR,
                    "Internal server error occurred while trying to start / stop recording: " + ex);
        }

    }

    private void updateResponse(final HttpServletResponse response, final int status, final String message) throws IOException {
        response.setStatus(status);
        response.getWriter().write(message);
    }
}
