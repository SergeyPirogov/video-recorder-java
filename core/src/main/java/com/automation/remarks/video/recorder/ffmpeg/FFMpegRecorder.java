package com.automation.remarks.video.recorder.ffmpeg;

import com.automation.remarks.video.exception.RecordingException;
import com.automation.remarks.video.recorder.VideoRecorder;
import org.awaitility.constraint.WaitConstraint;
import org.awaitility.core.ConditionTimeoutException;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

/**
 * Created by sepi on 19.07.16.
 */
public abstract class FFMpegRecorder extends VideoRecorder {

    private FFmpegWrapper ffmpegWrapper;

    public FFMpegRecorder() {
        this.ffmpegWrapper = new FFmpegWrapper();
    }

    public FFmpegWrapper getFfmpegWrapper() {
        return ffmpegWrapper;
    }

    @Override
    public File stopAndSave(final String filename) {
        File file = getFfmpegWrapper().stopFFmpegAndSave(filename);
        waitForVideoCompleted(file);
        setLastVideo(file);
        return file;
    }

    private void waitForVideoCompleted(File video) {
        try {
            await().atMost(5, TimeUnit.SECONDS)
                    .pollDelay(1, TimeUnit.SECONDS)
                    .ignoreExceptions()
                    .until(video::exists);
        } catch (ConditionTimeoutException ex) {
            throw new RecordingException("Video was not fulfilled within 5 seconds " + ex);
        }
    }
}
