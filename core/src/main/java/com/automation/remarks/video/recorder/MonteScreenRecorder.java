package com.automation.remarks.video.recorder;

import com.automation.remarks.video.DateUtils;
import com.automation.remarks.video.exception.RecordingException;
import org.monte.media.Format;
import org.monte.media.Registry;
import org.monte.screenrecorder.ScreenRecorder;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by sergey on 13.04.16.
 */
public class MonteScreenRecorder extends ScreenRecorder {

    private static final String TEMP_FILENAME_WITHOUT_EXTENSION = "currentRecording";
    private String currentTempExtension;

    MonteScreenRecorder(GraphicsConfiguration cfg,
                               Format fileFormat,
                               Format screenFormat,
                               Format mouseFormat,
                               Format audioFormat,
                               File folder) throws IOException, AWTException {
        super(cfg, fileFormat, screenFormat, mouseFormat, audioFormat);
        super.movieFolder = folder;
    }

    MonteScreenRecorder(GraphicsConfiguration cfg,
                        Rectangle rectangle,
                        Format fileFormat,
                        Format screenFormat,
                        Format mouseFormat,
                        Format audioFormat,
                        File folder) throws IOException, AWTException {
        super(cfg, rectangle, fileFormat, screenFormat, mouseFormat, audioFormat);
        super.movieFolder = folder;
    }

    @Override
    protected File createMovieFile(Format fileFormat) throws IOException {
        this.currentTempExtension = Registry.getInstance().getExtension(fileFormat);
        String tempFile = getTempFileName();

        File fileToWriteMovie = new File(tempFile);
        if (fileToWriteMovie.exists()) {
            fileToWriteMovie.delete();
        }

        return fileToWriteMovie;
    }

    private String getTempFileName() throws IOException {
        if(!this.movieFolder.exists()) {
            this.movieFolder.mkdirs();
        } else if(!this.movieFolder.isDirectory()) {
            throw new IOException("\"" + this.movieFolder + "\" is not a directory.");
        }
        return this.movieFolder + File.separator +
                TEMP_FILENAME_WITHOUT_EXTENSION + "." + this.currentTempExtension;
    }

    public File saveAs(String filename){
        this.stop();

        File tempFile = this.getCreatedMovieFiles().get(0);

        File destFile = getDestinationFile(filename);
        tempFile.renameTo(destFile);
        return destFile;
    }

    private File getDestinationFile(String filename) {
        String fileName = filename + "_recording_" + DateUtils.formatDate(new Date(), "yyyy_dd_MM_HH_mm_ss");
        return new File(this.movieFolder + File.separator + fileName + "." + this.currentTempExtension);

    }

    @Override
    public void start() {
        try {
            super.start();
        } catch (IOException e) {
            throw new RecordingException(e);
        }
    }

    @Override
    public void stop() {
        try {
            super.stop();
        } catch (IOException e) {
            throw new RecordingException(e);
        }
    }
}
