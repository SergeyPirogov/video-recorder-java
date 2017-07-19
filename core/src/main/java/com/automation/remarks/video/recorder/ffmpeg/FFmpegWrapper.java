package com.automation.remarks.video.recorder.ffmpeg;

import com.automation.remarks.video.DateUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.log4j.Logger;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
    private CompletableFuture<String> future;
    private File temporaryFile;

    public void startFFmpeg(String display, String recorder, String... args) {
        File videoFolder = new File(conf().folder());
        if (!videoFolder.exists()) {
            videoFolder.mkdirs();
        }

        temporaryFile = getTemporaryFile();
        final String[] commandsSequence = new String[]{
                FFmpegWrapper.RECORDING_TOOL, "-y",
                "-video_size", getScreenSize(),
                "-f", recorder,
                "-i", display,
                "-an",
                "-framerate", String.valueOf(conf().frameRate()),
                temporaryFile.getAbsolutePath()
        };

        List<String> command = new ArrayList<>();
        command.addAll(Arrays.asList(commandsSequence));
        command.addAll(Arrays.asList(args));
        this.future = CompletableFuture.supplyAsync(() -> runCommand(command));
    }

    public File stopFFmpegAndSave(String filename) {
        String killLog = killFFmpeg();
        log.info("Process kill output: " + killLog);

        File destFile = getResultFile(filename);
        this.future.whenCompleteAsync((out, errors) -> {
            temporaryFile.renameTo(destFile);
            log.debug("Recording output log: " + out + (errors != null ? "; ex: " + errors : ""));
            log.info("Recording finished to: " + destFile.getAbsolutePath());
        });
        return destFile;
    }

    private String killFFmpeg() {
        final String SEND_CTRL_C_TOOL_NAME = "SendSignalCtrlC.exe";
        return SystemUtils.IS_OS_WINDOWS ?
                runCommand(SEND_CTRL_C_TOOL_NAME, getPidOf(RECORDING_TOOL)) :
                runCommand("pkill", "-INT", RECORDING_TOOL);
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
