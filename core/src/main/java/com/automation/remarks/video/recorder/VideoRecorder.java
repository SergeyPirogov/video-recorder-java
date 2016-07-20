package com.automation.remarks.video.recorder;

import java.io.File;

/**
 * Created by sepi on 19.07.16.
 */
public abstract class VideoRecorder implements IVideoRecorder{
    public static VideoConfiguration conf() {
        return new VideoConfiguration();
    }

    private static File lastVideo;

    void setLastVideo(File video){
        lastVideo = video;
    }

    public static File getLastRecording() {
        return lastVideo;
    }
}
