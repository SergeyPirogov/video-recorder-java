package com.qa.automation.video.recorder;

import org.monte.media.Format;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by sergey on 4/18/16.
 */
public class GeneralScreenRecorderBuilder {

    private GraphicsConfiguration cfg;
    private Format fileFormat;
    private Format screenFormat;
    private Format mouseFormat;
    private Format audioFormat;
    private File folder;
    private String fileName;

    public static Builder builder() {
        return new GeneralScreenRecorderBuilder().new Builder();
    }

    public class Builder {

        public Builder setGraphicConfig(GraphicsConfiguration cfg) {
            GeneralScreenRecorderBuilder.this.cfg = cfg;
            return this;
        }

        public Builder setFileFormat(Format fileFormat) {
            GeneralScreenRecorderBuilder.this.fileFormat = fileFormat;
            return this;
        }

        public Builder setScreenFormat(Format screenFormat) {
            GeneralScreenRecorderBuilder.this.screenFormat = screenFormat;
            return this;
        }

        public Builder setMouseFormat(Format mouseFormat) {
            GeneralScreenRecorderBuilder.this.mouseFormat = mouseFormat;
            return this;
        }

        public Builder setAudioFormat(Format audioFormat) {
            GeneralScreenRecorderBuilder.this.audioFormat = audioFormat;
            return this;
        }

        public Builder setFolder(File folder) {
            GeneralScreenRecorderBuilder.this.folder = folder;
            return this;
        }

        public Builder setFileName(String fileName) {
            GeneralScreenRecorderBuilder.this.fileName = fileName;
            return this;
        }

        public GeneralScreenRecorder build() {
            try {
                return new GeneralScreenRecorder(cfg,
                        fileFormat,
                        screenFormat,
                        mouseFormat,
                        audioFormat,
                        folder,
                        fileName);
            } catch (IOException | AWTException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
