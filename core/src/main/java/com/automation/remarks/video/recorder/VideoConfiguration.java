package com.automation.remarks.video.recorder;

import com.automation.remarks.video.enums.RecorderType;
import com.automation.remarks.video.enums.RecordingMode;
import com.automation.remarks.video.enums.VideoSaveMode;

import java.io.File;

import static com.automation.remarks.video.enums.RecordingMode.valueOf;
import static java.io.File.separator;
import static java.lang.System.getProperty;

/**
 * Created by sergey on 4/13/16.
 */
public class VideoConfiguration {

    VideoConfiguration() {
    }

    private static final String FOLDER = getProperty("user.dir") + separator + "video";
    private static String videoFolder = getProperty("video.folder", FOLDER);
    private static boolean videoEnabled = Boolean.valueOf(getProperty("video.enabled", "true"));
    private static RecordingMode mode = valueOf(getProperty("video.mode", "ANNOTATED").toUpperCase());
    private static String remoteUrl = getProperty("remote.video.hub", "http://localhost:4444");
    private static RecorderType recorderType = RecorderType.valueOf(getProperty("recorder.type", "MONTE"));
    private static VideoSaveMode saveMode = VideoSaveMode.valueOf(getProperty("video.save.mode", "FAILED_ONLY"));

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

    public VideoConfiguration withRecorderType(RecorderType type) {
        recorderType = type;
        return this;
    }

    public VideoConfiguration withVideoSaveMode(VideoSaveMode mode) {
        saveMode = mode;
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

    public static VideoSaveMode saveMode() {
        return saveMode;
    }

    public boolean isVideoEnabled() {
        return videoEnabled;
    }

    public RecorderType getRecorderType() {
        return recorderType;
    }

    public VideoConfiguration withDefaultFolder() {
        videoFolder = FOLDER;
        return this;
    }
}
