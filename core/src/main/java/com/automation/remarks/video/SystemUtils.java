package com.automation.remarks.video;

import com.automation.remarks.video.exception.RecordingException;
import com.automation.remarks.video.recorder.ffmpeg.FFMpegRecorder;
import org.apache.log4j.Logger;
import org.zeroturnaround.exec.ProcessExecutor;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Created by sepi on 31.08.16.
 */
public class SystemUtils {

    private static final Logger log = org.apache.log4j.Logger.getLogger(FFMpegRecorder.class);

    public static String runCommand(final List<String> args) {
        log.info("Trying to execute the following command: " + args);
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

    public static String runCommand(final String... args) {
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

    public static String getPidOf(final String processName) {
        return runCommand("cmd", "/c", "for /f \"tokens=2\" %i in ('tasklist ^| findstr \"" + processName +
                "\"') do @echo %i").trim();
    }

    public static Dimension getSystemScreenDimension() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }
}
