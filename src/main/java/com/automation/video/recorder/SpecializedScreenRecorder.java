package com.automation.video.recorder;

import org.monte.media.Format;
import org.monte.media.Registry;
import org.monte.screenrecorder.ScreenRecorder;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by sergey on 13.04.16.
 */
public class SpecializedScreenRecorder extends ScreenRecorder {

    private String fileName;

    public SpecializedScreenRecorder(GraphicsConfiguration cfg,
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
        return new File(movieFolder, fileName + "."
                + Registry.getInstance().getExtension(fileFormat));
    }

    public void setMovieFolder(File movieFolder) {
        this.movieFolder = movieFolder;
    }
}
