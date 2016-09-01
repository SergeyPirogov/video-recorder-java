package com.automation.remarks.video.recorder.ffmpeg;

import com.automation.remarks.video.recorder.VideoRecorder;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * Created by sepi on 19.07.16.
 */
public abstract class FFMpegRecorder extends VideoRecorder {

    private FFmpegWrapper ffmpegWrapper;

    public FFMpegRecorder() {
        this.ffmpegWrapper = new FFmpegWrapper();
    }

    public FFmpegWrapper getFfmpegWrapper(){
        return ffmpegWrapper;
    }

    @Override
    public File stopAndSave(final String filename) {
        File file = getFfmpegWrapper().stopFFmpegAndSave(filename);
        setLastVideo(file);
        return file;
    }
}
