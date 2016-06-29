package com.automation.remarks.junit.rule.test.util;

import com.automation.remarks.junit.rule.test.JUnitVideoRecording;
import org.junit.internal.runners.statements.InvokeMethod;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Created by sergey on 25.06.16.
 */
public class TestUtils {

    private static final Logger logger = Logger.getLogger("TestUtils");

    private static Method getMethod(Class<?> tClass, String methodName) {
        try {
            return tClass.getDeclaredMethod(methodName);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static void runRule(TestRule rule, Class<?> clazz, String methodName) {
        Method method = TestUtils.getMethod(JUnitVideoRecording.class, methodName);
        Description description = Description.createTestDescription(clazz, method.getName(), method.getDeclaredAnnotations());
        try {
            InvokeMethod invokeMethod = new InvokeMethod(new FrameworkMethod(method), clazz.newInstance());
            rule.apply(invokeMethod, description).evaluate();
        } catch (Throwable throwable) {
            logger.warning(Arrays.toString(throwable.getStackTrace()));
        }
    }
}
