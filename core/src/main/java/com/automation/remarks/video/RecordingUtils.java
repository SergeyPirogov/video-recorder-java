package com.automation.remarks.video;

import java.io.File;
import java.net.MalformedURLException;
import java.util.logging.Logger;

/**
 * Created by sergey on 4/21/16.
 */
public class RecordingUtils {

    private static final Logger logger = Logger.getLogger(RecordingUtils.class.getName());

    private RecordingUtils() {
    }

    public static String doVideoProcessing(boolean successfulTest, File video) {
        String filePath = formatVideoFilePath(video);
        if (!successfulTest) {
            logger.info("Video recording on failed test: " + filePath);
            return filePath;
        } else if (video != null) {
            video.delete();
            logger.info("Video deleted on success test: " + filePath);
        }
        return "";
    }

    private static String formatVideoFilePath(File video) {
        if (video == null) {
            return "";
        }
        try {
            return video.toURI().toURL().toExternalForm();
        } catch (MalformedURLException e) {
            return "file://" + video.getAbsolutePath();
        }
    }
}
