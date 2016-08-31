package com.automation.remarks.video.recorder.ffmpeg;

/**
 * Created by sepi on 31.08.16.
 */
public class MacFFmpegRecorder extends FFMpegRecorder {
    @Override
    public void start() {
        String display = "1:";
        String recorder = "avfoundation";
        getFfmpegWrapper().startFFmpeg(display, recorder, 24, "-vsync", "2");
    }
}
