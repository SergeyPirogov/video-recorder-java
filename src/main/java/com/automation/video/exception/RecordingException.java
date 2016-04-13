package com.automation.video.exception;

/**
 * Created by sergey on 4/13/16.
 */
public class RecordingException extends RuntimeException {

    public RecordingException() {
    }

    public RecordingException(String message) {
        super(message);
    }

    public RecordingException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecordingException(Throwable cause) {
        super(cause);
    }

    public RecordingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
