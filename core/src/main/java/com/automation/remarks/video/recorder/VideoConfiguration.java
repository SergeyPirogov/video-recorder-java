package com.automation.remarks.video.recorder;

import com.automation.remarks.video.RecordingMode;

import java.io.File;

import static com.automation.remarks.video.RecordingMode.valueOf;
import static java.io.File.separator;
import static java.lang.System.getProperty;

/**
 * Created by sergey on 4/13/16.
 */
public class VideoConfiguration {

    VideoConfiguration() {}

    private static final String FOLDER = getProperty("user.dir") + separator + "video";
    private static String videoFolder = getProperty("video.folder", FOLDER);
    private static boolean videoEnabled = Boolean.valueOf(getProperty("video.enabled", "true"));
    private static RecordingMode mode = valueOf(getProperty("video.mode", "ANNOTATED").toUpperCase());
    private static String remoteUrl = getProperty("remote", "http://localhost:4444");

    public VideoConfiguration withVideoFolder(String dirPath) {
        videoFolder = dirPath;
        return this;
    }

    public VideoConfiguration videoEnabled(boolean condition) {
        VideoConfiguration.videoEnabled = condition;
        return this;
    }

    public VideoConfiguration withRecordMode(RecordingMode mode) {
        VideoConfiguration.mode = mode;
        return this;
    }

    public VideoConfiguration withRemoteGridHubUrl(String url) {
        VideoConfiguration.remoteUrl = url;
        return this;
    }

    public File getVideoFolder() {
        return new File(videoFolder);
    }

    public RecordingMode getMode() {
        return mode;
    }

    public String getRemoteUrl() {
        return remoteUrl;
    }

    public boolean isVideoEnabled() {
        return videoEnabled;
    }
}
