package com.automation.remarks.video.recorder.ffmpeg;

/**
 * Created by sepi on 31.08.16.
 */
public class WindowsFFmpegRecorder extends FFMpegRecorder {

    @Override
    public void start() {
        String display = "desktop";
        String recorder = "gdigrab";
        getFfmpegWrapper().startFFmpeg(display, recorder, 24);
    }
}
