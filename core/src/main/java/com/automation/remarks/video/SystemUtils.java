package com.automation.remarks.video;

import com.automation.remarks.video.enums.OsType;
import com.automation.remarks.video.exception.RecordingException;
import com.automation.remarks.video.recorder.ffmpeg.FFMpegRecorder;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.StartedProcess;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import static org.apache.commons.lang3.SystemUtils.IS_OS_MAC;
import static org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS;

/**
 * Created by sepi on 31.08.16.
 */
public class SystemUtils {

    private static final Logger log = org.apache.log4j.Logger.getLogger(FFMpegRecorder.class);

    public static void sendCommandToProcess(Process process, String whatToSend) {
        try(OutputStream outputStream = process.getOutputStream()) {
            outputStream.write(whatToSend.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            log.error("error sending command to process", e);
            throw new RecordingException(e);
        }
    }

    public static String waitForProcessToFinish(Process process) {
        try {
            if (process.waitFor()!= 0) {
                try (InputStream errorStream = process.getErrorStream()) {
                    String error = IOUtils.toString(errorStream);
                    throw new RecordingException(error);
                } catch (IOException e) {
                    log.error("Error reading process error stream", e);
                    throw new RecordingException(e);
                }
            }
            try (InputStream outputStream = process.getInputStream()) {
                return IOUtils.toString(outputStream);
            } catch (IOException e) {
                log.error("Error reading process output stream", e);
                throw new RecordingException(e);
            }
        } catch (InterruptedException e) {
            log.error("Wait for process was interrupted", e);
            throw new RecordingException(e);
        }
    }

    public static StartedProcess runCommand(final List<String> args) {
        log.info("Trying to execute the following command: " + args);
        try {
            return new ProcessExecutor()
                    .command(args)
                    .start();
        } catch (IOException e) {
            log.warn("Unable to execute command: " + e);
            throw new RecordingException(e);
        }
    }

    public static Dimension getSystemScreenDimension() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    public static String getOsType() {
        if (IS_OS_WINDOWS) {
            return OsType.windows.name();
        } else if (IS_OS_MAC) {
            return OsType.mac.name();
        }
        return OsType.linux.name();
    }
}
