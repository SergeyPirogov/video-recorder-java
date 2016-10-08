package com.automation.remarks.testng.test;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.enums.RecorderType;
import com.automation.remarks.video.enums.RecordingMode;
import com.automation.remarks.video.recorder.monte.MonteRecorder;
import org.apache.commons.io.FileUtils;
import org.testng.IClass;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.internal.ConstructorOrMethod;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

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
        MonteRecorder.conf()
                .withRecordMode(RecordingMode.ANNOTATED)
                .withRecorderType(RecorderType.MONTE);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        // deleteVideoDir();
    }

    private static void deleteVideoDir() throws IOException {
        FileUtils.deleteDirectory(MonteRecorder.conf().getVideoFolder());
    }

    protected ITestResult prepareMock(Class<?> tClass, Method method) {
        ITestResult result = mock(ITestResult.class);
        IClass clazz = mock(IClass.class);
        ITestNGMethod testNGMethod = mock(ITestNGMethod.class);
        ConstructorOrMethod cm = mock(ConstructorOrMethod.class);
        String methodName = method.getName();
        when(result.getTestClass()).thenReturn(clazz);
        when(result.getTestClass().getRealClass()).thenReturn(tClass);
        when(clazz.getName()).thenReturn(this.getClass().getName());
        when(result.getMethod()).thenReturn(testNGMethod);
        when(cm.getMethod()).thenReturn(method);
        when(result.getMethod().getConstructorOrMethod()).thenReturn(cm);
        when(testNGMethod.getMethodName()).thenReturn(methodName);
        ITestContext context = mock(ITestContext.class);
        when(result.getTestContext()).thenReturn(context);
        XmlTest xmlTest = new XmlTest();
        XmlSuite suite = new XmlSuite();
        xmlTest.setXmlSuite(suite);
        suite.setListeners(Arrays.asList(VideoListener.class.getName()));
        when(context.getCurrentXmlTest()).thenReturn(xmlTest);
        return result;
    }

    protected ITestResult prepareMock(Method testMethod) {
        return prepareMock(TestNgListenerTest.class, testMethod);
    }
}
