package com.automation.video.recorder;

import com.automation.video.VideoConfiguration;
import com.automation.video.exception.RecordingException;
import org.monte.media.Format;
import org.monte.media.math.Rational;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MIME_AVI;
import static org.monte.media.FormatKeys.MediaType;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.*;

/**
 * Created by sergey on 13.04.16.
 */
public class VideoRecorder {

    private String fileName;
    private SpecializedScreenRecorder screenRecorder;
    private GraphicsConfiguration gc;
    private File folder;

    public VideoRecorder(String fileName) {
        this.fileName = fileName;
        this.folder = new File(VideoConfiguration.VIDEO_FOLDER);
        this.gc = getGraphicConfig();
        this.screenRecorder = getScreenRecorder();
    }

    public void start() {
        screenRecorder.start();
    }

    public List<File> stop() {
        screenRecorder.stop();
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

    private SpecializedScreenRecorder getScreenRecorder() {
        try {
            return new SpecializedScreenRecorder(gc,
                    new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey,
                            ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                            CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                            DepthKey, 24, FrameRateKey, Rational.valueOf(15),
                            QualityKey, 1.0f,
                            KeyFrameIntervalKey, 15 * 60),
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black",
                            FrameRateKey, Rational.valueOf(30)),
                    null, folder, fileName);
        } catch (IOException | AWTException e) {
            throw new RecordingException(e);
        }
    }
}
