package com.automation.remarks.testng.test;

import com.automation.remarks.video.RecordingMode;
import com.automation.remarks.video.recorder.MonteRecorder;
import org.apache.commons.io.FileUtils;
import org.testng.IClass;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.internal.ConstructorOrMethod;

import java.io.IOException;
import java.lang.reflect.Method;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by sergey on 25.06.16.
 */
public class BaseTest {

    protected Method testMethod;

    @BeforeMethod
    public void beforeMethod(Method method) throws IOException {
        this.testMethod = method;
        deleteVideoDir();
        MonteRecorder.conf().withRecordMode(RecordingMode.ANNOTATED);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        deleteVideoDir();
    }

    private static void deleteVideoDir() throws IOException {
        FileUtils.deleteDirectory(MonteRecorder.conf().getVideoFolder());
    }

    protected ITestResult prepareMock(Method testMethod) {
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
