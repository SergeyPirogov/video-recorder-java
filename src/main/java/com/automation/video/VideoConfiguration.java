package com.automation.video;

import java.io.File;

/**
 * Created by sergey on 4/13/16.
 */
public class VideoConfiguration {

    private VideoConfiguration() {
    }

    public static final String FOLDER = System.getProperty("video.home", System.getProperty("user.dir"));
    public static final String RECORDINGS_FOLDER = System.getProperty("video.folder", "recordings");
    public static final String VIDEO_FOLDER = FOLDER + File.separator + RECORDINGS_FOLDER;
    public static final String VIDEO_ENABLED = System.getProperty("video.enabled", "true");
}
