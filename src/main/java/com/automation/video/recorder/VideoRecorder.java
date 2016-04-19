package com.automation.video.recorder;

import com.automation.video.VideoConfiguration;
import org.monte.media.Format;
import org.monte.media.math.Rational;

import java.awt.*;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MediaType;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.*;
import static org.monte.media.VideoFormatKeys.MIME_AVI;

/**
 * Created by sergey on 13.04.16.
 */
public class VideoRecorder {

    private String fileName;
    private GeneralScreenRecorder screenRecorder;
    private GraphicsConfiguration gc;
    private File folder;

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
        return screenRecorder.getCreatedMovieFiles();
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

    private GeneralScreenRecorder getScreenRecorder() {
        Format fileFormat = new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI);
        Format screenFormat = new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey,
                ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                DepthKey, 24, FrameRateKey, Rational.valueOf(15),
                QualityKey, 1.0f,
                KeyFrameIntervalKey, 15 * 60);
        Format mouseFormat = new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black",
                FrameRateKey, Rational.valueOf(30));

        return GeneralScreenRecorderBuilder
                .builder()
                .setGraphicConfig(gc)
                .setFileFormat(fileFormat)
                .setScreenFormat(screenFormat)
                .setFolder(folder)
                .setMouseFormat(mouseFormat)
                .setFileName(fileName).build();
    }
}
