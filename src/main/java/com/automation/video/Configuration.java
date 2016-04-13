package com.automation.video;

import java.io.File;

/**
 * Created by sergey on 4/13/16.
 */
public class Configuration {

    private Configuration() {
    }

    public static final String FOLDER = System.getProperty("video.home", System.getProperty("user.home"));
    public static final String RECORDINGS_FOLDER = System.getProperty("video.folder", "Movies");
    public static final String VIDEO_FOLDER = FOLDER + File.separator + RECORDINGS_FOLDER;
}
