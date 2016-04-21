package com.automation.remarks.video;

import java.io.File;

/**
 * Created by sergey on 4/13/16.
 */
public class VideoConfiguration {

    private VideoConfiguration() {
    }

    public static String FOLDER = System.getProperty("video.home", System.getProperty("user.dir"));
    public static String RECORDINGS_FOLDER = System.getProperty("video.folder", "recordings");
    public static String VIDEO_FOLDER = FOLDER + File.separator + RECORDINGS_FOLDER;
    public static String VIDEO_ENABLED = System.getProperty("video.enabled", "true");
}
