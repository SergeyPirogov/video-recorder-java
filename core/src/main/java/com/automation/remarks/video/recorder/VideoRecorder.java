package com.automation.remarks.video.recorder;

import com.automation.remarks.video.recorder.IVideoRecorder;
import com.automation.remarks.video.recorder.VideoConfiguration;

import java.io.File;

/**
 * Created by sepi on 19.07.16.
 */
public abstract class VideoRecorder implements IVideoRecorder {
    public static VideoConfiguration conf() {
        return new VideoConfiguration();
    }

    private static File lastVideo;

    protected void setLastVideo(File video){
        lastVideo = video;
    }

    public static File getLastRecording() {
        return lastVideo;
    }
}
