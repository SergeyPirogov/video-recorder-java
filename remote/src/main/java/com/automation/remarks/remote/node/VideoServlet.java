package com.automation.remarks.remote.node;

import com.automation.remarks.video.recorder.VideoRecorder;
import org.apache.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import static com.automation.remarks.remote.utils.RestUtils.updateResponse;
import static com.automation.remarks.video.RecordingUtils.doVideoProcessing;

/**
 * Created by Serhii_Pirohov on 10.05.2016.
 */
public class VideoServlet extends HttpServlet {

    private VideoRecorder videoRecorder;

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
                    String folder = req.getParameter("folder");
                    if(folder != null){
                        VideoRecorder.conf().withVideoFolder(folder);
                    }
                    videoRecorder = new VideoRecorder(getFileName(req));
                    videoRecorder.start();
                    updateResponse(resp, HttpStatus.SC_OK, "recording started");
                    break;
                case "/stop":
                    if (videoRecorder == null) {
                        updateResponse(resp, HttpStatus.SC_METHOD_NOT_ALLOWED, "Wrong Action! First, start recording");
                        break;
                    }
                    LinkedList<File> files = videoRecorder.stop();
                    String filePath = doVideoProcessing(isSuccess(req), files);
                    updateResponse(resp, HttpStatus.SC_OK, "recording stopped " + filePath);
                    break;
            }
        } catch (Exception ex) {
            updateResponse(resp, HttpStatus.SC_INTERNAL_SERVER_ERROR,
                    "Internal server error occurred while trying to start / stop recording: " + ex);
        }
    }

    private String getFileName(HttpServletRequest req) {
        String name = req.getParameter("name");
        if (name == null || "null".equalsIgnoreCase(name)) {
            return "video";
        }
        return name;
    }

    private boolean isSuccess(HttpServletRequest req) {
        String result = req.getParameter("result");
        if (result == null) {
            return false;
        }
        return Boolean.valueOf(result);
    }
}
