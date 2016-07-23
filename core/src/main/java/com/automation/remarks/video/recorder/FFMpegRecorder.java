package com.automation.remarks.video.recorder;

import com.automation.remarks.video.DateUtils;
import com.automation.remarks.video.exception.RecordingException;
import org.apache.commons.lang3.SystemUtils;
import org.apache.log4j.Logger;
import org.zeroturnaround.exec.ProcessExecutor;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

/**
 * Created by sepi on 19.07.16.
 */
public class FFMpegRecorder extends VideoRecorder {

    private static final Logger log = org.apache.log4j.Logger.getLogger(FFMpegRecorder.class);

    private static final String RECORDING_TOOL = "ffmpeg";
    public static final String TEM_FILE_NAME = "temporary";
    private static final String EXTENTION = ".mp4";

    private final File movieFolder = conf().getVideoFolder();
    private File outputFile;

    private CompletableFuture<String> future;


    @Override
    public void start() {
        createVideoFolder();
        Dimension screen = getScreenDimension();
        outputFile = getDestinationFile(TEM_FILE_NAME);
        final String display = SystemUtils.IS_OS_LINUX ? ":0.0" : "desktop";
        final String recorder = SystemUtils.IS_OS_LINUX ? "x11grab" : "gdigrab";

        final String[] commandsSequence = new String[]{
                RECORDING_TOOL, "-y",
                "-video_size", String.format("%sx%s", screen.width, screen.height),
                "-f", recorder,
                "-i", display,
                "-an",
                "-r", "24",
                outputFile.getAbsolutePath()
        };
        this.future = CompletableFuture.supplyAsync(() -> runCommand(commandsSequence));
    }

    @Override
    public File stopAndSave(final String filename) {
        killFFmpeg();

        File destFile = getDestinationFile(filename);
        this.future.whenCompleteAsync((out, errors) -> {
            outputFile.renameTo(destFile);
            log.debug("Recording output log: " + out + (errors != null ? "; ex: " + errors : ""));
            log.debug("Recording finished to: " + destFile.getAbsolutePath());
        });
        setLastVideo(destFile);
        return destFile;
    }

    private String killFFmpeg() {
        final String SEND_CTRL_C_TOOL_NAME = "SendSignalCtrlC.exe";
        return SystemUtils.IS_OS_WINDOWS ?
                runCommand(SEND_CTRL_C_TOOL_NAME, getPidOf(RECORDING_TOOL)) : runCommand("pkill", "-INT", RECORDING_TOOL);
    }

    private File getDestinationFile(final String filename) {
        final String name = filename + "_recording_" + DateUtils.formatDate(new Date(), "yyyy_dd_MM_HH_mm_ss");
        return new File(this.movieFolder + File.separator + name + EXTENTION);

    }

    private String runCommand(final String... args) {
        log.info("Trying to execute the following command: " + Arrays.asList(args));
        try {
            return new ProcessExecutor()
                    .command(args)
                    .readOutput(true)
                    .execute()
                    .outputUTF8();
        } catch (IOException | InterruptedException | TimeoutException e) {
            log.warn("Unable to execute command: " + e);
            throw new RecordingException(e);
        }
    }

    private String getPidOf(final String processName) {
        return runCommand("cmd", "/c", "for /f \"tokens=2\" %i in ('tasklist ^| findstr \"" + processName +
                "\"') do @echo %i").trim();
    }

    private void createVideoFolder() {
        if (!movieFolder.exists()) {
            movieFolder.mkdirs();
        } else if (!movieFolder.isDirectory()) {
            throw new RecordingException("\"" + movieFolder + "\" is not a directory.");
        }
    }

    private Dimension getScreenDimension() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }
}
