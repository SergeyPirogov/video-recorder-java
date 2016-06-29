package com.automation.remarks.video.recorder;

import org.monte.media.Format;
import org.monte.media.FormatKeys;
import org.monte.media.math.Rational;

import java.awt.*;
import java.io.File;
import java.util.LinkedList;

import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MediaType;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.*;

/**
 * Created by sergey on 13.04.16.
 */
public class VideoRecorder implements IVideoRecorder {

    private String fileName;
    private MonteScreenRecorder screenRecorder;
    private GraphicsConfiguration gc;
    private File folder;
    private VideoConfiguration videoConfiguration;

    private static LinkedList<String> recordingsNames = new LinkedList<>();

    public VideoRecorder(String fileName) {
        this.fileName = fileName;
        this.videoConfiguration = conf();
        this.folder = videoConfiguration.getVideoFolder();
        this.gc = getGraphicConfig();
        this.screenRecorder = getScreenRecorder();
    }

    public void start() {
        if (videoConfiguration.isVideoEnabled()) {
            screenRecorder.start();
        }
    }

    public LinkedList<File> stop() {
        if (videoConfiguration.isVideoEnabled()) {
            screenRecorder.stop();
        }
        LinkedList<File> createdMovieFiles = screenRecorder.getCreatedMovieFiles();
        rememberFilePath(createdMovieFiles);
        return createdMovieFiles;
    }

    private void rememberFilePath(java.util.List<File> fileList) {
        for (File file : fileList) {
            recordingsNames.add(file.getAbsolutePath());
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

        Rectangle captureSize = new Rectangle(0, 0, width, height);

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

    public static LinkedList<String> getAllRecordedTestNames() {
        return recordingsNames;
    }

    public static String getLastRecordingPath() {
        if (recordingsNames.size() > 0)
            return recordingsNames.getLast();
        return "";
    }

    public static VideoConfiguration conf(){
        return new VideoConfiguration();
    }
}
