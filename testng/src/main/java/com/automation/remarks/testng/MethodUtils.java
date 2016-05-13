package com.automation.remarks.testng;

import com.automation.remarks.video.annotations.Video;
import org.testng.ITestNGMethod;

import java.lang.annotation.Annotation;

/**
 * Created by Serhii_Pirohov on 13.05.2016.
 */
class MethodUtils {

    public static Video getVideoAnnotation(ITestNGMethod method) {
        Annotation[] declaredAnnotations = method.getConstructorOrMethod().getMethod().getDeclaredAnnotations();
        for (Annotation declaredAnnotation : declaredAnnotations) {
            if (declaredAnnotation.annotationType().equals(Video.class)) {
                return (Video) declaredAnnotation;
            }
        }
        return null;
    }

}
