package com.automation.remarks.video.recorder;

import com.automation.remarks.video.VideoConfiguration;
import org.monte.media.Format;
import org.monte.media.FormatKeys;
import org.monte.media.math.Rational;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

/**
 * Created by sergey on 13.04.16.
 */
public class VideoRecorder implements IVideoRecorder {

    private String fileName;
    private MonteScreenRecorder screenRecorder;
    private GraphicsConfiguration gc;
    private File folder;

    private static LinkedList<File> recordings;
    private static ArrayList<String> recordingsNames = new ArrayList<>();

    public VideoRecorder(String fileName) {
        this.fileName = fileName;
        this.folder = new File(VideoConfiguration.VIDEO_FOLDER);
        this.gc = getGraphicConfig();
        this.screenRecorder = getScreenRecorder();
    }

    public void start() {
        if (videoEnabled()) {
            screenRecorder.start();
        }
    }

    public LinkedList<File> stop() {
        if (videoEnabled()) {
            screenRecorder.stop();
        }
        LinkedList<File> createdMovieFiles = screenRecorder.getCreatedMovieFiles();
        rememberFileNames(createdMovieFiles);
        recordings = createdMovieFiles;
        return createdMovieFiles;
    }

    private void rememberFileNames(java.util.List<File> fileList) {
        for (File file : fileList) {
            recordingsNames.add(file.getName());
        }
    }

    public static LinkedList<File> getRecordings() {
        return recordings;
    }

    public static boolean videoEnabled() {
        try {
            return Boolean.valueOf(VideoConfiguration.VIDEO_ENABLED);
        } catch (Exception e) {
            return false;
        }
    }

    private GraphicsConfiguration getGraphicConfig() {
        return GraphicsEnvironment
                .getLocalGraphicsEnvironment().getDefaultScreenDevice()
                .getDefaultConfiguration();
    }

    private MonteScreenRecorder getScreenRecorder() {
        Format fileFormat = new Format(MediaTypeKey, MediaType.VIDEO, MimeTypeKey, FormatKeys.MIME_AVI);
        Format screenFormat = new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey,
                ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                DepthKey, 24, FrameRateKey, Rational.valueOf(15),
                QualityKey, 1.0f,
                KeyFrameIntervalKey, 15 * 60);
        Format mouseFormat = new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black",
                FrameRateKey, Rational.valueOf(30));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        Rectangle captureSize = new Rectangle(0,0, width, height);

        return MonteScreenRecorderBuilder
                .builder()
                .setGraphicConfig(gc)
                .setRectangle(captureSize)
                .setFileFormat(fileFormat)
                .setScreenFormat(screenFormat)
                .setFolder(folder)
                .setMouseFormat(mouseFormat)
                .setFileName(fileName).build();
    }

    public static ArrayList<String> getAllRecordedTestNames() {
        return recordingsNames;
    }
}
