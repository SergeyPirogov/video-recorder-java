package com.automation.video.recorder;

import com.automation.video.exception.RecordingException;
import org.monte.media.Format;
import org.monte.media.Registry;
import org.monte.screenrecorder.ScreenRecorder;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sergey on 13.04.16.
 */
public class GeneralScreenRecorder extends ScreenRecorder {

    private String fileName;
    private List<File> createdFiles = new ArrayList<>();

    public GeneralScreenRecorder(GraphicsConfiguration cfg,
                                 Format fileFormat,
                                 Format screenFormat,
                                 Format mouseFormat,
                                 Format audioFormat, File folder, String fileName) throws IOException, AWTException {
        super(cfg, fileFormat, screenFormat, mouseFormat, audioFormat);
        this.fileName = fileName;
        setMovieFolder(folder);
    }

    @Override
    protected File createMovieFile(Format fileFormat) throws IOException {
        if (!movieFolder.exists()) {
            movieFolder.mkdirs();
        } else if (!movieFolder.isDirectory()) {
            throw new IOException("[" + movieFolder + "] is not a directory.");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy_MM_dd_HH_mm_ss");
        fileName = fileName + "_recording_" + dateFormat.format(new Date());
        File file = new File(movieFolder, fileName + "."
                + Registry.getInstance().getExtension(fileFormat));
        createdFiles.add(file);
        return file;
    }

    @Override
    public java.util.List<File> getCreatedMovieFiles() {
        return createdFiles;
    }

    public void setMovieFolder(File movieFolder) {
        this.movieFolder = movieFolder;
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
