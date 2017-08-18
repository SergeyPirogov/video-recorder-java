package com.automation.remarks.video.recorder.ffmpeg;

/**
 * Created by sepi on 31.08.16.
 */
public class MacFFmpegRecorder extends FFMpegRecorder {
    @Override
    public void start() {
        getFfmpegWrapper().startFFmpeg("-vsync", "2");
    }
}
