package com.automation.remarks.video;

import java.io.File;
import java.util.logging.Logger;

/**
 * Created by sergey on 4/21/16.
 */
public class RecordingUtils {

    private static final Logger logger = Logger.getLogger(RecordingUtils.class.getName());

    private RecordingUtils() {
    }

    public static String doVideoProcessing(boolean successfulTest, File video) {
        if (video == null) {
            return "Video file is NULL";
        }
        String absolutePath = video.getAbsolutePath();
        if (!successfulTest) {
            logger.info("Video recording on failed test: " + absolutePath);
            return absolutePath;
        } else {
            logger.info("Video deleted on success test: " + absolutePath);
            video.delete();
        }
        return "no recordings on success test";
    }
}
