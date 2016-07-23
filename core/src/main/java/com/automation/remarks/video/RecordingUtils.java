package com.automation.remarks.video;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Created by sergey on 4/21/16.
 */
public class RecordingUtils {

    private static final Logger logger = Logger.getLogger(RecordingUtils.class);

    private RecordingUtils() {
    }

    public static String doVideoProcessing(boolean successfulTest, File video) {
        String filePath = formatVideoFilePath(video);
        if (!successfulTest) {
            logger.info("Video recording: " + filePath);
            return filePath;
        } else if (video != null) {
            video.delete();
            logger.info("No video on success test");
        }
        return "";
    }

    private static String formatVideoFilePath(File video) {
        if (video == null) {
            return "";
        }
        String jenkinsReportsUrl = getJenkinsReportsUrl();
        if (!isEmpty(jenkinsReportsUrl)) {
            return jenkinsReportsUrl + getVideoCanonicalPath(video);
        }
        return "file://" + video.getAbsolutePath();
    }

    private static boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    private static String getVideoCanonicalPath(File video) {
        try {
            return video.getCanonicalPath();
        } catch (IOException e) {
            return "";
        }
    }

    private static String getJenkinsReportsUrl() {
        String build_url = System.getProperty("BUILD_URL");
        if (!isEmpty(build_url)) {
            logger.info("Using Jenkins BUILD_URL: " + build_url);
            return build_url + "artifact/";
        } else {
            logger.info("No BUILD_URL variable found. It's not Jenkins.");
            return null;
        }
    }
}
