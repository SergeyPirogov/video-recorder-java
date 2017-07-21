package com.automation.remarks.junit5.test;

/**
 * Created by sergey on 12.02.17.
 */

import com.automation.remarks.junit5.Video;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Video
@Tag("Flaky")
@Test
public @interface FlakyTest {
}
