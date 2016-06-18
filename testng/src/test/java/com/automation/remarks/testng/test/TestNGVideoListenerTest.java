package com.automation.remarks.testng.test;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import com.automation.remarks.video.recorder.VideoRecorder;
import org.testng.IClass;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.internal.ConstructorOrMethod;

import java.io.File;
import java.lang.reflect.Method;
import java.util.LinkedList;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by sergey on 18.06.16.
 */
public class TestNGVideoListenerTest {

    private Method testMethod;

    @BeforeMethod
    public void beforeMethod(Method method) {
        this.testMethod = method;
    }

    @Test
    @Video
    public void shouldBeOneRecordingOnTestFail() throws NoSuchMethodException {
        ITestResult result = prepareMock(testMethod);
        VideoListener listener = new VideoListener();
        listener.onTestStart(result);
        listener.onTestFailure(result);
        LinkedList<File> allRecordedTestNames = VideoRecorder.getRecordings();
        assertThat(allRecordedTestNames.size(), equalTo(1));
    }

    @Test
    @Video
    public void shouldNotBeRecordingOnTestSuccess() throws NoSuchMethodException {
        ITestResult result = prepareMock(testMethod);
        VideoListener listener = new VideoListener();
        listener.onTestStart(result);
        listener.onTestSuccess(result);
        LinkedList<File> allRecordedTestNames = VideoRecorder.getRecordings();
        assertThat(allRecordedTestNames.size(), equalTo(1));
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
