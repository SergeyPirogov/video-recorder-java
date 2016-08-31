package com.automation.remarks.video.recorder.ffmpeg;

/**
 * Created by sepi on 31.08.16.
 */
public class LinuxFFmpegRecorder extends FFMpegRecorder {
    @Override
    public void start() {
        String display = ":0.0";
        String recorder = "x11grab";
        getFfmpegWrapper().startFFmpeg(display, recorder, 24);
    }
}
