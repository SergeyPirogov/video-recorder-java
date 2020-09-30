package com.automation.remarks.video.recorder.ffmpeg;

import com.automation.remarks.video.DateUtils;
import com.automation.remarks.video.exception.RecordingException;
import org.apache.log4j.Logger;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import static com.automation.remarks.video.SystemUtils.*;
import static com.automation.remarks.video.recorder.VideoRecorder.conf;

/**
 * Created by sepi on 31.08.16.
 */
public class FFmpegWrapper {

    private static final Logger log = org.apache.log4j.Logger.getLogger(FFMpegRecorder.class);

    public static final String RECORDING_TOOL = "ffmpeg";
    private static final String TEM_FILE_NAME = "temporary";
    private static final String EXTENSION = ".mp4";
    private File temporaryFile;
    private Process process;

    public void startFFmpeg(String... args) {
        File videoFolder = new File(conf().folder());
        if (!videoFolder.exists()) {
            videoFolder.mkdirs();
        }

        temporaryFile = getTemporaryFile();
        final String[] commandsSequence = new String[]{
                FFmpegWrapper.RECORDING_TOOL,
                "-y",
                "-video_size", getScreenSize(),
                "-f", conf().ffmpegFormat(),
                "-i", conf().ffmpegDisplay(),
                "-an",
                "-framerate", String.valueOf(conf().frameRate()),
                "-pix_fmt", conf().ffmpegPixelFormat(),
                temporaryFile.getAbsolutePath()
        };

        List<String> command = new ArrayList<>();
        command.addAll(Arrays.asList(commandsSequence));
        command.addAll(Arrays.asList(args));
        process = runCommand(command).getProcess();
    }

    public File stopFFmpegAndSave(String filename) {
        stopFFmpeg();

        File destFile = getResultFile(filename);
        ForkJoinPool.commonPool().execute(() -> {
            try {
                String out = waitForProcessToFinish(process);
                temporaryFile.renameTo(destFile);
                log.debug("Recording output log: " + out);
            } catch (RecordingException e) {
                log.debug("Recording finished with errors:" + e.getMessage());
            }
            log.info("Recording finished to: " + destFile.getAbsolutePath());
        });

        return destFile;
    }

    private void stopFFmpeg() {
        sendCommandToProcess(process, "q\n");
    }

    public File getTemporaryFile() {
        return getFile(TEM_FILE_NAME);
    }

    public File getResultFile(String name) {
        return getFile(name);
    }

    private File getFile(final String filename) {
        File movieFolder = new File(conf().folder());
        final String name = filename + "_recording_" + DateUtils.formatDate(new Date(), "yyyy_dd_MM_HH_mm_ss");
        return new File(movieFolder + File.separator + name + EXTENSION);
    }

    private String getScreenSize() {
        Dimension dimension = conf().screenSize();
        return dimension.width + "x" + dimension.height;
    }
}
