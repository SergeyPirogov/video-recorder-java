package com.automation.remarks.testng;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;

import java.lang.annotation.Annotation;

import static java.util.Arrays.asList;

/**
 * Created by sepi on 05.10.16.
 */
public class TestNgListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {

    }

    @Override
    public void onTestSuccess(ITestResult result) {

    }

    @Override
    public void onTestFailure(ITestResult result) {

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        onTestFailure(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        onTestFailure(result);
    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }

    public boolean shouldIntercept(Class testClass, Class annotation) {
        Listeners listenersAnnotation = getListenersAnnotation(testClass);
        return listenersAnnotation != null && asList(listenersAnnotation.value()).contains(annotation);
    }

    private Listeners getListenersAnnotation(Class testClass) {
        Annotation annotation = testClass.getAnnotation(Listeners.class);
        return annotation != null ? (Listeners) annotation :
                testClass.getSuperclass() != null ? getListenersAnnotation(testClass.getSuperclass()) : null;
    }
}
