package com.automation.remarks.video;

import static java.io.File.separator;
import static java.lang.System.getProperty;

/**
 * Created by sergey on 4/13/16.
 */
public class VideoConfiguration {

    private VideoConfiguration() {
    }

    private static String FOLDER = getProperty("user.dir") + separator + "recordings";
    public static String VIDEO_FOLDER = getProperty("video.folder", FOLDER);
    public static String VIDEO_ENABLED = getProperty("video.enabled", "true");
}
