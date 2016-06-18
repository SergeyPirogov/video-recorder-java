package com.automation.remarks.testng.test;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.VideoConfiguration;
import com.automation.remarks.video.annotations.Video;
import com.automation.remarks.video.recorder.VideoRecorder;
import org.apache.commons.io.FileUtils;
import org.testng.IClass;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.internal.ConstructorOrMethod;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created by sergey on 18.06.16.
 */
public class TestNGVideoListenerTest {

    private Method testMethod;

    @BeforeMethod
    public void beforeMethod(Method method) throws IOException {
        this.testMethod = method;
        FileUtils.deleteDirectory(new File(VideoConfiguration.VIDEO_FOLDER));
    }

    @Test
    @Video
    public void shouldBeOneRecordingOnTestFail() {
        ITestResult result = prepareMock(testMethod);
        VideoListener listener = new VideoListener();
        listener.onTestStart(result);
        listener.onTestFailure(result);
        File file = new File(VideoRecorder.getLastRecordingPath());
        assertTrue(file.exists());
    }

    @Test
    @Video
    public void shouldNotBeRecordingOnTestSuccess()  {
        ITestResult result = prepareMock(testMethod);
        VideoListener listener = new VideoListener();
        listener.onTestStart(result);
        listener.onTestSuccess(result);
        File file = new File(VideoRecorder.getLastRecordingPath());
        assertFalse(file.exists());
    }

    @Test
    @Video(enabled = false)
    public void shouldNotBeRecordingIfVideoDisabled() {
        ITestResult result = prepareMock(testMethod);
        VideoListener listener = new VideoListener();
        listener.onTestStart(result);
        listener.onTestSuccess(result);
        File file = new File(VideoRecorder.getLastRecordingPath());
        assertFalse(file.exists());
    }

    @Test
    @Video(name = "new_recording")
    public void shouldBeRecordingWithCustomName() {
        ITestResult result = prepareMock(testMethod);
        VideoListener listener = new VideoListener();
        listener.onTestStart(result);
        listener.onTestFailure(result);
        File file = new File(VideoRecorder.getLastRecordingPath());
        assertTrue(file.exists());
        assertTrue(file.getName().contains("new_recording"));
    }

    private ITestResult prepareMock(Method testMethod) {
        ITestResult result = mock(ITestResult.class);
        IClass clazz = mock(IClass.class);
        ITestNGMethod testNGMethod = mock(ITestNGMethod.class);
        ConstructorOrMethod cm = mock(ConstructorOrMethod.class);
        String methodName = testMethod.getName();
        when(result.getTestClass()).thenReturn(clazz);
        when(clazz.getName()).thenReturn(this.getClass().getName());
        when(result.getMethod()).thenReturn(testNGMethod);
        when(cm.getMethod()).thenReturn(testMethod);
        when(result.getMethod().getConstructorOrMethod()).thenReturn(cm);
        when(testNGMethod.getMethodName()).thenReturn(methodName);
        return result;
    }
}
