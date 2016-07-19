package com.automation.remarks.video.recorder;

/**
 * Created by sepi on 19.07.16.
 */
public abstract class BaseRecorder implements IVideoRecorder{
    public static VideoConfiguration conf() {
        return new VideoConfiguration();
    }
}
