package com.automation.remarks.video;

import static com.automation.remarks.video.RecordingMode.valueOf;
import static java.io.File.separator;
import static java.lang.System.getProperty;

/**
 * Created by sergey on 4/13/16.
 */
public class VideoConfiguration {

    private VideoConfiguration() {
    }

    private static final String FOLDER = getProperty("user.dir") + separator + "video";
    public static String VIDEO_FOLDER = getProperty("com.automation.remarks.video.folder", FOLDER);
    public static String VIDEO_ENABLED = getProperty("com.automation.remarks.video.enabled", "true");
    public static RecordingMode MODE = valueOf(getProperty("com.automation.remarks.video.mode", "ANNOTATED").toUpperCase());
}
